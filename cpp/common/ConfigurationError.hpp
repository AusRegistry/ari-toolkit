#ifndef __CONFIGURATION_ERROR_HPP
#define __CONFIGURATION_ERROR_HPP

#include "common/EPPException.hpp"

class ConfigurationError : public EPPException
{
public:
	ConfigurationError (const std::string &msg)
		: EPPException (msg) {};

	ConfigurationError (const EPPException &other)
		: EPPException (other.getMessage()) {};
	EPP_EXCEPTION(ConfigurationError);
};

#endif // __CONFIGURATION_ERROR_HPP
