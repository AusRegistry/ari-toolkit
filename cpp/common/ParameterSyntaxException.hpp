#ifndef __PARAMETER_SYNTAX_EXCEPTION_HPP
#define __PARAMETER_SYNTAX_EXCEPTION_HPP

#include "common/EPPException.hpp"

class ParameterSyntaxException : public EPPException
{
public:
	ParameterSyntaxException (const std::string &msg)
		: EPPException (msg) {};
	EPP_EXCEPTION(ParameterSyntaxException);
};

#endif // __PARAMETER_SYNTAX_EXCEPTION_HPP
