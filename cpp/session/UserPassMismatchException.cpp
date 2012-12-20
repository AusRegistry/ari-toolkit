#include "session/UserPassMismatchException.hpp"
#include "common/ErrorPkg.hpp"

const std::string UserPassMismatchException::makeMessage(
		const std::string &clID, const std::string &pw)
{
	std::vector<std::string> args;
	args.push_back("<<clID>>");
	args.push_back("<<pw>>");

	std::vector<std::string> vals;
	vals.push_back(clID);
	vals.push_back(pw);

	return ErrorPkg::getMessage("epp.login.fail.auth.pw", args, vals);
}
