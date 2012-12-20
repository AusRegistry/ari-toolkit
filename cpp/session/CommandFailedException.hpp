#ifndef __COMMAND_FAILED_EXCEPTION_HPP
#define __COMMAND_FAILED_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include "common/ErrorPkg.hpp"

class CommandFailedException : public EPPException
{
public:
	CommandFailedException(
		const std::string& msg = ErrorPkg::getMessage("EPP.server.cmdfail"))
		: EPPException(msg) { }
	EPP_EXCEPTION(CommandFailedException);
};

#endif // __COMMAND_FAILED_EXCEPTION_HPP
