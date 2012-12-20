#ifndef __TEST_ENVIRONMENT_HPP
#define __TEST_ENVIRONMENT_HPP

#include "session/SessionManagerProperties.hpp"
#include "se/StandardCommandType.hpp"

#include <stdlib.h>

/// Provides a trivial, fixed implememtnation of SessionManagerProperties
/// for unit testing.
// @todo Load from env file, not hard code.
class TestEnvironment : public SessionManagerProperties
{
public:
    TestEnvironment(const std::string& sitePropsFile = "")
    {
        using namespace std;
        const std::string sitePropsEnvName("EPP_SITE_PROPERTIES");
        const std::string defaultFile("etc/site.properties");

        string file(sitePropsFile);
        if (file.size() == 0)
        {
            if (getenv(sitePropsEnvName.c_str()))
            {
                file = getenv(sitePropsEnvName.c_str());
            }
            else
            {
                file = defaultFile;
            }
        }
        try
        {
            siteProps.load(file);
        }
        catch (PropertyConfigException& e)
        {
            EPPException err("Could not open site configuration file.  Tried '"
                    + defaultFile + "', the environment variable '"
                    + sitePropsEnvName + "' and the passed in file '"
                    + sitePropsFile + "'.");
            err.causedBy(e);
            throw err;
        }
        pw = siteProps.getProperty("epp.client.password");
    }
    virtual ~TestEnvironment() { }

    // SessionProperties API [begin]
    std::string getHostname() const {
        return siteProps.getProperty("epp.server.hostname");
    }
    int getPort() const
    {
        return siteProps.getIntProperty("epp.server.port");
    }
    std::string getClientID() const
    {
        return siteProps.getProperty("epp.client.clID");
    }
    std::string getClientPW() const { return pw;}
    std::string getVersion() const { return "1.0";}
    std::string getLanguage() const { return "en";}
    std::vector<std::string> getObjURIs() const
    {
        std::vector<std::string> vec;
        vec.push_back("urn:ietf:params:xml:ns:domain-1.0");
        vec.push_back("urn:ietf:params:xml:ns:contact-1.0");
        vec.push_back("urn:ietf:params:xml:ns:host-1.0");
        return vec;
    }
    std::vector<std::string> getExtURIs() const
    {
        std::vector<std::string> vec;
        vec.push_back("urn:X-au:params:xml:ns:auext-1.1");
        return vec;
    }
    std::string getPrivateKeyFilename() const
    {
        return siteProps.getProperty("ssl.privatekey.location");
    }
    std::string getCertFilename() const
    {
        return siteProps.getProperty("ssl.cert.location");
    }
    std::string getCAFilename() const
    {
        return siteProps.getProperty("ssl.ca.location");
    }
    std::string getPrivateKeyPassphrase() const
    {
        return siteProps.getProperty("ssl.privatekey.pass");
    }
    std::string getSSLVersion() const { return "TLSv1";}
    long getWaitTimeout() const { return 120000;}
    int getCommandLimit() const { return 1000; }
    int getCommandLimit(const CommandType* type) const
    {
        const StandardCommandType* t = dynamic_cast<const StandardCommandType *>(type);
        if (t == NULL) return 10;
        if (t == StandardCommandType::POLL())
        {
            return 5;
        }
        return 1;
    }
    long getCommandLimitInterval() const { return 5000; }
    long getAcquireTimeout() const { return 20000L; }
    // SessionProperties API [end]

    // SessionPoolProperties API [begin]
    int getMaximumPoolSize() const { return 10; }
    int getSocketTimeout() const { return 60000; }
    long getServerTimeout() const { return 600000L; }
    long getClientTimeout() const { return 120000L; }
    // SessionPoolProperties API [end]

    void setClientPW(const std::string& p)
    {
        pw = p;
    }

    bool enforceStrictValidation() const
    {
        return true;
    }

public:
    std::string pw;
    std::string sitePropsFile;
    Properties siteProps;

    TestEnvironment(const TestEnvironment&);
    TestEnvironment& operator=(const TestEnvironment&);
};

#endif // __TEST_ENVIRONMENT_HPP

