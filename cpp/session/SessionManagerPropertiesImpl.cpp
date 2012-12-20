#include "SessionManagerPropertiesImpl.hpp"
#include <sstream>
#include <limits>

using namespace std;

SessionManagerPropertiesImpl::SessionManagerPropertiesImpl(const string& propertiesFile)
{
	filename = propertiesFile;
	properties.load(propertiesFile);
}

SessionManagerPropertiesImpl::~SessionManagerPropertiesImpl()
{ }

string SessionManagerPropertiesImpl::getHostname() const
{
    return properties.getProperty("epp.server.hostname");
}

int SessionManagerPropertiesImpl::getPort() const
{
    return properties.getIntProperty("epp.server.port", 700);
}

string SessionManagerPropertiesImpl::getClientID() const
{
    return properties.getProperty("epp.client.clID");
}

void SessionManagerPropertiesImpl::setClientPW(const string& pw)
{
    properties.setProperty("epp.client.password", pw);
}

string SessionManagerPropertiesImpl::getClientPW() const
{
    return properties.getProperty("epp.client.password");
}

string SessionManagerPropertiesImpl::getVersion() const
{
    return properties.getProperty("epp.client.options.version", "1.0");
}

string SessionManagerPropertiesImpl::getLanguage() const
{
    return properties.getProperty("epp.client.options.lang", "en");
}

namespace {

	typedef vector<pair<string,string> > pairs;
	vector<string> map_second(const pairs& p)
	{
		vector<string> res;
		for (pairs::const_iterator i = p.begin(); i != p.end(); ++i)
		{
			res.push_back(i->second);
		}
		return res;
	}
}

vector<string>
SessionManagerPropertiesImpl::getObjURIs() const
{
	return map_second(properties.getProperties("xml.uri.obj"));
}

vector<string>
SessionManagerPropertiesImpl::getExtURIs() const
{
	return map_second(properties.getProperties("xml.uri.ext"));
}

string SessionManagerPropertiesImpl::getPrivateKeyFilename() const
{
    return properties.getProperty("ssl.privatekey.location", "key.pem");
}

string SessionManagerPropertiesImpl::getCertFilename() const
{
    return properties.getProperty("ssl.cert.location", "cert.pem");
}

string SessionManagerPropertiesImpl::getCAFilename() const
{
    return properties.getProperty("ssl.ca.location", "cacert.pem");
}

string SessionManagerPropertiesImpl::getPrivateKeyPassphrase() const
{
    return properties.getProperty("ssl.privatekey.pass");
}

string SessionManagerPropertiesImpl::getSSLVersion() const
{
    return properties.getProperty("ssl.protocol", "TLSv1");
}

int SessionManagerPropertiesImpl::getCommandLimit(const CommandType* type) const
{
	const string propName = "epp.server.command.limit." + type->getCommandName();
	return properties.getIntProperty(propName, 5);
}

int SessionManagerPropertiesImpl::getCommandLimit() const
{
	const string propName = "epp.server.command.limit";
	return properties.getIntProperty(propName, numeric_limits<int>::max());
}

long SessionManagerPropertiesImpl::getCommandLimitInterval() const
{
	const string propName = "epp.server.command.limit.interval";
	return properties.getLongProperty(propName, 1000L);
}

int SessionManagerPropertiesImpl::getMaximumPoolSize() const
{
	return properties.getIntProperty("epp.client.session.count.max", 5);
}

long SessionManagerPropertiesImpl::getWaitTimeout() const
{
	return properties.getLongProperty("thread.wait.timeout", 120000);
}

long SessionManagerPropertiesImpl::getServerTimeout() const
{
	return properties.getLongProperty("net.server.timeout", 600000);
}

long SessionManagerPropertiesImpl::getClientTimeout() const
{
	return properties.getLongProperty("net.client.timeout", 12000000);
}

long SessionManagerPropertiesImpl::getAcquireTimeout() const
{
	return properties.getLongProperty("session.acquire.timeout", 20000);
}

int SessionManagerPropertiesImpl::getSocketTimeout() const
{
    return properties.getIntProperty("net.socket.timeout", 60000);
}

bool SessionManagerPropertiesImpl::enforceStrictValidation() const
{
	return properties.getBooleanProperty("xml.validation.enable", true);
}
