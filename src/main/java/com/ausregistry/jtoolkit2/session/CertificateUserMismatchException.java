package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Thrown to indicate that the common name of an X509 certificate does not
 * match the client identifier (clID) provided in an EPP login command sent
 * over a connection established using that X509 certificate.
 */
public class CertificateUserMismatchException extends LoginException {
    /**
     *
     */
    private static final long serialVersionUID = 4406311838282797298L;
    private static final String[] USER_CN_ARR = new String[] {
        "<<clID>>", "<<cn>>"
    };

    public CertificateUserMismatchException() {
        super("Username does not match certificate common name");
    }

    public CertificateUserMismatchException(String msg) {
        super(msg);
    }

    public CertificateUserMismatchException(String clID, String cn) {
        super(ErrorPkg.getMessage("epp.login.fail.auth.match",
                    USER_CN_ARR,
                    new String[] {clID, cn}));
    }

    public CertificateUserMismatchException(Throwable cause) {
        super(cause);
    }
}

