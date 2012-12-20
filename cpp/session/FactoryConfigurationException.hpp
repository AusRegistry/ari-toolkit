#ifndef __FACTORY_CONFIGURATION_EXCEPTION_HPP
#define __FACTORY_CONFIGURATION_EXCEPTION_HPP

#include "session/ConfigurationException.hpp"

class FactoryConfigurationException : public ConfigurationException
{
public:
	FactoryConfigurationException(const std::string &msg)
			: ConfigurationException(msg) {};
};

#endif // __FACTORY_CONFIGURATION_EXCEPTION_HPP
