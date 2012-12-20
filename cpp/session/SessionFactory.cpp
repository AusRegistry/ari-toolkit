#include "session/SessionFactory.hpp"

#include "session/Session.hpp"
#include "session/TLSSession.hpp"
#include "session/SessionProperties.hpp"

Session* SessionFactory::newInstance (SessionProperties* props)
		throw (SessionConfigurationException)
{
    return new TLSSession(props);
}
