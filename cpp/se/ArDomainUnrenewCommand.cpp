#include "se/ArDomainUnrenewCommand.hpp"

#include "se/CommandType.hpp"
#include "se/ArExtension.hpp"
#include "se/ArDomainObjectType.hpp"
#include "se/EPPDateFormatter.hpp"
#include "xml/XMLHelper.hpp"
#include "se/XMLGregorianCalendar.hpp"

namespace {
    class ArUnrenewCommand : public CommandType
    {
        public:
            ArUnrenewCommand() : CommandType (getCommandName()) { }
            std::string getCommandName() const { return "unrenew"; }
            std::string toString() const { return "unrenew"; }
    };

    Extension& arExtension() {
        static Extension* arExt = new ArExtension();
        return *arExt;
    }

    const ArUnrenewCommand unrenewCmdType;
    const ArDomainObjectType ardomType;
}; // anonymous namespace

ArDomainUnrenewCommand::ArDomainUnrenewCommand(
        const std::string &name,
        const XMLGregorianCalendar& exDate) : ProtocolExtensionCommand(
            &unrenewCmdType, &ardomType, name, arExtension())
{
    std::string exDateStr = EPPDateFormatter::toXSDate(exDate);
    XMLHelper::setTextContent
        (xmlWriter->appendChild(objElement, "curExpDate"), exDateStr);
}

