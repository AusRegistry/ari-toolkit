#ifndef __LOGIN_EXCEPTION_HPP
#define __LOGIN_EXCEPTION_HPP

#include "common/EPPException.hpp"

class LoginException : public EPPException
{
public:
	LoginException(const std::string& msg = "Authentication failure in login")
		: EPPException(msg) { }
	EPP_EXCEPTION(LoginException);
};

#endif // __LOGIN_EXCEPTION_HPP
