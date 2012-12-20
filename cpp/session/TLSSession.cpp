#include "session/TLSSession.hpp"

#include "se/CLTRID.hpp"
#include "se/PollRequestCommand.hpp"
#include "se/LoginCommand.hpp"
#include "se/LogoutCommand.hpp"
#include "se/ReceiveSE.hpp"
#include "se/Response.hpp"
#include "se/ResultCode.hpp"
#include "common/ErrorPkg.hpp"
#include "session/CertificateUserMismatchException.hpp"
#include "session/UserPassMismatchException.hpp"
#include "session/SessionLimitExceededException.hpp"
#include "session/UninitialisedSessionException.hpp"
#include "session/GreetingException.hpp"
#include "session/TLSContext.hpp"
#include "session/TLSSocket.hpp"
#include "common/Logger.hpp"
#include "session/Timer.hpp"
#include "xml/ParsingException.hpp"
#include "common/AutoMutex.hpp"
#include "session/SessionProperties.hpp"
#include "se/Greeting.hpp"

#include <xalanc/XalanDOM/XalanText.hpp>
#include <sys/time.h>
#include <errno.h>

using namespace std;
using namespace xalanc;

namespace {
    PollRequestCommand& poll()
    {
        static PollRequestCommand poll;
        return poll;
    }

    const string& pollXML()
    {
        static const string str(poll().toXML());
        return str;
    }
}

TLSSession::TLSSession(SessionProperties* props)
    throw (SessionConfigurationException)
    : _inUse(false), _isOpen(false), _isInvalid(true), commandCounter(NULL)
{
    pthread_mutex_init(&mtx, NULL);
    pthread_cond_init(&cond, NULL);
    configure(props);
    
    const string pname("com.ausregistry.cpptoolkit.session");
    debugLogger = Logger::getLogger(pname + ".debug");
    supportLogger = Logger::getLogger(pname + ".support");
    userLogger = Logger::getLogger(pname + ".user");

    parser = auto_ptr<XMLParser>(new XMLParser);
}

TLSSession::~TLSSession()
{
    pthread_mutex_destroy(&mtx);
    if (pthread_cond_destroy(&cond) == EBUSY)
    {
        try
        {
            supportLogger->LOG_SEVERE(
                "Condition variable for TLSSession destroyed while in use.");
        }
        catch (...)
        { }
    }
    // default dtor for TLSSocket is sufficient.
}

void TLSSession::configure(SessionProperties* props)
    throw (SessionConfigurationException)
{
    hostName = props->getHostname();
    port = props->getPort();
    username = props->getClientID();
    password = props->getClientPW();
    eppVersion = props->getVersion();
    language = props->getLanguage();
    objURIs = props->getObjURIs();
    extURIs = props->getExtURIs();
    acquireTimeout = props->getAcquireTimeout();
    commandCounter.reset(new CommandCounter(props->getCommandLimitInterval()));
    validationEnabled = props->enforceStrictValidation();

    try
    {
        ctx = std::auto_ptr<TLSContext>(
                new TLSContext(props->getPrivateKeyFilename(),
                               props->getCertFilename(),
                               props->getCAFilename(),
                               props->getPrivateKeyPassphrase()));
    }
    catch (const EPPException& e)
    {
        SessionConfigurationException sce("Error during TLSSession::configure.");
        sce.causedBy(e);
        throw sce;
    }
}

void TLSSession::open() throw (SessionOpenException)
{
    debugLogger->LOG_FINEST("enter");
    _isInvalid = true;
    try
    {
        openSocket();
        processGreeting();
        login();
        _isOpen = true;
        _isInvalid = false;
    }
    catch (EPPException& e)
    {
        userLogger->LOG_WARNING(e.getMessage());
        SessionOpenException seo("Session open failed.");
        seo.causedBy(e);
        throw seo;
    }
    debugLogger->LOG_FINEST("exit");
}

void TLSSession::processGreeting() throw (EPPIOException, GreetingException)
{
    if (greeting.get() == NULL)
    {
        greeting = auto_ptr<Greeting>(new Greeting);
    }

    try
    {
        read(*greeting.get());
    }
    catch (EPPException& pe)
    {
        GreetingException ge("Error while processing greeting.");
        ge.causedBy(pe);
        throw ge;
    }
}

void TLSSession::openSocket()
{
    debugLogger->LOG_FINEST("enter");
    socket = ctx->createSocket(hostName, port, SO_TIMEOUT);
    debugLogger->LOG_FINEST("exit");
}

void TLSSession::login()
    throw (LoginException, EPPIOException, SessionLimitExceededException)
{
    LoginCommand login(username, password, newPW.get(), eppVersion, language,
                       objURIs, extURIs);

    CLTRID::setClID(username);
    writeXML(login.toXML(), login.getCommandType());
    mruTime = Timer::now();
    Response response;

    try
    {
        read(response);
    }
    catch (ParsingException &pe)
    {
        LoginException le("Parse error during login.");
        le.causedBy(pe);
        throw le;
    }

    Result result(response.getResults()[0]);

    string msg = result.getResultMessage();

    switch (result.getResultCode())
    {
    case ResultCode::SUCCESS:
        return;
    case ResultCode::CMD_USE_ERR:
        userLogger->warning(ErrorPkg::getMessage ("epp.login.fail.loggedin"));
        throw LoginException(msg);
    
    case ResultCode::AUTHENT_ERROR_CLOSING:
    case ResultCode::AUTHENT_ERR:
        {
            const string cn = socket->getCertificateCommonName();
            if (cn != username)
            {
                string_vec args;
                args.push_back("<<pw>>");
                args.push_back("<<clID>>");

                string_vec vals;
                vals.push_back(password);
                vals.push_back(username);
                userLogger->severe(
                    ErrorPkg::getMessage("epp.login.fail.auth.pw", args, vals));
                CertificateUserMismatchException certMismatch(username, password);
                LoginException le;
                le.causedBy(certMismatch);
                throw le;
            }
            else
            {
                string_vec args;
                args.push_back("<<clID>>");
                args.push_back("<<cn>>");

                string_vec vals;
                vals.push_back(username);
                vals.push_back(cn);

                userLogger->severe(
                    ErrorPkg::getMessage("epp.login.fail.auth.match",
                                         args, vals));
                UserPassMismatchException userPassMis(msg);
                LoginException le;
                le.causedBy(userPassMis);
                throw le;
            }
        }
        break;
            
    case ResultCode::UNIMPL_OBJ_SVC:
        {
            const XalanNode *node = result.getResultValue();

            if (node)
            {
                msg = c_str(
                    TranscodeToLocalCodePage(dynamic_cast<const XalanText *>(node)->getData()));
            }

            userLogger->severe(
                ErrorPkg::getMessage("epp.login.fail.unimpl.objsvc", "<<uri>>", msg));
            throw LoginException(msg);
        }
        break;
    
    case ResultCode::PARAM_VAL_SYNTAX_ERR:
        throw LoginException(result.getResultMessage());

    case ResultCode::SESS_LIM_EXCEEDED_CLOSING:
        throw SessionLimitExceededException();

    case ResultCode::CMD_FAILED:
    case ResultCode::CMD_FAILED_CLOSING:
        throw LoginException("Login failed because server is closing");
    }
}

void TLSSession::close()
{
    // Closing an already closed Session is a no-op.
    if (!_isOpen) return;
    _isOpen = false;

    try
    {
        logout();
    }
    catch (const LogoutException &le)
    {
        debugLogger->LOG_FINE(le.getMessage());
        debugLogger->LOG_FINE("net.event.socket_closed");
    }
    closeSocket();
}

void TLSSession::changePassword(const string& newPassword)
{
    newPW = auto_ptr<string>(new string(newPassword));
    open();
    close();
}

void TLSSession::logout() throw (LogoutException)
{
    LogoutCommand logout;
    Response response;

    try
    {
        writeXML(logout.toXML(), logout.getCommandType());
        read(response);

        const vector<Result>& results = response.getResults();
        switch (results[0].getResultCode())
        {
        case ResultCode::SUCCESS:
            return;
        case ResultCode::CMD_FAILED:
            throw LogoutException (results[0].getResultMessage());
        }
    }
    catch (const EPPIOException &ioe)
    {
        LogoutException le("IO exception during logout operation.");
        le.causedBy(ioe);
        throw le;
    }
    catch (const ParsingException &pe)
    {
        LogoutException le("Parsing error during logout operation.");
        le.causedBy(pe);
        throw le;
    }
}

void TLSSession::closeSocket()
{
    socket = auto_ptr<TLSSocket>();
}

string TLSSession::read() throw (EPPIOException)
{
    debugLogger->LOG_FINEST ("enter");
    int n = readSize();
    debugLogger->LOG_FINEST ("PDU size: " + StringUtils::makeString(n));
    string data (readData(n));
    supportLogger->LOG_INFO(data);
    debugLogger->LOG_FINEST("exit");
    return data;
}


void TLSSession::read(ReceiveSE& receivedElement) throw (EPPIOException, ParsingException)
{
    auto_ptr<XMLDocument> doc(readToDocument());
    receivedElement.fromXML(doc.get());
}

XMLDocument* TLSSession::readToDocument() 
        throw (EPPIOException, ParsingException)
{
    string xml = read();
    return parser->parse(xml);
}

void TLSSession::writeXML(const string& xml)
    throw (EPPIOException, ParsingException)
{
    if (validationEnabled)
    {
        try
        {
            auto_ptr<XMLDocument> doc(parser->parse(xml));
        }
        catch (ParsingException& e)
        {
            userLogger->warning("Outbound service element malformed: " + xml);
            userLogger->warning(e.getMessage());
            throw e;
        }
    }
    doWrite(xml);
    mruTime = Timer::now();
}

void TLSSession::write(Command& command) throw (EPPIOException, ParsingException)
{
    return writeXML(command.toXML());
}

void TLSSession::doWrite(const string &xml) throw (EPPIOException)
{
    debugLogger->LOG_FINEST("enter");
    try
    {
        if (socket.get() == NULL)
            throw EPPIOException("The socket is not initialised.");
    
        writeSize(xml.length());
        writeData(xml);
        userLogger->info(xml);
    }
    catch (EPPException& e)
    {
        _isInvalid = true;
        EPPIOException eio("An error occured while writing to the socket.");
        eio.causedBy(e);
        throw eio;
    }
    debugLogger->LOG_FINEST("exit");
}

void TLSSession::writeXML(const string& xml, const CommandType* cmdType)
    throw (EPPIOException)
{
    debugLogger->LOG_FINER("writing command " + cmdType->toString());
    doWrite(xml);
    incCommandCounter(cmdType);
}

void TLSSession::keepAlive() throw (EPPIOException)
{
    debugLogger->LOG_FINEST("enter");

    try
    {
        acquire();
        writeXML(pollXML(), poll().getCommandType());
        read();
        release();
    }
    catch (EPPTimeoutException& e)
    {
        userLogger->LOG_WARNING(e.getMessage());
    }
    catch (EPPInterruptedException &ie)
    {
        userLogger->LOG_INFO(ie.getMessage());
    }
    debugLogger->LOG_FINEST("exit");
}

int TLSSession::readSize() throw (EPPIOException)
{
    int size = 0;
    try
    {
        size = socket->readInt() - 4;
    }
    catch(SSLException &e)
    {
        throw EPPIOException("Error when reading data size from socket.");
    }
    return size;
}

void TLSSession::writeSize(int size) throw (EPPIOException)
{
    try
    {
        socket->writeInt(size + 4);
    }
    catch(SSLException &e)
    {
        throw EPPIOException("Error when writing data size to socket.");
    }
}

string TLSSession::readData(int length) throw (EPPIOException)
{
    string inputBuffer;
    try
    {
        socket->readFully(inputBuffer, length);
    }
    catch(SSLException &e)
    {
        throw EPPIOException("Error when reading data from socket.");
    }
    return inputBuffer;
}

void TLSSession::writeData(const string &xml) throw (EPPIOException)
{
    try
    {
        socket->writeBytes(xml);
    }
    catch(SSLException &e)
    {
        throw EPPIOException("Error when writing data to socket.");
    }
}

void TLSSession::incCommandCounter(const CommandType* type)
{
    debugLogger->LOG_FINEST("enter");
    commandCounter->increment(type);
    debugLogger->LOG_FINEST("exit");
}

void TLSSession::incResultCounter(int resultCode)
{
    debugLogger->LOG_FINEST("enter");
    resultCounter.increment(resultCode);
    debugLogger->LOG_FINEST("exit");
}

int TLSSession::getCommandCount()
{
    debugLogger->LOG_FINEST("enter & exit");
    return commandCounter->getTotal();
}

int TLSSession::getCommandCount(const CommandType* type)
{
    debugLogger->LOG_FINEST("enter & exit");
    return commandCounter->getCount(type);
}

int TLSSession::getResultCodeCount(int resultCode)
{
    debugLogger->LOG_FINEST("enter & exit");
    return resultCounter.getValue(resultCode);
}

long TLSSession::getMruInterval() const
{
    return Timer::msDiff(mruTime);
}

void TLSSession::acquire() throw (EPPInterruptedException, EPPTimeoutException)
{
    debugLogger->LOG_FINEST("enter");
    {
        AutoMutex lock(&mtx);
        while (_inUse)
        {
            const struct timespec until(Timer::msOffset2abs(acquireTimeout));

            switch (pthread_cond_timedwait(&cond, &mtx, &until))
            {
                case EINTR:
                    throw EPPInterruptedException(
                            "Interupted while waiting to acquire a session.");
                case ETIMEDOUT:
                    throw EPPTimeoutException(
                            "Timed-out while waiting to acquire session");
                default:
                    // Default implies signalled.
                    break;
            }
            debugLogger->LOG_FINE(ErrorPkg::getMessage("thread.wakeup.info"));
        }
        _inUse = true;
    }
    debugLogger->LOG_FINEST("exit");
}

void TLSSession::release() throw (IllegalStateException)
{
    debugLogger->LOG_FINEST("enter");
    {
        if (!_inUse)
            throw IllegalStateException(
                "Attempt to release a session that was not in use");
        AutoMutex lock(&mtx);
        _inUse = false;
        pthread_cond_signal(&cond);
    }
    debugLogger->LOG_FINEST("exit");
}

