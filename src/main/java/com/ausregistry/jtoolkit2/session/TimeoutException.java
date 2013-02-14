package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * This defines the exception that is thrown when a timeout period has elapsed.
 */
public class TimeoutException extends Exception {
    private static final long serialVersionUID = -1139392571972438327L;

    /**
     * Instantiates a new timeout exception.
     *
     * @param msg the message to used for the exception.
     */
    public TimeoutException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new timeout exception.
     *
     * @param timeout the period that has been exceeded
     */
    public TimeoutException(long timeout) {
        super(ErrorPkg.getMessage("timeout", "<<timeout>>", String.valueOf(timeout)));
    }
}
