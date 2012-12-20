#ifndef __STANDARDCOMMANDTYPE_HPP
#define __STANDARDCOMMANDTYPE_HPP

#include "se/EnumType.hpp"
#include "se/CommandType.hpp"

class StandardCommandType : public EnumType, public CommandType
{
public:
    StandardCommandType(const std::string &name);

    virtual std::string getCommandName() const { return getFirst(); }
	virtual std::string toString() const { return getFirst(); }
    
    static const StandardCommandType* value (const std::string &name);

    static const StandardCommandType* LOGIN();
    static const StandardCommandType* LOGOUT();
    static const StandardCommandType* POLL();
    static const StandardCommandType* CHECK();
    static const StandardCommandType* INFO();
    static const StandardCommandType* CREATE();
    static const StandardCommandType* DELETE();
    static const StandardCommandType* UPDATE();
    static const StandardCommandType* TRANSFER();
    static const StandardCommandType* RENEW();

	static void init();

private:
    static std::vector<const EnumType *>& values();
};

#endif // __STANDARDCOMMANDTYPE_HPP
