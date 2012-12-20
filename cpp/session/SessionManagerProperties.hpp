#ifndef __SESSION_MANAGER_PROPERTIES_HPP
#define __SESSION_MANAGER_PROPERTIES_HPP

#include "session/SessionPoolProperties.hpp"
#include "common/Properties.hpp"
#include <vector>
#include <string>

/**
 * A SessionManager is configured based on a SessionManagerProperties
 * instance.  This class loads properties from a properties file and presents the
 * set of properties required for a SessionManager and its internal SessionPool.
 */
class SessionManagerProperties : public SessionPoolProperties
{
public:

    virtual std::string getHostname() const = 0;
    virtual int getPort(void) const = 0;
    virtual std::string getClientID() const = 0;
    virtual void setClientPW(const std::string & pw) = 0;
    virtual std::string getClientPW() const = 0;
    virtual std::string getVersion() const = 0;
    virtual std::string getLanguage() const = 0;
    virtual std::vector<std::string> getObjURIs() const = 0;
    virtual std::vector<std::string> getExtURIs() const = 0;
    // virtual std::vector<std::string> getXSDLocations() const = 0;

    virtual std::string getPrivateKeyFilename() const = 0;
    virtual std::string getCertFilename() const = 0;
    virtual std::string getCAFilename() const = 0;
    virtual std::string getPrivateKeyPassphrase() const = 0;
    virtual std::string getSSLVersion() const = 0;

    virtual int getCommandLimit(const CommandType* type) const = 0;
    virtual int getCommandLimit() const = 0;
    virtual long getWaitTimeout() const = 0;
    virtual long getCommandLimitInterval() const = 0;

    virtual int getMaximumPoolSize() const = 0;
    virtual long getServerTimeout() const = 0;
    virtual long getClientTimeout() const = 0;
    
#if 0
    virtual void load();
    virtual void store() const = 0;
    
    /// Return all values that have property names starting with 'key_prefix'.
    virtual std::vector<std::string> getStringProperties(
            const std::string& key_prefix) const = 0;

    virtual std::string getStringProperty (const std::string& name) const = 0;
    virtual std::string getStringProperty (const std::string& name, 
                                   const std::string& defaultValue) const = 0;
    virtual std::string getProperty(const std::string& name) const = 0;
    virtual bool   getBooleanProperty(const std::string& name, 
                              bool defaultValue = false) const = 0;
    virtual int    getIntProperty(const std::string& name, 
                          int defaultValue = 0) const = 0;
    virtual long   getLongProperty(const std::string& name,
                           long defaultValue = 0L) const = 0;
    
    virtual void setProperty (const std::string& key, const std::string& value)
            throw (PropertyConfigException, PropertyNotFoundException) = 0;
#endif

};    
    
#endif // __SESSIONMANAGERPROPERTIES_H

