#include "se/Greeting.hpp"
#include "session/SessionManagerImpl.hpp"
#include "session/SessionPoolImpl.hpp"
#include "session/SessionFactory.hpp"
#include "session/Timer.hpp"
#include "se/ResultCode.hpp"

#include "common/EPPException.hpp"
#include "common/AutoMutex.hpp"

#include "session/SessionManagerProperties.hpp"
#include "session/Transaction.hpp"
#include "session/StatsViewer.hpp"
#include "session/StatsManager.hpp"
#include "session/LoginException.hpp"

#include "common/ErrorPkg.hpp"
#include <time.h>
#include <unistd.h>
#include <errno.h>
#include <limits>
#include <typeinfo>

using namespace std;

string SessionManagerImpl::pname("com.ausregistry.cpptoolkit.session");

SessionManagerImpl::SessionManagerImpl(SessionManagerProperties* props)
    throw (ConfigurationException)
    : state(STOPPED), properties(NULL)
{
    debugLogger = Logger::getLogger(pname + ".debug");
    userLogger  = Logger::getLogger(pname + ".user");

    configure(props);
}

SessionManagerImpl::~SessionManagerImpl()
{ }

void SessionManagerImpl::configure(SessionManagerProperties* props)
    throw (ConfigurationException)
{
    if (props == NULL)
    {
        throw ConfigurationException("SessionManagerProperties not provided");
    }
    SessionManagerProperties* prevProperties = properties;
    properties = props;

    try
    {
        doConfigure();
    }
    catch (const EPPException& e)
    {
        properties = prevProperties;
        if (properties != NULL)
        {
            try
            {
                doConfigure();
            }
            catch (EPPException& ee)
            {
                properties = NULL;
                ConfigurationException c("Could not configure the session manager with the previous properties.");
                c.causedBy(ee);
                throw c;
            }
        }
        ConfigurationException c("Could not configure the session manager.");
        c.causedBy(e);
        throw c;
    }
}

void SessionManagerImpl::doConfigure() throw (EPPException)
{
#if 0
    systemSetProperty("EPP.client.messages.dir",
                      properties->getProperty("EPP.client.messages.dir"));
    systemSetProperty("java.util.logging.config.file",
                      properties->getProperty("java.util.logging.config.file"));
#endif

    if (sessionPool.get() != NULL)
    {
        sessionPool->empty();
    }
    // Create a new pool that uses the new properties.
    sessionPool = auto_ptr<SessionPool>(new SessionPoolImpl(properties));
}


const Greeting* SessionManagerImpl::getLastGreeting() const
    throw (SessionConfigurationException, SessionOpenException)
{
    try
    {
        return sessionPool->getLastGreeting();
    }
    catch (EPPInterruptedException& ie)
    {
        return NULL;
    }
}


void SessionManagerImpl::startup()
        throw (SessionConfigurationException,
               SessionOpenException)
{
    userLogger->info ("startup");

    int failCount = 0;
    bool initialised = false;

    while (!initialised)
    {
        try
        {
            sessionPool->init();
            initialised = true;
            state = STARTED;
        }
        catch (const EPPInterruptedException &ie)
        {
            failCount++;
            userLogger->severe(ErrorPkg::getMessage("startup.interrupted"));
        }
        catch (const LoginException& le)
        {
            try
            {
                dynamic_cast<CommandFailedException *>(le.getCause());
                if (failCount < MAX_ACCEPTABLE_FAIL_COUNT)
                {
                    // Try again.
                    failCount++;
                    continue;
                }
            }
            catch (bad_cast&)
            { }
            throw;
        }
    }
}

namespace {

// Little helper to ensure a session is released back into the pool upon
// destruction of this object.
// 
class SessionHandle
{
public:
    SessionHandle(SessionPool& pool, Session* session)
        : _pool(pool), _sess(session)
    { }
    ~SessionHandle() { _pool.releaseSession(_sess); }

    Session& session() { return *_sess; }

private:
    SessionPool& _pool;
    Session* _sess;
};

} // anonymous namespace

void SessionManagerImpl::execute(Transaction& tx)
    throw (FatalSessionException, EPPIOException, ParsingException,
           CommandFailedException, IllegalStateException)
{
    debugLogger->LOG_FINEST("enter");

    if (state == STOPPED)
    {
        throw IllegalStateException(
                "Can not execute a command because the session "
                "manager is not started.");
    }
    Command& cmd = *(tx.getCommand());
    Response& response = *(tx.getResponse());

    int failCount = 0;
    bool isExecuted = false;

    while (!isExecuted && state != STOPPED)
    {
        try
        {
            SessionHandle sh(*sessionPool, sessionPool->getSession(cmd.getCommandType()));
            StatsManager* statsManager = sh.session().getStatsManager();
            statsManager->incCommandCounter(cmd.getCommandType());

            sh.session().write(cmd);
            isExecuted = true;
            sh.session().read(response);
            tx.setState(Transaction::PROCESSED);

            const vector<Result>& results(response.getResults());
            for (vector<Result>::const_iterator result_iter = results.begin();
                 result_iter != results.end(); ++result_iter)
            {
                int code = result_iter->getResultCode();
                statsManager->incResultCounter(code);
                if (code == ResultCode::CMD_FAILED
                    || code == ResultCode::CMD_FAILED_CLOSING)
                {
                    throw CommandFailedException();
                }
            }
        }
        catch (CommandFailedException& cfe)
        {
            userLogger->LOG_WARNING(cfe.getMessage());
            if (state != STOPPED && failCount < MAX_ACCEPTABLE_FAIL_COUNT)
            {
                failCount++;
            }
            else throw;
        }
        catch (EPPIOException& e)
        {
            userLogger->LOG_WARNING(e.getMessage());
            userLogger->LOG_WARNING(ErrorPkg::getMessage("net.socket.closed"));
            if (state != STOPPED && failCount < MAX_ACCEPTABLE_FAIL_COUNT)
            {
                failCount++;
            }
            else
            {
                throw;
            }
            
        }
        catch (SessionConfigurationException& sce)
        {
            FatalSessionException fe;
            fe.causedBy(sce);
            throw fe;
        }
        catch (SessionOpenException& soe)
        {
            FatalSessionException fe;
            fe.causedBy(soe);
            throw fe;
        }
        // Session release managed by handle class.
    }
    if (!isExecuted && state == STOPPED)
    {
        throw IllegalStateException("Illegal state");
    }
    debugLogger->LOG_FINEST("exit");
}

namespace {

// Attempt transmission of each the transactions in txs, setting the transation
// state to fatal or retry if there is an error.  If an OI error is encountered,
// the session is closed and EPPIOException is thrown.
// Quirk: This does not send the first entry in the array as it assumed to
// already have been used to decide whether 'session' is in a good state.
void send(vector<Transaction>& txs, Session& session, StatsManager& statsManager, Logger* userLogger)
    throw (EPPIOException)
{
    typedef vector<Transaction>::iterator iter;
    iter tx = txs.begin();
    tx++;   // skip first entry
    for (; tx != txs.end(); ++tx)
    {
        const Transaction::State state(tx->getState());
        if (! (state == Transaction::UNPROCESSED || state == Transaction::RETRY))
            continue;

        try
        {
            Command& command = *(tx->getCommand());
            session.write(command);
            statsManager.incCommandCounter(command.getCommandType());
        }
        catch (ParsingException& pe)
        {
            userLogger->warning(pe.getMessage());
            tx->setState(Transaction::FATAL_ERROR);
            tx->setCause(pe);
            // This transaction has not been sent, so it is okay to skip this
            // item and try the next one.
        }
        catch (EPPIOException& ioe)
        {

            userLogger->severe(ioe.getMessage());
            tx->setState(Transaction::RETRY);
            tx->setCause(ioe);

            // This session is toast.
            session.close();
            throw;
        }
    }
}

// Attempt to receive the responses to the previously sent commands.
// Already processed commands or 'fatal' error'd transactions are skipped, but
// the former count as successfully sent for this batch of receives.  Returns
// the offset of the first un-successfully transmitted item.
// 
int receive(vector<Transaction>& txs, Session& session,
            StatsManager& statsManager, Logger* userLogger)
{
    typedef vector<Transaction> Trans;
    Trans::size_type firstFailed = numeric_limits<int>::max();

    for (Trans::size_type i = 0; i < txs.size(); ++i)
    {
        switch (txs[i].getState())
        {
            case Transaction::PROCESSED:
                // Already received.
                continue;
            case Transaction::FATAL_ERROR:

                // Skip this as it was not sent.
                firstFailed = std::min(i, firstFailed);
                continue;
            default:
                ;
                // Let's process this one because it is either unprocessed or
                // retry-able.
        }

        try
        {
            session.read(*(txs[i].getResponse()));
            txs[i].setState(Transaction::PROCESSED);
        }
        catch (ParsingException& pe)
        {
            userLogger->warning(pe.getMessage());
            txs[i].setState(Transaction::FATAL_ERROR);
            txs[i].setCause(pe);

            // We can't trust that the message boundaries will be correct
            // following a parsing error, so the session must be closed in
            // order to prevent incorrect interpretation of further service
            // elements received via this session.  We also stop processing.
            session.close();

            return min(i, firstFailed);

        } catch (EPPIOException& ioe) {

            userLogger->warning(ioe.getMessage());
            txs[i].setState(Transaction::RETRY);
            txs[i].setCause(ioe);

            return min(i, firstFailed);
        }
    }

    return txs.size();
}

} // anonymous namespace


int SessionManagerImpl::execute(vector<Transaction>& txs)
    throw (FatalSessionException, IllegalStateException)
{
    debugLogger->LOG_FINEST("enter");
    if (txs.size() == 0) return 0;
    debugLogger->LOG_FINEST("size > 0");

    if (state == STOPPED)
    {
        throw IllegalStateException("Session manager is not started.");
    }
    debugLogger->LOG_FINEST("state != STOPPED");

    // When pipelining commands, a single session must be used for all
    // transactions to guarantee the correct command sequence.
    int firstFailedTrans = 0;
    SessionHandle sh(*sessionPool, sendCommandAndGetSession(txs));
    try
    {
        StatsManager* statsManager = sh.session().getStatsManager();
        send(txs, sh.session(), *statsManager, userLogger);
        firstFailedTrans = receive(txs, sh.session(), *statsManager, userLogger);
    }
    catch (EPPIOException& e)
    {
        // Only send can throw EPPIOException, so none of the transactions worked,
        // so return the offset to the 1st.
        return 0;
    }
    debugLogger->LOG_FINEST("exit");
    return firstFailedTrans;
}

void SessionManagerImpl::shutdown()
{
    debugLogger->LOG_FINEST("enter");
    userLogger->LOG_INFO("Initiating shutdown");

    SMState tmpState = state;

    if (tmpState == RUNNING)
    {
        debugLogger->LOG_INFO("state == RUNNING");
        state = STARTED;
        debugLogger->LOG_INFO("state == STARTED");

        pthread_cond_signal(&shutdown_condition);
        debugLogger->LOG_INFO("Joining keep-alive thread");
        pthread_join(run_thread_id, NULL);
        debugLogger->LOG_INFO("Joined keep-alive thread");
        pthread_mutex_destroy(&run_mutex);
        pthread_cond_destroy(&shutdown_condition);
    }

    if (tmpState == STARTED)
    {
        debugLogger->LOG_INFO("state == STARTED");
        sessionPool->empty();

        state = STOPPED;
    }

    debugLogger->LOG_INFO("state == STOPPED");

    userLogger->LOG_INFO("Shutdown complete");
    debugLogger->LOG_FINEST("exit");
}

void *SessionManagerImpl::doRun(void *myobj)
{
    static_cast<SessionManagerImpl*>(myobj)->threadMain();
    return NULL;
}

void SessionManagerImpl::run() {
    if (state != STARTED)
        return;

    pthread_mutex_init(&run_mutex, NULL);
    pthread_cond_init(&shutdown_condition, NULL);
    pthread_attr_t *attrs = NULL;
    void *args = this;

    int result = pthread_create(
            &run_thread_id,
            attrs,
            &SessionManagerImpl::doRun,
            (void *) args);

    if (result) {
        state = STARTED;
        userLogger->LOG_WARNING("Failed to create keep-alive thread: "
                + errno);
    }
}


/**
 * @return If the condition was not signalled during the wait period, thus resulting in
 * a timeout, then 
 */
int SessionManagerImpl::waitForInterrupt(struct timespec awakenTime)
{
    int res;
    int err;

    {
        AutoMutex lock(&run_mutex);
        res = pthread_cond_timedwait(
                &shutdown_condition, &run_mutex, &awakenTime);
        err = errno;
    }

    if (res)
        return err;

    return 0;
}

void SessionManagerImpl::threadMain(void)
{
    debugLogger->LOG_FINEST("enter");
    state = RUNNING;

    while (state == RUNNING)
    {
        long sleepInterval = sessionPool->keepAlive();
        struct timespec wakeTime(Timer::msOffset2abs(sleepInterval));

        bool interrupted = false;
        int retry = 0;
        int maxRetries = MAX_SLEEP_INTERRUPTS_TO_FAIL;

        // sleep for at least 'sleepInterval' milliseconds, taking into
        // account interruptions.
        do
        {
            retry++;
            if (sleepInterval > 0)
            {
                //long sleepStart = Timer::now();
                int waitCode = waitForInterrupt(wakeTime);
                if (waitCode == ETIMEDOUT)
                    interrupted = false;
                else if (waitCode == EINVAL)
                {
                    userLogger->LOG_WARNING(
                            "Out of range value for sleep: "
                            + sleepInterval/1000);
                    break;
                }
                else if (waitCode == EPERM)
                {
                }
                else if (waitCode == 0)
                {
                    interrupted = true;
                }
                else {
                    userLogger->LOG_WARNING(
                            "Unexpected return value from waitForInterrupt: "
                            + waitCode);
                    break;
                }
            }
        }
        while (interrupted && retry < maxRetries && state == RUNNING);
    }

    debugLogger->LOG_FINEST("exit");
    return;
}

void SessionManagerImpl::changePassword(
    const string& oldPassword, const string& newPassword)
{
    debugLogger->LOG_FINEST("enter");

    if (userLogger->enabled(Logger::FINE))
    {
        vector<string> args;
        args.push_back("<<old>>");
        args.push_back("<<new>>");
        vector<string> vals;
        vals.push_back(oldPassword);
        vals.push_back(newPassword);

        userLogger->LOG_FINE(
                ErrorPkg::getMessage("reconfigure.pw.change.init", args, vals));
    }
    sessionPool->empty();

    try
    {
        auto_ptr<Session> session(SessionFactory::newInstance(properties));
        session->changePassword(newPassword);
        userLogger->LOG_INFO(ErrorPkg::getMessage("reconfigure.pw.change.complete"));
        properties->setClientPW(newPassword);
        sessionPool = auto_ptr<SessionPool>(new SessionPoolImpl(properties));
        try
        {
            sessionPool->init();
        }
        catch (EPPException& e)
        {
            userLogger->LOG_SEVERE(e.getMessage());
            userLogger->LOG_SEVERE(ErrorPkg::getMessage("reconfigure.pw.change.reinit.fail"));
            throw;
        }
    }
    catch (EPPException& ex)
    {
        userLogger->LOG_SEVERE(ex.getMessage());
        userLogger->LOG_SEVERE(ErrorPkg::getMessage("reconfigure.pw.change.fail"));
        throw;
    }
    debugLogger->LOG_FINEST("exit");
}

const StatsViewer& SessionManagerImpl::getStatsViewer() const
{
    return sessionPool->getStatsViewer();
}


// This will either return an 'acquired' Session, or will throw an exception.
// txs must contain at least one Transaction.
Session* SessionManagerImpl::sendCommandAndGetSession(vector<Transaction>& txs)
    throw (FatalSessionException, IllegalStateException)
{
    debugLogger->LOG_FINEST("enter");

    Session* session = NULL;
    Command& command = *(txs[0].getCommand());
    int failCount = 0;

    while (state != STOPPED)
    {
        try
        {
            // session = sessionPool->getSession(txs);
            session = sessionPool->getSession();
            try
            {
                std::string xml = command.toXML();
                session->write(command);
                session->getStatsManager()->incCommandCounter(command.getCommandType());
            }
            catch (ParsingException& e)
            {
                // We got the session, tried to use it, but there was a
                // problem.  We assume that the packet-length preamble
                // was not at fault (if this assumption is false, expect all
                // following packets from this session to be invalid).
                txs[0].setState(Transaction::FATAL_ERROR);
                txs[0].setCause(e);
            }
            return session;
        }
        catch (EPPIOException& ioe)
        {
            sessionPool->releaseSession(session);
            if (++failCount > MAX_ACCEPTABLE_FAIL_COUNT)
            {
                FatalSessionException fse;
                fse.causedBy(ioe);
                throw fse;
            }
        }
        catch (EPPInterruptedException& ie)
        {
            // Release and try again while 'running'.
            sessionPool->releaseSession(session);
            userLogger->LOG_WARNING(ie.getMessage());
        }
        catch (SessionConfigurationException& sce)
        {
            sessionPool->releaseSession(session);
            FatalSessionException fse;
            fse.causedBy(sce);
            throw fse;
        }
        catch (SessionOpenException& soe)
        {
            sessionPool->releaseSession(session);
            FatalSessionException fse;
            fse.causedBy(soe);
            throw fse;
        }
    }
    throw IllegalStateException(
            "Can not execute command on a session because the session "
            "manager is not started.");
}

