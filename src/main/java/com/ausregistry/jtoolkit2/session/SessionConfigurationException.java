package com.ausregistry.jtoolkit2.session;

/**
 * Container exception for all exceptions which may be thrown in trying to
 * configure a Session.
 *
 */
public class SessionConfigurationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -3958122731649806921L;

    /**
     * A SessionConfigurationException must be instantiated with a causal
     * exception, indicating what caused configuration to fail.
     */
    public SessionConfigurationException(Throwable cause) {
        super(cause);
    }
}

