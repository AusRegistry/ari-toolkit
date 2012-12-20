#ifndef __SESSION_POOL_PROPERTIES_HPP
#define __SESSION_POOL_PROPERTIES_HPP

#include "session/SessionProperties.hpp"

/**
 * Configure the behaviour of a SessionPool.  {@link
 * com.ausregistry.jtoolkit2.session.Session}s in the pool are configured from
 * the {@link com.ausregistry.jtoolkit2.session.SessionProperties} extended by
 * this.
 */
class SessionPoolProperties : public SessionProperties
{
public:
	/**
	 * Get the maximum number of sessions allowed in the pool.
	 */
	virtual int getMaximumPoolSize() const = 0;

	/**
	 * Get the configured time interval (in milliseconds) after which the EPP
	 * server will close idle connections.
	 */
	virtual long getServerTimeout() const = 0;

	/**
	 * Get the session idle time (in milliseconds) after which the keep-alive
	 * feature will allow sessions in the pool to be dropped (will no longer
	 * poll).
	 */
	virtual long getClientTimeout() const = 0;
};

#endif // __SESSION_POOL_PROPERTIES_HPP

