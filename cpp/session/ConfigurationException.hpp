#ifndef __CONFIGURATION_EXCEPTION_HPP
#define __CONFIGURATION_EXCEPTION_HPP

#include "common/EPPException.hpp"

class ConfigurationException : public EPPException
{
public:
	ConfigurationException (const std::string &msg)
		: EPPException(msg) {};
	EPP_EXCEPTION(ConfigurationException);
};

#endif // __CONFIGURATION_EXCEPTION_HPP
