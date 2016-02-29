package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.se.Greeting;
import com.ausregistry.jtoolkit2.se.CommandType;

import java.io.IOException;

/**
 * A SessionPool has responsibility for maintaining a persistent pool of EPP
 * sessions to a single EPP server.  A pool may only grow to the size specified
 * in its {@link com.ausregistry.jtoolkit2.session.SessionPoolProperties} and
 * after successful initialisation may be instructed to hold sessions open for
 * at least the time specified by the client timeout property.  The client must
 * correctly specify the EPP server's timeout value in order for the
 * SessionPool to optimally prevent session timeouts.  The SessionPool is not
 * responsible for actually running a keep-alive loop; see the implementation of
 * the {@code run()} method in the package private class SessionManagerImpl for
 * an example of how to use the keep-alive feature.  By default, the {@link
 * com.ausregistry.jtoolkit2.session.SessionManagerFactory} will provide
 * that implementation of {@link
 * com.ausregistry.jtoolkit2.session.SessionManager}.
 */
public interface SessionPool {
    /**
     * Close all sessions in the pool, then remove references to those
     * sessions.
     */
    void empty();

    /**
     * Close all open sessions, then open the same number of sessions that
     * were closed.
     */
    void clean() throws SessionConfigurationException,
            SessionOpenException;

    /**
     * Set the maximum number of sessions allowed in the pool to the
     * specified value.
     */
    void setMaxSize(int size);

    /**
     * Get the greeting data from the most recently opened session in the pool.
     */
    Greeting getLastGreeting() throws SessionConfigurationException,
            SessionOpenException, InterruptedException;

    /**
     * Get the most suitable session from the pool.  The session provided
     * MUST NOT be eligible for further acquisition using getSession until it
     * has been released using releaseSession.  Use of getSession should not be
     * mixed with direct use of Session.acquire on sessions managed by the
     * pool.  Implementations are not required to guarantee that this will not
     * cause further invocations of getSession to block.
     *
     * @throws SessionConfigurationException The pool had no available
     * sessions, so a new session was created but configuration of that
     * session failed due to invalid session properties.
     *
     * @throws SessionOpenException The pool had no available sessions, and so
     * tried to open a new session which was successfully configured, but
     * failed to open.  It should be expected that further requests to get a
     * session will fail.
     */
    Session getSession() throws SessionConfigurationException,
            SessionOpenException, InterruptedException;

    /**
     * Get the most suitable session from the pool based on the type of the
     * command to be sent over that session.  See getSession for further
     * details.
     */
    Session getSession(CommandType commandType)
            throws SessionConfigurationException, SessionOpenException,
            InterruptedException;

    /**
     * Release back to the pool a session acquired using getSession.
     */
    void releaseSession(Session session);

    /**
     * Keep a single session in the pool open by polling the EPP server at
     * regular intervals.
     *
     * @throws IOException See Session.keepAlive.
     */
    long keepAlive() throws IOException;

    /**
     * Get the StatsViewer responsible for reporting operating statistics
     * about the sessions in the pool.
     */
    StatsViewer getStatsViewer();
}
