package com.ausregistry.jtoolkit2.session;

/**
 * The response to an EPP login command indicated that login failed.  Common
 * reasons for failure are incorrect authentication data, a malformed login
 * service element, exceeded session limit and server errors unrelated to the
 * EPP.
 */
public class LoginException extends Exception {
    private static final long serialVersionUID = 1929974738227448000L;

    private String msg;

    public LoginException() {
        this("Authentication failure in login");
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String errorMessage) {
        msg = errorMessage;
    }

    public String getMessage() {
        return msg;
    }
}

