#ifndef __USER_PASS_MISMATCH_EXCEPTION_HPP
#define __USER_PASS_MISMATCH_EXCEPTION_HPP

#include "session/LoginException.hpp"

#include <vector>

class UserPassMismatchException : public LoginException
{
public:
	UserPassMismatchException(const std::string& msg = "Incorrect password for specified user")
		: LoginException(msg)
	{ }

	UserPassMismatchException(const std::string& clID, const std::string &pw)
		: LoginException(makeMessage(clID, pw))
	{ }

private:
	static const std::string makeMessage(
			const std::string& clID, const std::string& pw);
};

#endif // __USER_PASS_MISMATCH_EXCEPTION_HPP
