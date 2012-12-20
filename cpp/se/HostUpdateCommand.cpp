#include "se/HostUpdateCommand.hpp"
#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"


HostUpdateCommand::HostUpdateCommand (const std::string &name,
                                      const HostAddRem *add,
                                      const HostAddRem *rem,
                                      const std::string *newName)
    : UpdateCommand(StandardObjectType::HOST(), name)
{
    if (add)
    {
        DOMElement *addElement = xmlWriter->appendChild (objElement, "add");
        add->appendToElement (xmlWriter, addElement);
    }
    
    if (rem)
    {
        DOMElement *remElement = xmlWriter->appendChild (objElement, "rem");
        rem->appendToElement (xmlWriter, remElement);
    }
    
    if (newName)
    {
        DOMElement *chgElement = xmlWriter->appendChild (objElement, "chg");
		XMLHelper::setTextContent
			(xmlWriter->appendChild (chgElement, "name"), *newName);
    }
}
