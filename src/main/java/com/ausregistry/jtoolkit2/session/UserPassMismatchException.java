package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * The credentials supplied to the EPP login command were considered invalid
 * by the EPP server to which the login was sent.  Intervention is required to
 * either change the password or supply the correct credentials to the login
 * command.
 */
public class UserPassMismatchException extends LoginException {
    /**
     *
     */
    private static final long serialVersionUID = -5500476048792038560L;
    private static final String[] USER_PASS_ARR = new String[] {
        "<<clID>>", "<<pw>>"
    };

    public UserPassMismatchException() {
        super("Incorrect password for specified user");
    }

    public UserPassMismatchException(String msg) {
        super(msg);
    }

    public UserPassMismatchException(Throwable cause) {
        super(cause);
    }

    public UserPassMismatchException(String clID, String pw) {
        super(ErrorPkg.getMessage("epp.login.fail.auth.pw",
                    USER_PASS_ARR,
                    new String[] {clID, pw}));
    }
}

