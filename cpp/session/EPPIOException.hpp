#ifndef __EPP_IO_EXCEPTION_HPP
#define __EPP_IO_EXCEPTION_HPP

#include "common/EPPException.hpp"

class EPPIOException : public EPPException
{
public:
	EPPIOException (const std::string &msg)
		: EPPException (msg)
	{ }
	EPP_EXCEPTION(EPPIOException); 
};

#endif // __EPP_IO_EXCEPTION_HPP
