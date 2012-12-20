#ifndef REGISTRANT_TRANSFER_COMMAND_TYPE
#define REGISTRANT_TRANSFER_COMMAND_TYPE

#include "se/CommandType.hpp"
#include <string>

class RegistrantTransferCommandType : public CommandType
{
    public:
        RegistrantTransferCommandType() : CommandType(getCommandName()) {};
        std::string getCommandName()  const { return cmdName; };
        std::string toString() const { return cmdName; };

    private:
        static const std::string cmdName;
};

#endif /* REGISTRANT_TRANSFER_COMMAND_TYPE */

