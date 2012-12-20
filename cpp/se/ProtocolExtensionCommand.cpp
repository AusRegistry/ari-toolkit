#include "common/ErrorPkg.hpp"
#include "se/CLTRID.hpp"
#include "se/IllegalArgException.hpp"
#include "se/ProtocolExtensionCommand.hpp"
#include "xml/XMLHelper.hpp"

ProtocolExtensionCommand::ProtocolExtensionCommand(
        const CommandType* commandType,
        const ObjectType* objectType,
        const std::string& ident,
        const Extension& ext) : SendSE(),
                                cmdType(commandType),
                                objType(objectType),
                                extension(ext)
{
    if (commandType == NULL)
    {
        throw IllegalArgException(
                ErrorPkg::getMessage("se.command.type.missing"));
    }
    cmdType = commandType;

    command = xmlWriter->appendChild(
            xmlWriter->appendChild(
                xmlWriter->getRoot(),
                "extension"),
            "command",
            extension.getURI());

    command->setAttribute(XStr("xsi:schemaLocation").str(),
            XStr(extension.getSchemaLocation()).str());

    cmdElement = xmlWriter->appendChild(command, cmdType->getCommandName());

    objElement = xmlWriter->appendChild(cmdElement,
            commandType->getCommandName(),
            objectType->getURI());
    objElement->setAttribute(
            XStr("xsi:schemaLocation").str(),
            XStr(objType->getSchemaLocation()).str());

    DOMElement* element = xmlWriter->appendChild(
            objElement, objectType->getIdentType());
    XMLHelper::setTextContent(element, ident);
}

std::string ProtocolExtensionCommand::toXMLImpl()
{
    XMLHelper::setTextContent(
        xmlWriter->appendChild(command, "clTRID"),
        CLTRID::nextVal());

    return xmlWriter->toXML();
}

