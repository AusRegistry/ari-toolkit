#ifndef __SESSIONFACTORY_H
#define __SESSIONFACTORY_H

#include "session/SessionConfigurationException.hpp"

class Session;
class SessionProperties;

class SessionFactory
{
public:
    static Session* newInstance(SessionProperties* props) 
		throw (SessionConfigurationException);

#if 0
	// Future enhancement opportunity: use e.g. dlopen to load an alternate
	// implememtation of the Session interface.  SessionFactory currently
	// supports only the TLSSession implementation.
    static Session* newInstance 
		(const SessionProperties &p, 
	     const std::string &className)
			throw (FactoryConfigurationException, SessionConfigurationException);
#endif
};

#endif  // __SESSIONFACTORY_H
