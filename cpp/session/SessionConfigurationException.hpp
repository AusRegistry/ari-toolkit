#ifndef __SESSION_CONFIGURATION_EXCEPTION_HPP
#define __SESSION_CONFIGURATION_EXCEPTION_HPP

#include "common/EPPException.hpp"

class SessionConfigurationException : public EPPException
{
public:
	SessionConfigurationException (const std::string &msg)
		: EPPException(msg) {};
	EPP_EXCEPTION(SessionConfigurationException);
};


#endif // __SESSION_CONFIGURATION_EXCEPTION_HPP
