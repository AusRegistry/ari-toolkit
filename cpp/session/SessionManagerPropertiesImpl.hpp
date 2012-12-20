#ifndef __SESSION_MANAGER_PROPERTIES_IMPL_HPP
#define __SESSION_MANAGER_PROPERTIES_IMPL_HPP

#include "session/SessionManagerProperties.hpp"
#include "common/Properties.hpp"
#include <vector>
#include <string>

/**
 * A SessionManager is configured based on a SessionManagerProperties
 * instance.  This class loads properties from a properties file and presents the
 * set of properties required for a SessionManager and its internal SessionPool.
 */
class SessionManagerPropertiesImpl : public SessionManagerProperties
{
public:

	SessionManagerPropertiesImpl(const std::string& propertiesFile);
	virtual ~SessionManagerPropertiesImpl();

    std::string getHostname() const;
    int getPort(void) const;
    std::string getClientID() const;
    void  setClientPW(const std::string & pw);
    std::string getClientPW() const;
    std::string getVersion() const;
    std::string getLanguage() const;
    std::vector<std::string> getObjURIs() const;
    std::vector<std::string> getExtURIs() const;

    std::string getPrivateKeyFilename() const;
    std::string getCertFilename() const;
    std::string getCAFilename() const;
    std::string getPrivateKeyPassphrase() const;
    std::string getSSLVersion() const;

	int getCommandLimit(const CommandType* type) const;
	int getCommandLimit() const;
	long getWaitTimeout() const;
	long getCommandLimitInterval() const;

	/**
	 * getMaximumPoolSize, getServerTimeout and getServerTimeout implement
	 * the {@lib SessionPoolProperties} interface.
	 */
	int getMaximumPoolSize() const;
	long getServerTimeout() const;
	long getClientTimeout() const;
	long getAcquireTimeout() const;
    int getSocketTimeout() const;

	bool enforceStrictValidation() const;

private:
	SessionManagerPropertiesImpl(const SessionManagerPropertiesImpl&);
	SessionManagerPropertiesImpl& operator=(const SessionManagerPropertiesImpl&);

	std::string filename;
	Properties properties;
};    
    

#endif // __SESSION_MANAGER_PROPERTIES_IMPL_HPP
