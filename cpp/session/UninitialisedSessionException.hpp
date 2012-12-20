#ifndef __UNINITIALISED_SESSION_EXCEPTION_HPP
#define __UNINITIALISED_SESSION_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include "common/ErrorPkg.hpp"

class UninitialisedSessionException : public EPPException
{
public:
	UninitialisedSessionException ()
		: EPPException(ErrorPkg::getMessage("net.socket.uninitialsed")) {};
	EPP_EXCEPTION(UninitialisedSessionException);
};

#endif // __UNINITIALISED_SESSION_EXCEPTION_HPP
