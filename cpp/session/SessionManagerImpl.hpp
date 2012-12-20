#ifndef __SESSION_MANAGER_IMPL_HPP
#define __SESSION_MANAGER_IMPL_HPP

#include "session/SessionManager.hpp"
#include "session/SessionPool.hpp"
#include <memory>

#include <pthread.h>

class Session;
class Logger;

typedef enum SMState { STOPPED, STARTED, RUNNING };

/**
 * AusRegistry&apos;s basic implementation of the SessionManager interface.  This
 * implementation provides only a blocking implementation of the
 * execute() method.  By default, it will use the TLSSession
 * implementation of the Session interface.  It also implements its own
 * StatsViewer.
 */
class SessionManagerImpl : public SessionManager
{
public:
    SessionManagerImpl()
        : state(STOPPED), sessionPool(NULL), properties(NULL)
    { }

    /// Does not assume ownership of 'props'.
    SessionManagerImpl(SessionManagerProperties* props) throw (ConfigurationException);
    virtual ~SessionManagerImpl();

    const SessionManagerProperties& getProperties() const 
        { return *properties; }

    /**
     * Configure the SessionManager from the given set of properties.  This can
     * also be used to reconfigure the SessionManager and its internal {@link
     * SessionPool}.  A change of password should be performed using <a
     * href="#changePassword()">changePassword</a>.  Following a change of
     * password, the properties source should be updated to reflect the change.
     * If this is not done, then the next invocation of configure() will fail.
     * @param properties A SessionManagerProperties ready to be used to reconfigure.  This
     *   object must outlive the SessionManager.
     */
    void configure(SessionManagerProperties* properties)
        throw (ConfigurationException);

    const Greeting* getLastGreeting() const
        throw (SessionConfigurationException,
               SessionOpenException);

    void startup() throw (SessionConfigurationException,
                          SessionOpenException);

    /**
     * Keep internal session alive (i.e. prevent server triggered time-out).
     * This method starts an internal thread which will continue until the
     * manager is shut down.
     */
    void keepAlive();

    void shutdown();
    void run();

    void changeMaxPoolSize (int size)
    {
        sessionPool->setMaxSize(size);
    }

    void changePassword (const std::string &oldPassword,
                         const std::string &newPassword);

    /**
     * Try to process a single transaction.  Up to
     * MAX_ACCEPTABLE_FAIL_COUNT attempts will be made to process the
     * transaction in cases where I/O errors or non-protocol server errors
     * occur during processing.  Use of the underlying session is protected
     * against concurrent use by other threads by using the
     * getSession/releaseSession features of this SessionManager's {@link
     * com.ausregistry.jtoolkit2.session.SessionPool}.  This method guarantees
     * that the session used will be returned to the pool before the method
     * returns.
     *
     * @throws FatalSessionException No session could be acquired to process
     * the transaction.  Check the exception message and log records for
     * details.
     *
     * @throws IOException Every attempt to execute the transaction command
     * failed due to an IOException.  This is the last such IOException.
     *
     * @throws ParsingException Parsing of the response failed.  Check the
     * exception message for the cause.
     *
     * @throws CommandFailedException The acceptable limit on the number of
     * failed commands due to server error was exceeded in trying to process
     * the command.  This probably indicates a server limitation related to
     * the command being processed.
     *
     * @throws IllegalStateException The SessionManager had been shutdown or
     * not started up prior to invoking this method.
     */
    void execute(Transaction& tx)
        throw (FatalSessionException,
               EPPIOException,
               ParsingException,
               CommandFailedException,
               IllegalStateException);

    /**
     * Pipeline execute a sequence of commands over a single session.  A single
     * {@link Session} is used in order to guarantee ordering of commands.
     * The number of transactions in the PROCESSED state following attempted
     * execution of all eligible transactions is returned.  If the number
     * returned is less than the number of transactions supplied, then the
     * state of each transaction should be checked prior to getting information
     * from the contained response object.  If the state is PROCESSED, then it
     * is safe to use any of the methods on the response.  If the state is
     * RETRY, then it is possible that a further attempt to execute the
     * transaction will succeed.  If the state is FATAL_ERROR, then the
     * transaction should not be re-attempted.  In either of these cases, the
     * getCause method can be used to determine the reason the transaction
     * failed.  If the state remains UNPROCESSED, it indicates that an error
     * which occurred in processing an earlier transaction would have prevented
     * the successful processing of the UNPROCESSED transaction.  These
     * transactions should be retried once the underlying cause of the first
     * error has been resolved.
     *
     * @throws FatalSessionException No session was available to process any
     * transactions (possibly due to misconfiguration or service unavailability
     * - check the exception message and log records).
     *
     * @throws IllegalStateException The SessionManager had been shutdown or
     * not started up prior to invoking this method.  See {@link
     * SessionManager#startup()} and {@link SessionManager#shutdown()}.
     *
     * @return The number of transactions in the PROCESSED state which precede
     * the first transaction which failed due to an I/O error (if any).
     */
    int execute(std::vector<Transaction>& txs)
        throw (FatalSessionException, IllegalStateException);

    const StatsViewer & getStatsViewer() const; 

private:
    static const int MAX_SLEEP_INTERRUPTS_TO_FAIL = 3,
                     MAX_ACCEPTABLE_FAIL_COUNT = 10;
    static std::string pname;
    
    pthread_t run_thread_id;
    pthread_cond_t shutdown_condition;
    pthread_mutex_t run_mutex;

    int waitForInterrupt(struct timespec awakenTime);
    SMState state;
    bool running;
    std::auto_ptr<SessionPool> sessionPool;

    SessionManagerProperties* properties;
    Logger *debugLogger, *userLogger;

    static void *doRun(void *myobj);
    void threadMain(void);
    void doConfigure () throw (EPPException);

    Session* sendCommandAndGetSession(std::vector<Transaction>& txs)
        throw (FatalSessionException, IllegalStateException);

};

#endif // __SESSION_MANAGER_IMPL_HPP
