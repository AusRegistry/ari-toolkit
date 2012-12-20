#ifndef __SESSION_LIMIT_EXCEEDED_EXCEPTION_HPP
#define __SESSION_LIMIT_EXCEEDED_EXCEPTION_HPP

#include "session/LoginException.hpp"

class SessionLimitExceededException : public LoginException
{
public:
	SessionLimitExceededException(const std::string &msg = "Session Limit Exceeded")
		: LoginException(msg) { };
};

#endif // __SESSION_LIMIT_EXCEEDED_EXCEPTION_HPP
