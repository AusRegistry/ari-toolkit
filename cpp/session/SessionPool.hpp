#ifndef __SESSION_POOL_HPP
#define __SESSION_POOL_HPP

#include "session/EPPIOException.hpp"
#include "session/EPPInterruptedException.hpp"

#include "session/Session.hpp"

class Greeting;
class CommandType;
class StatsViewer;

/**
 * A SessionPool has responsibility for maintaining a persistent pool of EPP
 * sessions to a single EPP server.  A pool may only grow to the size specified
 * in its {@link com.ausregistry.jtoolkit2.session.SessionPoolProperties} and
 * after successful initialisation may be instructed to hold sessions open for
 * at least the time specified by the client timeout property.  The client must
 * correctly specify the EPP server's timeout value in order for the
 * SessionPool to optimally prevent session timeouts.  The SessionPool is not
 * responsible for actually running a keep-alive loop; see the implementation of
 * the run() method in the package private class SessionManagerImpl for
 * an example of how to use the keep-alive feature.  By default, the
 * {@link SessionManagerFactory} will provide
 * that implementation of {@link SessionManager}.
 */
class SessionPool
{
public:
	virtual ~SessionPool(void) { }
	
	/**
	 * Close all sessions in the pool, then remove references to those
	 * sessions.
	 */
	virtual void empty() = 0;

	/**
	 * Initialise the session pool by ensuring that at least one session is
	 * open.
	 */
	virtual void init() throw (SessionConfigurationException,
					   SessionOpenException,
					   EPPInterruptedException) = 0;

	/**
	 * Close all open sessions, then open the same number of sessions that
	 * were closed.
	 */
	virtual void clean() throw (SessionConfigurationException,
						SessionOpenException) = 0;

	/**
	 * Set the maximum number of sessions allowed in the pool to the
	 * specified value.
	 */
	virtual void setMaxSize (int size) = 0;

	/**
	 * Get a read-only over the pool of sessions managed by this
	 * SessionPool.  The caller MUST NOT attempt to modify Sessions returned
	 * by the iterator.
	 */
	virtual const Greeting * getLastGreeting() throw (SessionConfigurationException,
			  						          SessionOpenException,
									          EPPInterruptedException) = 0;

	/**
	 * Get the most suitable session from the pool based upon the command type
	 * (if provided).  The session provided MUST NOT be eligible for further
	 * acquisition using getSession until it has been released using
	 * releaseSession.
	 *
	 * @throws SessionConfigurationException The pool had no available
	 * sessions, so a new session was created but configuration of that
	 * session failed due to invalid session properties.
	 *
	 * @throws SessionOpenException The pool had no available sessions, and so
	 * tried to open a new session which was successfully configured, but
	 * failed to open.  It should be expected that further requests to get a
	 * session will fail.
	 *
	 */
	virtual Session* getSession(const CommandType *type = NULL)
			throw (SessionConfigurationException,
				   SessionOpenException,
				   EPPInterruptedException) = 0;

	/**
	 * Release back to the pool a session acquired using getSession.
	 */
	virtual void releaseSession (Session * session) = 0;

	/**
     * Keep a single session in the pool open by polling the EPP server at
     * regular intervals.  Returns a reasonable duration to wait until the
     * next call to keepAlive, specified in milliseconds.
	 *
	 * @throws IOException See Session.keepAlive.
	 */
	virtual long keepAlive() throw (EPPIOException) = 0;

	/**
	 * Get the StatsViewer responsible for reporting operating statistics
	 * about the sessions in the pool.
	 */
	virtual const StatsViewer& getStatsViewer() const = 0;
};


#endif // __SESSION_POOL_HPP
