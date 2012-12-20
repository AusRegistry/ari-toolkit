#include "se/ArDomainPolicyDeleteCommand.hpp"
#include "se/ArExtension.hpp"
#include "se/ArDomainObjectType.hpp"
#include "se/CommandType.hpp"
#include "se/Extension.hpp"
#include "xml/XMLHelper.hpp"

#include <string>

namespace {
    class ArDomainPolicyDeleteCommandType : public CommandType
    {
        public:
            ArDomainPolicyDeleteCommandType() : CommandType(getCommandName()) { }
            std::string getCommandName() const { return "policyDelete"; }
            std::string toString() const { return "policyDelete"; }
    };

    Extension& arExtension() {
        static Extension* arExt = new ArExtension();
        return *arExt;
    }

    const ArDomainPolicyDeleteCommandType polDeleteCmdType;
    const ArDomainObjectType ardomType;
}; // anonymous namespace

ArDomainPolicyDeleteCommand::ArDomainPolicyDeleteCommand (
        const std::string &name, const std::string &reason) : ProtocolExtensionCommand(
            &polDeleteCmdType, &ardomType, name, arExtension())
{
    XMLHelper::setTextContent(
            xmlWriter->appendChild(objElement, "reason"), reason);
}

