package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * Configure the behaviour and authentication sources of a Session.
 */
public interface SessionProperties {

    /**
     * The hostname of the EPP server.
     */
    public String getHostname();

    /**
     * The port on which the EPP server listens (standard port is 700).
     */
    public int getPort();

    /**
     * The client's username (clID in EPP terminology) as used in the EPP login
     * command.
     */
    public String getClientID();

    /**
     * Set the client's username (clID in EPP terminology) to be returned by 
     * further calls to getClientID.
     */
    public void setClientID(String id);
    
    /**
     * The client's password (pw in EPP terminology) as used in the EPP login
     * command.
     */
    public String getClientPW();

    /**
     * Set the password to be returned by further calls to getClientPW.
     */
    public void setClientPW(String password);

    /**
     * The protocol version of EPP to be used to communicate with the EPP
     * server.
     */
    public String getVersion();

    /**
     * Request that the EPP server send any language-configurable messages in
     * this language.
     */
    public String getLanguage();

    /**
     * Request that the objects specified by these URIs be managable during the
     * Session configured from these properties.
     */
    public String[] getObjURIs();

    /**
     * Request that the extensions specified by these URIs be available during
     * the Session configured from these properties.
     */
    public String[] getExtURIs();

    /**
     * The location of the KeyStore file from which private key and public
     * certificate data will be read in order to establish a connection with
     * the EPP server.
     */
    public String getKeyStoreFilename();

    /**
     * The passphrase used to protect the KeyStore identified by <a
     * href="#getKeyStoreFilename()">getKeyStoreFilename</a>.
     */
    public String getKeyStorePassphrase();

    /**
     * The type of the KeyStore.  The two most common types are JKS (default)
     * and PKCS12.
     */
    public String getKeyStoreType();

    /**
     * The location of the KeyStore file from which Certification Authority
     * data will be read in order to verify the authenticity of the public key
     * in the accompanying user keystore.
     */
    public String getTrustStoreFilename();

    /**
     * The passphrase used to protect the KeyStore identified by <a
     * href="#getTrustStoreFilename()">getTrustStoreFilename</a>.
     */
    public String getTrustStorePassphrase();

    /**
     * The SSL version to use.  The only version currently supported by EPP is
     * TLSv1.
     */
    public String getSSLVersion();

    /**
     * The SSL key manager algorithm to use.  The default algorithm for the
     * Sun JRE is SunX509.
     */
    public String getSSLAlgorithm();

    /**
     * The maximum number of commands of the given type to process in a single
     * session over a short period of time (default: infinity).
     */
    public int getCommandLimit(CommandType type);

    /**
     * The maximum number of commands of any type to process in a single
     * session over a short period of time (default: infinity ).
     */
    public int getCommandLimit();

    /**
     * The time interval, in milliseconds, over which a command is considered
     * recent for the purposes of session-specific command limiting (default: 1
     * second = 1000 ms).
     */
    public long getCommandLimitInterval();

    /**
     * The maximum duration of time (specified in milliseconds) to wait when
     * acquiring a session.
     */
    public long getAcquireTimeout();

    /**
     * The maximum length of time (specified in milliseconds) to wait for data
     * to become available on the underlying socket when reading.
     */
    public int getSocketTimeout();

    /**
     * Enforce strict validation of incoming and outgoing service elements.
     * The purpose of strict validation is to detect service element errors
     * prior to transmission. 
     */
    public boolean enforceStrictValidation();
}
