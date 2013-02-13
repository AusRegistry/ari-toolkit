package com.ausregistry.jtoolkit2.session;

/**
 * An EPP logout command was sent to the server, but didn't achieve its
 * purpose of logging out an open session.  Possible reasons are: the session
 * wasn't open, an IO exception occurred while trying to write the data to the
 * socket, and the response was malformed.
 */
public class LogoutException extends Exception {
    private static final long serialVersionUID = 797409628214669256L;
    private String msg;
    private Throwable cause;

    public LogoutException(Throwable t) {
        super(t);
        cause = t;
    }

    public LogoutException(String errorMessage) {
        msg = errorMessage;
        cause = null;
    }

    public String getMessage() {
        if (cause != null) {
            return cause.getMessage();
        }

        return msg;
    }

    public String toString() {
        if (cause != null) {
            return cause.toString();
        }

        return "Logout attempt failed to complete cleanly";
    }
}

