#ifndef AR_UNRENEW_COMMAND_TYPE
#define AR_UNRENEW_COMMAND_TYPE

#include "se/CommandType.hpp"
#include <string>

class ArUnrenewCommandType : public CommandType
{
    public:
        ArUnrenewCommandType() : CommandType(getCommandName()) {};
        std::string getCommandName()  const { return cmdName; };
        std::string toString() const { return cmdName; };

    private:
        static const std::string cmdName;
};

#endif /* AR_UNRENEW_COMMAND_TYPE */

