#include "se/TransferCommand.hpp"
#include "se/StandardCommandType.hpp"
#include "xml/XMLHelper.hpp"
#include "common/ErrorPkg.hpp"


TransferCommand::TransferCommand (const ObjectType* objType,
                                  const TransferOp* operation,
                                  const std::string& ident)
    : ObjectCommand(StandardCommandType::TRANSFER(), objType, ident)
{
    Init(operation);
}

TransferCommand::TransferCommand (const ObjectType* objType,
                                  const TransferOp* operation,
                                  const std::string &ident,
                                  const std::string &pw)
    : ObjectCommand(StandardCommandType::TRANSFER(), objType, ident)
{
    Init(operation);
    appendPW (pw);
}

TransferCommand::TransferCommand (const ObjectType* objType,
                                  const TransferOp* operation,
                                  const std::string &ident,
                                  const Period &period,
                                  const std::string &pw)
    : ObjectCommand(StandardCommandType::TRANSFER(), objType, ident)
{
    Init (operation);
    period.appendPeriod (xmlWriter, objElement);
    appendPW (pw);
}

TransferCommand::TransferCommand (const ObjectType* objType,
                                  const TransferOp* operation,
                                  const std::string& ident,
                                  const std::string& roid,
                                  const std::string& pw)
    : ObjectCommand(StandardCommandType::TRANSFER(), objType, ident)
{
    Init(operation);
    appendPW(pw, roid);
}

TransferCommand::TransferCommand (const ObjectType* objType,
                                  const TransferOp* operation,
                                  const std::string& ident,
                                  const Period& period,
                                  const std::string& roid,
                                  const std::string& pw)
    : ObjectCommand(StandardCommandType::TRANSFER(), objType, ident)
{
    Init(operation);
    period.appendPeriod(xmlWriter, objElement);
    appendPW(pw, roid);
}

void TransferCommand::appendPW(const std::string &pw, const std::string &roid)
{
	XMLHelper::setTextContent(
		xmlWriter->appendChild(
			xmlWriter->appendChild(objElement, "authInfo"),
			"pw",
         	"roid",
         	roid),
		pw);
}

void TransferCommand::appendPW(const std::string &pw)
{
	XMLHelper::setTextContent(
		xmlWriter->appendChild(
			xmlWriter->appendChild(objElement, "authInfo"), "pw"),
		pw);
}

void TransferCommand::Init(const TransferOp* operation)
{
	if (operation == NULL)
	{
		throw IllegalArgException(ErrorPkg::getMessage("se.transfer.operation.missing"));
	}
	XMLHelper::setAttribute (cmdElement,
							 "op", operation->toString());
}
