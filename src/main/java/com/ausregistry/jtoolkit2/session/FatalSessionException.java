package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Thrown to indicate that attempts to configure and/or open a Session failed
 * in such a way as to indicate that further attempts to open a session with
 * the same configuration would also fail.  The {@link SessionProperties}
 * should be examined in conjunction with the cause of this exception and any
 * associated log messages to identify the problem and resolve the issue.
 */
public class FatalSessionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 895103134023475944L;

    public FatalSessionException() {
        super(ErrorPkg.getMessage("epp.session.open.fatalerr"));
    }

    public FatalSessionException(Throwable cause) {
        super(cause);
    }
}

