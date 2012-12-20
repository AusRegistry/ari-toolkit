#include "se/DomainUpdateCommand.hpp"
#include "se/DomainAdd.hpp"
#include "se/DomainRem.hpp"

#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"


DomainUpdateCommand::DomainUpdateCommand (const std::string& name,
                                          const std::string* pw,
                                          const DomainAdd* add,
                                          const DomainRem* rem,
                                          const std::string* registrantID)
    : UpdateCommand(StandardObjectType::DOMAIN(), name)
{
    if (add != NULL)
        add->appendToElement (xmlWriter, objElement);
    
    if (rem != NULL)
        rem->appendToElement (xmlWriter, objElement);
        
    if (pw != NULL || registrantID != NULL)
    {
        DOMElement *chg = xmlWriter->appendChild (objElement, "chg");
        
        if (registrantID)
			XMLHelper::setTextContent
				(xmlWriter->appendChild (chg, "registrant"),
				 *registrantID);

        if (pw)
			XMLHelper::setTextContent
				(xmlWriter->appendChild
				 	(xmlWriter->appendChild
                    	(chg, "authInfo"),
				     "pw"),
				 *pw);
    }
}
