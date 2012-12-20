#include "session/SessionManagerFactory.hpp"
#include "session/SessionManagerImpl.hpp"

using namespace std;

#if 0
SessionManager* 
SessionManagerFactory::newInstance(const std::string& propertiesFile)
		throw (ConfigurationException, EPPIOException)
{
	auto_ptr<SessionManagerProperties> properties(new SessionManagerProperties(propertiesFile));
	properties->load();
	return newInstance(properties.release());
}
#endif

SessionManager*
SessionManagerFactory::newInstance(SessionManagerProperties* properties)
	throw (ConfigurationException)
{
	return new SessionManagerImpl(properties);
}
