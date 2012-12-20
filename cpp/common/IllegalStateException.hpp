#ifndef __ILLEGAL_STATE_EXCEPTION_HPP
#define __ILLEGAL_STATE_EXCEPTION_HPP

#include "common/EPPException.hpp"

class IllegalStateException : public EPPException
{
public:
	IllegalStateException(const std::string &msg)
		: EPPException (msg)
	{ }
	EPP_EXCEPTION(IllegalStateException);
};

#endif // __ILLEGAL_ARGUMENT_EXCEPTION_HPP
