package com.ausregistry.jtoolkit2.session;

/**
 * Indicates that the keystore location specified did not refer to a readable
 * file on the system.  The source from which the {@link SessionProperties}
 * were loaded should be examined in conjunction with the related log messages
 * to resolve this issue.  The message of this exception should state the name
 * of the file expected.
 */
public class KeyStoreNotFoundException extends java.io.FileNotFoundException {
    private static final long serialVersionUID = 8838426685198744968L;

    public KeyStoreNotFoundException(java.io.FileNotFoundException fnfe) {
        super(fnfe.getMessage());
    }
}

