#include "common/ErrorPkg.hpp"
#include "common/Logger.hpp"
#include "common/StringUtils.hpp"
#include "common/SystemProperties.hpp"

#include <iostream>

#include <sys/types.h>
#include <dirent.h>

using namespace std;

Properties ErrorPkg::properties;
static std::string pname;


void ErrorPkg::init() throw (PropertyConfigException)
{
    pname = "com.ausregistry.cpptoolkit";
    try
    {
        string msgsFile = SystemProperties::getProperty("epp.client.messages.file");
        Logger::getLogger(pname + ".debug")->fine("Using message file: " + msgsFile);

        Logger::getLogger(pname + ".debug")->fine("Loading messages file: " + msgsFile);
        properties.load(msgsFile);
    }
	catch (PropertyNotFoundException& e)
	{
		PropertyConfigException pc("ErrPkg::init failed.");
		pc.causedBy(e);
		throw pc;
	}
}

string ErrorPkg::getMessage (const string &key)
{
	try
	{
		return properties.getProperty(key);
	}
	catch (PropertyNotFoundException)
	{
		Logger::getLogger(pname+".support")->warning
				("Message definition not found for name: " + key);
		return "";
	}
}


string ErrorPkg::getMessage (const string &name,
								  const string &arg,
								  const string &val)
{
	try
	{
		string msg = getMessage(name);
		
		Logger::getLogger(pname+".debug")->finer
				(name + ": " + arg + "= " + val);

		return StringUtils::replaceAll(msg, arg, val);
	}
	catch (ConfigurationError)
	{
		Logger::getLogger(pname+".support")->warning
				("Message definition not found for name: " + name);
		return "";
	}
}
	

string ErrorPkg::getMessage(const string &name,
							const vector<string> &args,
							const vector<string> &vals)
{
	try
	{
		string msg = getMessage (name);

		for (unsigned int i = 0; i < args.size() && i < vals.size(); i++)
		{
			Logger::getLogger(pname+".debug")->fine("replace: " + args[i]);
			Logger::getLogger(pname+".debug")->fine("with:    " + vals[i]);
			msg = StringUtils::replaceAll (msg, args[i], vals[i]);
		}

		return msg;
	}
	catch (ConfigurationError)
	{
		Logger::getLogger(pname+".support")->warning
				("Message definition not found for name: " + name);

		return "";
	}
}

string ErrorPkg::findMessageFile()
{
	string msgsFile = SystemProperties::getProperty("epp.client.messages.file");
	Logger::getLogger(pname+".debug")->config("Using message file: " + msgsFile);
	return msgsFile;
}
