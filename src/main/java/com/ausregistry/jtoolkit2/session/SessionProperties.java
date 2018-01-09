package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * Configure the behaviour and authentication sources of a Session.
 */
public interface SessionProperties {

    /**
     * The hostname of the EPP server.
     */
    String getHostname();

    /**
     * The port on which the EPP server listens (standard port is 700).
     */
    int getPort();

    /**
     * The client's username (clID in EPP terminology) as used in the EPP login
     * command.
     */
    String getClientID();

    /**
     * Set the client's username (clID in EPP terminology) to be returned by
     * further calls to getClientID.
     */
    void setClientID(String id);

    /**
     * The client's password (pw in EPP terminology) as used in the EPP login
     * command.
     */
    String getClientPW();

    /**
     * Set the password to be returned by further calls to getClientPW.
     */
    void setClientPW(String password);

    /**
     * The protocol version of EPP to be used to communicate with the EPP
     * server.
     */
    String getVersion();

    /**
     * Request that the EPP server send any language-configurable messages in
     * this language.
     */
    String getLanguage();

    /**
     * Request that the objects specified by these URIs be managable during the
     * Session configured from these properties.
     */
    String[] getObjURIs();

    /**
     * Request that the extensions specified by these URIs be available during
     * the Session configured from these properties.
     */
    String[] getExtURIs();

    /**
     * The location of the KeyStore file from which private key and public
     * certificate data will be read in order to establish a connection with
     * the EPP server.
     */
    String getKeyStoreFilename();

    /**
     * The passphrase used to protect the KeyStore identified by <a
     * href="#getKeyStoreFilename()">getKeyStoreFilename</a>.
     */
    String getKeyStorePassphrase();

    /**
     * The type of the KeyStore.  The two most common types are JKS (default)
     * and PKCS12.
     */
    String getKeyStoreType();

    /**
     * The location of the KeyStore file from which Certification Authority
     * data will be read in order to verify the authenticity of the key
     * in the accompanying user keystore.
     */
    String getTrustStoreFilename();

    /**
     * The passphrase used to protect the KeyStore identified by <a
     * href="#getTrustStoreFilename()">getTrustStoreFilename</a>.
     */
    String getTrustStorePassphrase();

    /**
     * The SSL version to use.  The default version is TLSv1.
     */
    String getSSLVersion();

    /**
     * The SSL key manager algorithm to use.  The default algorithm for the
     * Sun JRE is SunX509.
     */
    String getSSLAlgorithm();

    /**
     * The maximum number of commands of the given type to process in a single
     * session over a short period of time (default: infinity).
     */
    int getCommandLimit(CommandType type);

    /**
     * The maximum number of commands of any type to process in a single
     * session over a short period of time (default: infinity ).
     */
    int getCommandLimit();

    /**
     * The time interval, in milliseconds, over which a command is considered
     * recent for the purposes of session-specific command limiting (default: 1
     * second = 1000 ms).
     */
    long getCommandLimitInterval();

    /**
     * The maximum duration of time (specified in milliseconds) to wait when
     * acquiring a session.
     */
    long getAcquireTimeout();

    /**
     * The maximum length of time (specified in milliseconds) to wait for data
     * to become available on the underlying socket when reading.
     */
    int getSocketTimeout();

    /**
     * Enforce strict validation of incoming and outgoing service elements.
     * The purpose of strict validation is to detect service element errors
     * prior to transmission.
     */
    boolean enforceStrictValidation();

    /**
     * Enforce XML output to include namespace prefix.
     *
     * <p>
     * A namespace prefix is considered in-scope on the declaration element
     * as well as on any of its descendant elements. Once declared, the prefix
     * can be used in front of any element or attribute name separated by
     * a colon (such as s:student).
     * </p>
     *
     * <p>
     * This complete name including the prefix is the lexical form of a qualified name (QName):
     * </p>
     * <pre>
     *     QName = &lt;prefix&gt;:&lt;local name&gt;
     * </pre>
     *
     * <p>
     * If not configured, the default value will be {@code false}.
     * </p>
     *
     * @return a boolean flag where true means XML output will have namespace prefix
     */
    boolean needOutputNamespacePrefixInXml();
}
