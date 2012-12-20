#ifndef __ILLEGAL_ARGUMENT_EXCEPTION_HPP
#define __ILLEGAL_ARGUMENT_EXCEPTION_HPP

#include "common/EPPException.hpp"

class IllegalArgException : public EPPException
{
public:
	IllegalArgException(const std::string &msg)
		: EPPException (msg)
	{ }
	EPP_EXCEPTION(IllegalArgException);
};

#endif // __ILLEGAL_ARGUMENT_EXCEPTION_HPP
