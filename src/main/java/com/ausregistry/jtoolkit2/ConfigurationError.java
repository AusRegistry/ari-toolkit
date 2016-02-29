package com.ausregistry.jtoolkit2;

/**
 * An error occurred during toolkit configuration from which recovery without
 * user intervention is not possible.
 */
public class ConfigurationError extends Error {
    private static final long serialVersionUID = 2557229394685654302L;

    /**
     * Instantiates a new configuration error based a new message.
     *
     * @param message the message
     */
    public ConfigurationError(String message) {
        super(message);
    }

    /**
     * Instantiates a new configuration error based on an exception.
     *
     * @param cause the exception that was thrown
     */
    public ConfigurationError(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new configuration error using a message and exception.
     *
     * @param message the message
     * @param cause the exception that was thrown
     */
    public ConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }
}

