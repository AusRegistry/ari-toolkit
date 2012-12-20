#include "se/ArDomainUndeleteCommand.hpp"

#include "se/ArExtension.hpp"
#include "se/ArDomainObjectType.hpp"
#include "se/CommandType.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XStr.hpp"

namespace {
    class ArUndeleteCommandType : public CommandType
    {
        public:
            ArUndeleteCommandType() : CommandType (getCommandName()) {}
            std::string getCommandName() const { return "undelete"; }
            std::string toString() const { return "undelete"; }
    };    

    Extension& arExtension() {
        static Extension* arExt = new ArExtension();
        return *arExt;
    }

    const ArUndeleteCommandType undeleteCmdType;
    const ArDomainObjectType ardomType;
}; // anonymous namespace

ArDomainUndeleteCommand::ArDomainUndeleteCommand(
        const std::string &name) : ProtocolExtensionCommand(
            &undeleteCmdType, &ardomType, name, arExtension())
{
}

