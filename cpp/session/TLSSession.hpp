#ifndef __TLS_SESSION_HPP
#define __TLS_SESSION_HPP

#include "session/Session.hpp"
#include "session/StatsManager.hpp"
#include "session/CommandCounter.hpp"
#include "session/ResultCounter.hpp"

#include "xml/XMLParser.hpp"
#include "xml/XMLDocument.hpp"

#include "common/IllegalStateException.hpp"
#include "session/EPPIOException.hpp"
#include "session/EPPInterruptedException.hpp"

#include "session/SessionConfigurationException.hpp"
#include "session/SessionOpenException.hpp"
#include "session/LoginException.hpp"
#include "session/LogoutException.hpp"
#include "session/GreetingException.hpp"
#include "session/SessionLimitExceededException.hpp"

#include <memory>
#include <vector>
#include <string>

class SessionProperties;
class Greeting;
class TLSContext;
class TLSSocket;
class Logger;

/**
 * RFC 3734 specifies a transport mapping for EPP over TCP; this class
 * implements that mapping.  That specification requires that the session is
 * layered over TLS (Transport Layer Security).  This class complies with RFC
 * 3734 in implementing the Session interface.  It also implements its own
 * StatsManager since it is tightly coupled with the Session implementation.
 */
class TLSSession : public Session, public StatsManager
{
public:

	TLSSession(SessionProperties* props)
		throw (SessionConfigurationException);
	virtual ~TLSSession();

	/**
	 * Configure the session as described in the Session interface.
	 *
	 * @throws SessionConfigurationException
	 */
	void configure (SessionProperties* properties) 
			throw (SessionConfigurationException);

	bool isInvalid() const { return _isInvalid; };
	bool isOpen() const { return _isOpen; };
	const Greeting* getGreeting() const { return greeting.get(); };

	/**
	 * Implements the open method in the Session interface.  Please refer to
	 * the Session documentation for a description of the open contract.
	 *
	 * @throws SessionOpenException
	 */
	void open() throw (SessionOpenException);

	/**
	 * Close the session, as described in the Session interface.  Any exception
	 * that occurs in the process is logged, but otherwise ignored.
	 */
	void close();

	/**
	 * Change the clients password.   Session must already be opened.
	 */
	void changePassword(const std::string& newPW);

	/**
	 * Receive data from the peer. This method is unsynchronized; the caller
	 * MUST provide synchronization against other calls to read.
	 */
	std::string read() throw (EPPIOException);

	/**
	 * Read a single EPP service element from the transport layer and assign
	 * values to the attributes of the given ReceiveSE instance appropriately,
	 * according to the received XML document content.  Typically, the latter
	 * can be achieved by invoking the given ReceiveSE instance&apos;s toXML
	 * method.
	 *
	 * @throws IOException There was a failure in the transport layer in
	 * attempting to receive data from the server.  The cause of the exception
	 * should be described in the exception message.
	 *
	 * @throws ParsingException The XML parser reported an error while trying
	 * to construct an XMLDocument instance from the data received from the
	 * server.
	 */
	void read(ReceiveSE& receivedElement) throw (EPPIOException, ParsingException);

	/**
	 * Read a single EPP service element as described in the Session interface.
	 */
	XMLDocument * readToDocument() throw (EPPIOException, ParsingException);

	/**
	 * Send data to peer. This method is unsynchronized; the caller MUST
	 * provide synchronization against other calls to <code>write(String)</code>.
	 * @throw ParsingException If validation is enabled, a parsing exception will
	 * be thrown if 'xml' is not valid.
	 */
	void writeXML(const std::string &xml) throw (EPPIOException, ParsingException);

	void write(Command& command) throw (EPPIOException, ParsingException);

	/**
	 * Send a poll command to the EPP server in order to prevent the session
	 * timing out.  This operation does not affect the most-recently-used
	 * statistic.
	 */
	void keepAlive() throw (EPPIOException); 

	void incCommandCounter (const CommandType* type);
	void incResultCounter  (int resultCode);

	/**
	 * Get the total number of commands processed in this session.
	 */
    int getCommandCount();
	
	/**
	 * Get the number of commands of the given type processed in this session.
	 */
    int getCommandCount(const CommandType* type);

	/**
	 * Get the number of responses that had the given result code.
	 */
    int getResultCodeCount(int resultCode);

	/**
	 * Get the length of time (in milliseconds) since the most recent use (mru)
	 * of the session.  The session is considered to be used when the write
	 * method is invoked.
	 */
	long getMruInterval() const;

	/**
	 * Get the StatsManager associated with the session, as described in the
	 * Session interface.
	 */
	StatsManager* getStatsManager() { return this; };

	/**
	 * See the description of isAvailable in the Session interface.
	 */
	bool isAvailable() const { return (_isOpen && !_inUse); };
	
	/**
	 * See the description of acquire in the Session interface.
	 */
	void acquire() throw (EPPInterruptedException, EPPTimeoutException);

	/**
	 * See the description of release in the Session interface.
	 */
	void release() throw (IllegalStateException);

private:
	static const int SO_TIMEOUT = 12;
	static const int BUF_SIZE = 4096;

	pthread_mutex_t mtx;
	pthread_cond_t cond;

	// 'socket' must be destroyed before 'ctx'.
	std::auto_ptr<TLSContext> ctx;
	std::auto_ptr<TLSSocket> socket;

	std::auto_ptr<XMLParser> parser;

	bool _inUse, _isOpen, _isInvalid;
	std::auto_ptr<CommandCounter> commandCounter;
	ResultCounter resultCounter;
	// milliseconds.
	long mruTime;
	long acquireTimeout;

	std::string hostName;
	int port;

	bool validationEnabled;

	typedef std::vector<std::string> string_vec;
	std::string username, password, eppVersion, language, pname;
	string_vec objURIs, extURIs;
	std::auto_ptr<std::string> newPW;

	std::auto_ptr<Greeting> greeting;

	Logger *debugLogger, *supportLogger, *userLogger;

	void openSocket();
	void processGreeting() throw (EPPIOException, GreetingException);
	void login() throw (LoginException, EPPIOException, SessionLimitExceededException);
	void logout() throw (LogoutException);
	void closeSocket();
	void doWrite(const std::string& xml) throw (EPPIOException);
	void writeXML(const std::string& xml, const CommandType* cmdType)
			throw (EPPIOException);
	int readSize() throw (EPPIOException);
	void writeSize(int size) throw (EPPIOException);
	std::string readData (int length) throw (EPPIOException);
	void writeData(const std::string& xml) throw (EPPIOException);
};

#endif // __TLS_SESSION_HPP
