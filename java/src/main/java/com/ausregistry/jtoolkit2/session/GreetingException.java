package com.ausregistry.jtoolkit2.session;

import java.io.Serializable;

/**
 * Thrown when an EPP greeting was expected but the service element read was
 * not a valid EPP greeting.
 */
public class GreetingException extends Exception implements Serializable {
    private static final long serialVersionUID = -2964955261411813653L;

    public GreetingException(Throwable t) {
        super(t);
    }
}

