package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Thrown to indicate that a server has responded to a login command with a
 * "Session limit exceeded; server closing connection" result message.  From
 * RFC5730:<p>
 * {@code This response code MUST be returned when a server receives a <login>
 * command, and the command can not be completed becaquse the client has
 * exceeded a system-defined limit on the number of sessions that the client
 * can establish.  It might be possible to establish a session by ending
 * existing unused sessions and closing inactive connections.}</p>
 */
public class SessionLimitExceededException extends LoginException {
    /**
     *
     */
    private static final long serialVersionUID = 7819901786803747534L;

    public SessionLimitExceededException() {
        this(ErrorPkg.getMessage("session.limit.exceeded"));
    }

    public SessionLimitExceededException(Throwable t) {
        super(t);
    }

    public SessionLimitExceededException(String message) {
        super(message);
    }
}

