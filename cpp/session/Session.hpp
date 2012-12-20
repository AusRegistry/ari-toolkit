#ifndef __SESSION_H
#define __SESSION_H

#include "xml/ParsingException.hpp"
#include "session/EPPIOException.hpp"
#include "session/EPPInterruptedException.hpp"
#include "session/SessionOpenException.hpp"
#include "session/SessionConfigurationException.hpp"

class SessionProperties;
class StatsManager;
class Greeting;
class XMLDocument;
class Command;
class ReceiveSE;

/**
 * EPP is a session-oriented service - all commands (except session management)
 * are only accepted by an EPP server if within a session context.  The Session
 * interface provides the necessary operations to negotiate and use an EPP
 * session; open, close, read and write being the primary operations.
 * The state of a session is maintained internally; state conditions considered
 * relevant to users of a session are visible through the isOpen, isInvalid and
 * isAvailable methods.  The acquire and release methods can be used to
 * synchronize use of a Session if the implementation requires this.  EPP
 * allows for servers to close idle connections after a server policy defined
 * period.  In order to keep a session open in this situation, it is necessary
 * to poll the server with valid EPP commands at regular intervals; the
 * keepAlive method provides this functionality.  Upon successfully negotiating
 * a connection to a server, an EPP greeting service element is sent to the
 * client; a representation of that greeting is available using getGreeting.  A
 * session must publish operational data to a StatsManager which is sufficient
 * for the StatsManager to meet its interface obligations.  That StatsManager
 * is made available via getStatsManager().
 *
 */

class EPPTimeoutException : public EPPException
{
public:
	EPPTimeoutException(const std::string& msg)
		: EPPException(msg) { }
	EPP_EXCEPTION(EPPTimeoutException); 
};


class Session
{
public:
	virtual ~Session(void) { }
	/**
	 * Configure EPP session parameters such as server hostname, port, client
	 * identifier, password and authentication sources.
	 *
	 * @param properties Properties as defined in the SessionProperties
	 * interface, which govern the behaviour of a Session.
	 *
	 * @throws SessionConfigurationException Session configuration failed to
	 * prepare the session for being opened.  The cause of the failure should
	 * be available by calling getCause() on the thrown exception.
	 */
    virtual void configure(SessionProperties* properties) 
		throw (SessionConfigurationException) = 0;

	/**
	 * An EPP session is opened by first establishing a connection using the
	 * server location information and authentication sources provided in
	 * <code>configure</code>, then issuing a login command with further
	 * authentication data and options provided in <code>configure</code>.
	 * Service information provided immediately upon connection establishment
	 * may affect options provided in the login command.
	 *
	 * @throws SessionOpenException The session failed to open.  The cause MUST
	 * be made available by calling <code>getCause()</code> on the exception
	 * object thrown.  Required causes are LoginException (such as is caused by
	 * invalid username/password combination) and IOException (such as would
	 * result from the server being unavailable at the configured location).
	 * Other causes may include ParsingException and SSLHandshakeException.
	 */
    virtual void open() throw (SessionOpenException) = 0;

	/**
	 * Formally close the EPP sessions with a logout command.  This method
	 * closes the session and releases the internal resources used to maintain
	 * the connection.  The transport mechanism will be forcefully closed and
	 * released even if the logout communication fails.  Any errors which occur
	 * during the close operation should not be presented to the caller, but
	 * may be logged depending upon the implementation.  The session can not be
	 * used for further operations once closed.
	 */
    virtual void close() = 0;
    
	/**
	 * EPP service discovery is implemented by the greeting service element.
	 * Upon successfully negotiating a connection to a server, a greeting is
	 * sent to the client, describing the services offered by the EPP server.
	 * That information is encapsulated in a Greeting object, which may be
	 * retrieved using this method.
	 */
	virtual const Greeting* getGreeting() const = 0;
	
	/**
	 * Change the clients password.   Session must already be opened.
	 */
	virtual void changePassword(const std::string& newPW) = 0;

	/**
	 * Read a single EPP service element from the transport layer.
	 *
	 * @throws IOException There was a failure in the transport layer in
	 * attempting to receive data from the server.  The cause of the exception
	 * should be described in the exception message.
	 */
	virtual std::string read() throw (EPPIOException) = 0;
    
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
	virtual void read(ReceiveSE& receivedElement) throw (EPPIOException, ParsingException) = 0;

	/**
	 * Read a single EPP service element from the transport layer, then
	 * construct an XMLDocument instance from the element read.
	 *
	 * @throws IOException There was a failure in the transport layer in
	 * attempting to receive data from the server.  The cause of the exception
	 * should be described in the exception message.
	 *
	 * @throws ParsingException The XML parser reported an error while trying
	 * to construct an XMLDocument instance from the data received from the
	 * server.
	 */
    virtual XMLDocument* readToDocument() 
			throw (EPPIOException, ParsingException) = 0;
    
	/**
	 * Write an EPP service element to the transport layer service, with the
	 * intention of sending the command to the connected EPP server.
	 *
	 * @throws IOException The transport layer was unable to send the data to
	 * the server.  This condition is considered permanent and causes the
	 * session state to become invalid, as indicated by isInvalid.
	 */
    virtual void writeXML(const std::string &xml) throw (EPPIOException, ParsingException) = 0;
    
	/**
	 * Write an EPP service element to the transport layer service, with the
	 * intention of sending the command to the connected EPP server.  The
	 * service element is modelled by the specified {@link Command} instance.
	 * The XML representation of the service element is obtained from the toXML
	 * method of the Command instance.
	 *
	 * @throws IOException The transport layer was unable to send the data to
	 * the server.  This condition is considered permanent and causes the
	 * session state to become invalid, as indicated by isInvalid.
	 *
	 * @throws ParsingException See the description of Command.toXML().
	 */
	virtual void write(Command& command) throw (EPPIOException, ParsingException) = 0;

	/**
	 * Determine whether a call to open has previously succeeded.
	 */
    virtual bool isOpen() const = 0;

	/**
	 * Determine whether a call to read or write would result in an
	 * IOException.
	 */
    virtual bool isInvalid() const = 0;

	/**
	 * Determine whether a call to <code>acquire</code> would block.
	 */
    virtual bool isAvailable() const = 0;

	/**
	 * Acquire the session for exclusive use.  Further requests to acquire
	 * the session before it is released should block until release is called.
	 * The correct usage sequence if contention is expected is acquire,
	 * (write, read)+, release.
	 *
	 * @throws InterruptedException The thread was interrupted while waiting
	 * to acquire the lock on the session.
	 */
	virtual void acquire() throw (EPPInterruptedException, EPPTimeoutException) = 0;
	
	/**
	 * Release the Session for use by another user.  This should only be used
	 * after the session has been <code>acquire</code>d.
	 */
    virtual void release() = 0;
    
	/**
	 * Keep the Session open, as described in the class documentation.
	 */
    virtual void keepAlive() throw (EPPIOException) = 0;
    
	/**
	 * Get the StatsManager as described in the class documentation.
	 */
    virtual StatsManager* getStatsManager() = 0;
};

#endif  // __SESSION_H
