#include "se/ReceiveSE.hpp"

#include "common/StringUtils.hpp"

Logger* ReceiveSE::maintLogger;
Logger* ReceiveSE::supportLogger;
Logger* ReceiveSE::userLogger;
Logger* ReceiveSE::debugLogger;

void ReceiveSE::init()
{
    std::string pname = "com.ausregistry.cpptoolkit.se";
	maintLogger = Logger::getLogger(pname + ".maint");
    supportLogger = Logger::getLogger(pname + ".support");
    userLogger = Logger::getLogger(pname + ".user");
    debugLogger = Logger::getLogger(pname + ".debug");
}

std::string ReceiveSE::replaceIndex (const std::string & inputExpr, int index)
{
	return StringUtils::replaceAll (inputExpr,
									"IDX",
									StringUtils::makeString(index));
}
