package com.ausregistry.jtoolkit2.session;

/**
 * Thrown when configuration of a SessionManager fails and a previous
 * configuration is not available to fall back to.  The cause of the exception
 * must be available via getCause().
 */
public class ConfigurationException extends Exception {

    private static final long serialVersionUID = -253766373975550464L;

    public ConfigurationException(Throwable t) {
        super(t);
    }
}

