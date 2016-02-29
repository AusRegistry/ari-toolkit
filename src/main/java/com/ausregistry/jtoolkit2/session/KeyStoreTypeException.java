package com.ausregistry.jtoolkit2.session;

import java.security.KeyStoreException;

/**
 * The requested type of KeyStore implementation wasn't supported by the
 * available Providers.
 */
public class KeyStoreTypeException extends KeyStoreException {
    private static final long serialVersionUID = 2871709917491631879L;

    public KeyStoreTypeException(KeyStoreException cause) {
        super(cause);
    }
}

