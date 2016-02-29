package com.ausregistry.jtoolkit2.session;

/**
 * Configure the behaviour of a SessionPool.  {@link
 * com.ausregistry.jtoolkit2.session.Session}s in the pool are configured from
 * the {@link com.ausregistry.jtoolkit2.session.SessionProperties} extended by
 * this.
 */
public interface SessionPoolProperties {
    /**
     * Get the maximum number of sessions allowed in the pool.
     */
    int getMaximumPoolSize();

    /**
     * Get the configured time interval (in milliseconds) after which the EPP
     * server will close idle connections.
     */
    long getServerTimeout();

    /**
     * The maximum time for configured objects to spend in wait() for any
     * relevant condition, such as session acquisition or release.
     */
    long getWaitTimeout();

    /**
     * Get the session idle time (in milliseconds) after which the keep-alive
     * feature will allow sessions in the pool to be dropped (will no longer
     * poll).
     */
    long getClientTimeout();
}
