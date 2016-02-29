package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * A write operation was performed on a Session which had not yet been
 * successfully opened.
 */
public class UninitialisedSessionException extends java.io.IOException {
    /**
     *
     */
    private static final long serialVersionUID = 175246572597956573L;

    public UninitialisedSessionException() {
        super(ErrorPkg.getMessage("net.socket.uninitialised"));
    }
}

