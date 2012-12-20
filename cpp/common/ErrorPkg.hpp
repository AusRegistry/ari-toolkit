#ifndef __ERROR_PKG_HPP
#define __ERROR_PKG_HPP

#include "common/Properties.hpp"
#include "common/ConfigurationError.hpp"
#include "common/Logger.hpp"
#include <string>
#include <vector>

class ErrorPkg
{
public:
	static std::string getMessage(const std::string& msg);

	static std::string getMessage(const std::string& msg,
								  const std::string& arg,
								  const std::string& val);

	static std::string getMessage(const std::string& msg,
								  const std::vector<std::string>& args,
								  const std::vector<std::string>& vals);
	static void init() throw (PropertyConfigException);
private:

	static std::string getMessageInternal(const std::string& msg);

	static Properties properties;

	static std::string findMessageFile();
};

#endif // __ERROR_PKG_HPP
