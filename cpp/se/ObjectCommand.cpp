#include "se/ObjectCommand.hpp"
#include "xml/XStr.hpp"
#include "se/CommandType.hpp"
#include "se/IllegalArgException.hpp"
#include "common/ErrorPkg.hpp"

ObjectCommand::ObjectCommand (const CommandType* commandType,
                              const ObjectType* objectType,
                              const std::string& ident)
    : Command(commandType), objType(objectType)
{
    const std::vector<std::string> idents (1, ident);
    
    Init(commandType, objectType, idents);
}

ObjectCommand::ObjectCommand (const CommandType* commandType,
                              const ObjectType* objectType,
                              const std::vector<std::string>& idents)
    : Command(commandType), objType(objectType)
{
    Init(commandType, objectType, idents);
}

void ObjectCommand::Init(const CommandType* commandType,
                         const ObjectType* objectType,
                         const std::vector<std::string>& idents)
{
	if (commandType == NULL || objectType == NULL || idents.size() == 0)
	{
		throw IllegalArgException(ErrorPkg::getMessage("se.object.missing_arg"));
	}
    objElement = xmlWriter->appendChild(cmdElement,
                                        commandType->getCommandName(),
                                        objectType->getURI());
	objElement->setAttribute(
			XStr("xsi:schemaLocation").str(),
			XStr(objType->getSchemaLocation()).str());
    
    xmlWriter->appendChildren(objElement, objectType->getIdentType(), idents);
}

