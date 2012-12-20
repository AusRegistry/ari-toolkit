#include "se/ArDomainPolicyUndeleteCommand.hpp"
#include "se/ArExtension.hpp"
#include "se/ArDomainObjectType.hpp"
#include "se/CommandType.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XStr.hpp"

namespace {
    class ArPolicyUndeleteCommandType : public CommandType
    {
        public:
            ArPolicyUndeleteCommandType() : CommandType(getCommandName()) { }
            std::string getCommandName() const { return "policyUndelete"; }
            std::string toString() const { return "policyUndelete"; }
    };

    Extension& arExtension() {
        static Extension* arExt = new ArExtension();
        return *arExt;
    }

    const ArPolicyUndeleteCommandType polUndeleteCmdType;
    const ArDomainObjectType ardomType;
}; // anonymous namespace

ArDomainPolicyUndeleteCommand::ArDomainPolicyUndeleteCommand(
        const std::string &name) : ProtocolExtensionCommand(
            &polUndeleteCmdType, &ardomType, name, arExtension())
{
}

