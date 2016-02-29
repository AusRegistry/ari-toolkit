package com.ausregistry.jtoolkit2.session;

import java.io.IOException;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.Greeting;
import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * EPP is a session-oriented service - all commands (except session management)
 * are only accepted by an EPP server if within a session context.  The Session
 * interface provides the necessary operations to negotiate and use an EPP
 * session; open, close, read and write being the primary operations.
 * The state of a session is maintained internally; state conditions considered
 * relevant to users of a session are visible through the isOpen, isInvalid and
 * isAvailable methods.  The acquire and release methods can be used to
 * synchronise use of a Session if the implementation requires this.  EPP
 * allows for servers to close idle connections after a server policy defined
 * period.  In order to keep a session open in this situation, it is necessary
 * to poll the server with valid EPP commands at regular intervals; the
 * keepAlive method provides this functionality.  Upon successfully negotiating
 * a connection to a server, an EPP greeting service element is sent to the
 * client; a representation of that greeting is available using getGreeting.  A
 * session must publish operational data to a StatsManager which is sufficient
 * for the StatsManager to meet its interface obligations.  That StatsManager
 * is made available via getStatsManager.
 *
 */
public interface Session {

    /**
     * Configure EPP session parameters such as server host name, port, client
     * identifier, password and authentication sources.
     *
     * @param properties Properties as defined in the SessionProperties
     * interface, which govern the behaviour of a Session.
     *
     * @throws SessionConfigurationException Session configuration failed to
     * prepare the session for being opened.  The cause of the failure should
     * be available by calling getCause() on the thrown exception.
     */
    void configure(SessionProperties properties)
        throws SessionConfigurationException;

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
    void open() throws SessionOpenException;

    /**
     * Change the client password to the given value.  This must be implemented
     * in EPP by logging in with the newPW value set, then logging out.
     *
     * @param newPassword The new client password.
     */
    void changePassword(String newPassword) throws SessionOpenException;

    /**
     * EPP sessions are closed cleanly by sending an EPP logout command,
     * awaiting a response from the server, then acknowledging the close
     * of the underlying transport mechanism initiated by the server upon
     * completion of sending the logout response.  This method guarantees that
     * the session is closed and the resources used to maintain the connection
     * are freed.  This may require forcing the underlying transport mechanism
     * to close in the event that the logout fails (for any reason).  Any
     * errors which occur during the close operation are not presented to the
     * caller, but may be logged.
     */
    void close();

    /**
     * EPP service discovery is implemented by the greeting service element.
     * Upon successfully negotiating a connection to a server, a greeting is
     * sent to the client, describing the services offered by the EPP server.
     * That information is encapsulated in a Greeting object, which may be
     * retrieved using this method.
     */
    Greeting getGreeting();

    /**
     * Read a single EPP service element from the transport layer.
     *
     * @throws IOException There was a failure in the transport layer in
     * attempting to receive data from the server.  The cause of the exception
     * should be described in the exception message.
     */
    String read() throws IOException;

    /**
     * Read a single EPP service element from the transport layer and assign
     * values to the attributes of the given Response instance appropriately,
     * according to the received XML document content.  Typically, the latter
     * can be achieved by invoking the given Response instance&rsquo;s toXML
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
    void read(Response response) throws IOException, ParsingException;

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
    XMLDocument readToDocument() throws IOException, ParsingException;

    /**
     * Write an EPP service element to the transport layer service, with the
     * intention of sending the command to the connected EPP server.
     *
     * @throws IOException The transport layer was unable to send the data to
     * the server.  This condition is considered permanent and causes the
     * session state to become invalid, as indicated by isInvalid.
     */
    void write(String xml) throws IOException;

    /**
     * Write an EPP service element to the transport layer service, with the
     * intention of sending the command to the connected EPP server.  The
     * service element is modelled by the specified {@link
     * com.ausregistry.jtoolkit2.se.Command} instance.  The XML representation
     * of the service element is obtained from the toXML method of the Command
     * instance.
     *
     * @throws IOException The transport layer was unable to send the data to
     * the server.  This condition is considered permanent and causes the
     * session state to become invalid, as indicated by isInvalid.
     *
     * @throws ParsingException See the description of Command.toXML().
     */
    void write(Command command)
        throws IOException, ParsingException;

    /**
     * Determine whether a call to open has previously succeeded.
     */
    boolean isOpen();

    /**
     * Determine whether a call to read or write would result in an
     * IOException.
     */
    boolean isInvalid();

    /**
     * Determine whether a call to <code>acquire</code> would block.
     */
    boolean isAvailable();

    /**
     * Acquire the session for exclusive use.  Further requests to acquire
     * the session before it is released should block until release is called.
     * The correct usage sequence if contention is expected is acquire,
     * (write, read)+, release.
     *
     * @throws TimeoutException The acquire timeout period elapsed while
     * waiting to acquire exclusive use of the session.
     *
     * @throws InterruptedException The thread was interrupted while waiting to
     * acquire the lock on the session.
     *
     * @see com.ausregistry.jtoolkit2.session.SessionProperties#getAcquireTimeout()
     */
    void acquire() throws InterruptedException, TimeoutException;

    /**
     * Release the Session for use by another user.  This should only be used
     * after the session has been <code>acquire</code>d.
     */
    void release();

    /**
     * Keep the Session open, as described in the class documentation.
     */
    void keepAlive() throws IOException;

    /**
     * Get the StatsManager as described in the class documentation.
     */
    StatsManager getStatsManager();
}

