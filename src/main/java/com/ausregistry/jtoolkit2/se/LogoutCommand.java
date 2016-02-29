package com.ausregistry.jtoolkit2.se;

/**
 * Use this to close an open EPP session.  This should be used to cleanly end a
 * session which is no longer needed, or when changing an EPP client password.
 * Instances of this class generate, via the toXML method, logout service elements
 * compliant with the logout specification in RFC5730.
 *
 * @see com.ausregistry.jtoolkit2.se.LoginCommand The session should have been
 * opened using this command prior to logging out.
 */
public final class LogoutCommand extends Command {
    private static final long serialVersionUID = -5639166942335318497L;

    public LogoutCommand() {
        super(StandardCommandType.LOGOUT);
    }
}

