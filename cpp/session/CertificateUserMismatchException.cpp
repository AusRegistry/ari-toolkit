#include "session/CertificateUserMismatchException.hpp"
#include "common/ErrorPkg.hpp"

std::vector<std::string> CertificateUserMismatchException::USER_CN_ARR;


CertificateUserMismatchException::CertificateUserMismatchException
		(const std::string &clID, const std::string &cn)
{
	std::vector<std::string> ctor;
	ctor.push_back (clID);
	ctor.push_back (cn);

	const std::vector<std::string> &p = getCertificateUser();
	std::string msg = ErrorPkg::getMessage ("epp.login.fail.auth.match",
											p,
											ctor);

	// LoginException (msg);
}

const std::vector<std::string> & CertificateUserMismatchException::getCertificateUser()
{
	if (USER_CN_ARR.size() == 0)
	{
		USER_CN_ARR.push_back ("<<clID>>");
		USER_CN_ARR.push_back ("<<cn>>");
	}

	return USER_CN_ARR;
}

	
