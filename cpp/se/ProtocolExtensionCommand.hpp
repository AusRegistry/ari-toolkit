#ifndef __PROTOCOLEXTENSIONCOMMAND_HPP
#define __PROTOCOLEXTENSIONCOMMAND_HPP

#include "se/SendSE.hpp"
#include "se/CommandType.hpp"
#include "se/ObjectType.hpp"
#include "se/Extension.hpp"
#include "xml/XStr.hpp"

#include <xercesc/dom/DOMElement.hpp>

/**
 * Base class for AusRegistry protocol extension commands.  Instances of this
 * class are responsible for building the part of the XML DOM tree common to
 * all arext-1.0 protocol extension commands.
 */
class ProtocolExtensionCommand : public SendSE
{
public:
    ProtocolExtensionCommand(
            const CommandType* commandType,
            const ObjectType* objectType,
            const std::string& ident,
            const Extension& ext);

        // We can not use the xmlWriter API until the base class is
        // constructed, so we disable the default command element
        // creation in the base class initialised for Command and do
        // our own construction here.

protected:
    DOMElement *command;
    DOMElement *cmdElement;
    DOMElement *objElement;
    const CommandType *cmdType;
    const ObjectType *objType;
    const Extension& extension;

private:
    virtual std::string toXMLImpl();
};

#endif // __PROTOCOLEXTENSIONCOMMAND_HPP

