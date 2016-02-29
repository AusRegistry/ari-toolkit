package com.ausregistry.jtoolkit2.session;

/**
 * The KeyStore was found in the location specified, but loading of the
 * KeyStore failed.  The exception message should describe the reason for
 * failure.
 */
public class KeyStoreReadException extends java.io.IOException {
    private static final long serialVersionUID = 1278011234758501135L;

    public KeyStoreReadException(java.io.IOException ioe) {
        super(ioe.getMessage());
    }
}

