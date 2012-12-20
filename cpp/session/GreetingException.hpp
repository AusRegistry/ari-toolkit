#ifndef __GREETING_EXCEPTION_HPP
#define __GREETING_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include <string>

class GreetingException : public EPPException
{
public:
	GreetingException (const std::string &msg)
		: EPPException(msg) {};
	EPP_EXCEPTION(GreetingException);
};

#endif // __GREETING_EXCEPTION_HPP
