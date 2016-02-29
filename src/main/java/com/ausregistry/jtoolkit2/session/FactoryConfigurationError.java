package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ConfigurationError;

/**
 * A fatal error occurred while trying to obtain an instance from a toolkit
 * factory.  Such an error would prevent the system from functioning and is
 * due to incorrect configuration, thus requiring intervention to resolve.
 */
public class FactoryConfigurationError extends ConfigurationError {

    private static final long serialVersionUID = -1300855942728686423L;

    /**
     * Instantiates a new factory configuration error using a message.
     *
     * @param msg the message to be using in the error
     */
    public FactoryConfigurationError(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new factory configuration error using a message and an exception.
     *
     * @param msg the message to be using in the error
     * @param cause the exception that caused the error
     */
    public FactoryConfigurationError(String msg, Throwable cause) {
        super(msg, cause);
    }
}

