#ifndef __SESSION_PROPERTIES_HPP
#define __SESSION_PROPERTIES_HPP

#include "se/CommandType.hpp"

#include <string>
#include <vector>

/**
 * Configure the behaviour and authentication sources of a Session.
 */
class SessionProperties
{
public:
	virtual ~SessionProperties(void) { }
    /**
     * The hostname of the EPP server.
     */
    virtual std::string getHostname() const = 0;

    /**
     * The port on which the EPP server listens (standard port is 700)
     */
    virtual int getPort() const = 0;

    /**
     * The client's username (clID in EPP terminology) as used in the EPP login
     * command.
     */
    virtual std::string getClientID() const = 0;

    /**
     * The client's password (pw in EPP terminology) as used in the EPP login
     * command.
     */
    virtual std::string getClientPW() const = 0;

    /**
     * The protocol version of EPP to be used to communicate with the EPP
     * server.
     */
    virtual std::string getVersion() const = 0;

    /**
     * Request that the EPP server send any language-configurable messages in
     * this language.
     */
    virtual std::string getLanguage() const = 0;

    /**
     * Request that the objects specified by these URIs be manageable during the Session
     * configured from these properties.
     */
    virtual std::vector<std::string> getObjURIs() const = 0;

    /**
     * Request that the extensions specified by these URIs be available during
     * the Session configured from these properties.
     */
    virtual std::vector<std::string> getExtURIs() const = 0;

    /**
     * The location of the private key file from which private key
     * will be read in order to establish a connection with the EPP server.
     */
    virtual std::string getPrivateKeyFilename() const = 0;

    /**
     * The location of the certificate for the private key 
     * which will be read in order to establish a connection with the EPP
     * server.
     */
    virtual std::string getCertFilename() const = 0;

    /**
     * The location of the certification authorities (CA's) which will be read
     * in order to establish a connection with the EPP server.
     */
    virtual std::string getCAFilename() const = 0;

    /**
     * The passphrase used to protect the Private Key identified by <a
     * href="#getKeyStoreFilename()">getKeyStoreFilename</a>.
     */
    virtual std::string getPrivateKeyPassphrase() const = 0;

    /**
     * The SSL version to use.  The only version currently supported by EPP is
     * TLSv1.
     */
    virtual std::string getSSLVersion() const = 0;

    /**
     * The maximum time for configured objects to spend in wait() for any
     * relevant condition, such as session acquisition or release.
     */
    virtual long getWaitTimeout() const = 0;

    /**
     * The maximum number of commands of the given type to process in a single
     * session over a short period of time (suggested default 1 second).
     */
    virtual int getCommandLimit(const CommandType* type) const = 0;

    /**
     * The maximum number of commands of any given type to process in a single
     * session over a short period of time (suggested default 1 second).
     */
    virtual int getCommandLimit() const = 0;

    /**
     * The time interval, in milliseconds, over which a command is considered
     * recent for the purposes of session-specific command limiting (suggested
     * defautl 1 second = 1000ms)
     */
    virtual long getCommandLimitInterval() const = 0;

    /**
     * The maximum duration of time (specified in milliseconds) to wait when
     * acquiring a session.
     */
    virtual long getAcquireTimeout() const = 0;

    /**
     * Enforce strict validation of incoming and outgoing service elements.
     * The purpose of strict validation is to detect service element errors
     * prior to transmission. 
     */
    virtual bool enforceStrictValidation() const = 0;

    /**
     * The maximum length of time (specified in milliseconds) to wait for data
     * to become available on the underlying socket when reading.
     */
    virtual int getSocketTimeout() const = 0;
};

#endif // __SESSION_PROPERTIES_HPP

