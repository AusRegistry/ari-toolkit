#include <string>
#include <exception>

#include "se/CLTRID.hpp"
#include "se/Command.hpp"
#include "se/CommandType.hpp"
#include "se/IllegalArgException.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XMLParser.hpp"
#include "common/ErrorPkg.hpp"

Command::Command(const CommandType* commandType)
    : extensionElement(NULL), cmdType(commandType)
{
    if (commandType == NULL)
    {
        throw IllegalArgException(
                ErrorPkg::getMessage("se.command.type.missing"));
    }

    command = xmlWriter->appendChild(xmlWriter->getRoot(), "command");
    cmdElement = xmlWriter->appendChild(command, cmdType->getCommandName());
}

std::string Command::toXMLImpl()
{
    XMLHelper::setTextContent(
        xmlWriter->appendChild (command, "clTRID"),
        CLTRID::nextVal());

    return xmlWriter->toXML();
}

void Command::appendExtension(const CommandExtension& extension)
{
    try
    {
        if (extensionElement == NULL)
        {
            extensionElement = xmlWriter->appendChild(command, "extension");
        }

        extension.addToCommand(*this);
    }
    catch (std::exception &e)
    {
        userLogger->warning("Exception while appending an extension to command of type \'"
                + cmdType->getCommandName() + "\': " + e.what());
        throw;
    }
    catch (...)
    {
        userLogger->warning("Unknown exception while appending an extension to command of type \'"
                + cmdType->getCommandName() + "\'");
        throw;
    }
}
