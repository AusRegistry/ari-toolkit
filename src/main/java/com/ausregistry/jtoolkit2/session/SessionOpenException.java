package com.ausregistry.jtoolkit2.session;

/**
 * Container exception for all exceptions which may be thrown in trying to
 * open a Session.  Examples of such exceptions are IOException,
 * SSLHandshakeException, GreetingException and LoginException.
 */
public class SessionOpenException extends Exception {

    private static final long serialVersionUID = 135019219553375225L;

    /**
     * The cause of a SessionOpenException must be indicated via the Throwable
     * argument to this constructor.
     */
    public SessionOpenException(Throwable cause) {
        super(cause);
    }

    /**
     * The cause of a SessionOpenException will be indicated by a message
     */
    public SessionOpenException(final String message) {
        super(message);
    }
}

