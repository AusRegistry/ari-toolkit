#ifndef __LOGOUT_EXCEPTION_HPP
#define __LOGOUT_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include <string>

class LogoutException : public EPPException
{
public:
	LogoutException(const std::string &msg = "Error during logout.")
		: EPPException(msg) { }
	EPP_EXCEPTION(LogoutException);
};

#endif // __LOGIN_EXCEPTION_HPP
