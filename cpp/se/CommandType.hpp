#ifndef __COMMANDTYPE_HPP
#define __COMMANDTYPE_HPP

#include "common/StringUtils.hpp"
#include <string>

/**
 * Each EPP command is identified by an instance of CommandType.  A CommandType
 * has a command name, which is the name of the corresponding EPP command, and
 * a response name, which is the name of the specific EPP response appropriate
 * to this command type, if any.
 * 
 */
class CommandType
{
public:
	CommandType (const std::string& hashRef)
		: hashVal (StringUtils::hashCode(hashRef)) {};

	virtual ~CommandType() { }

    virtual std::string getCommandName() const = 0;
    virtual std::string toString() const = 0;

	StringUtils::HashType hash() const { return hashVal; };

private:
	StringUtils::HashType hashVal;
};

#endif // __COMMANDTYPE_HPP
