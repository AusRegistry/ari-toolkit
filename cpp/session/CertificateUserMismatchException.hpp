#ifndef __CERTIFICATE_USER_MISMATCH_EXCEPTION_HPP
#define __CERTIFICATE_USER_MISMATCH_EXCEPTION_HPP

#include "session/LoginException.hpp"

#include <vector>
#include <string>

class CertificateUserMismatchException : public LoginException
{
public:
	CertificateUserMismatchException 
		(const std::string &msg = "Username does not match certificate common name")
			: LoginException(msg) {};

	CertificateUserMismatchException
		(const std::string &clID, const std::string &pw);

private:
	static std::vector<std::string> USER_CN_ARR;
protected:
	static const std::vector<std::string> & getCertificateUser();
};

#endif // __CERTIFICATE_USER_MISMATCH_EXCEPTION_HPP
