#ifndef __EPP_INTERRUPTED_EXCEPTION_HPP
#define __EPP_INTERRUPTED_EXCEPTION_HPP

#include "common/EPPException.hpp"

class EPPInterruptedException : public EPPException
{
public:
	EPPInterruptedException (const std::string &msg)
		: EPPException (msg)
	{ }
	EPP_EXCEPTION(EPPInterruptedException);
};

#endif // __EPP_INTERRUPTED_EXCEPTION_HPP
