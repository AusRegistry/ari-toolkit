#ifndef __FATAL_SESSION_EXCEPTION_HPP
#define __FATAL_SESSION_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include "common/ErrorPkg.hpp"

class FatalSessionException : public EPPException
{
public:
	FatalSessionException(
		const std::string &msg = ErrorPkg::getMessage("EPP.session.open.fatalerr"))
	: EPPException(msg) { }
	EPP_EXCEPTION(FatalSessionException);
};

#endif // __FATAL_SESSION_EXCEPTION_HPP
