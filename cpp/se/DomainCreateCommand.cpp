#include "se/DomainCreateCommand.hpp"
#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"

                                          
DomainCreateCommand::DomainCreateCommand (const std::string& name,
                                          const std::string& pw,
                                          const std::string* registrantID,
                                          const std::vector<std::string>* techContacts,
                                          const std::vector<std::string>* nameservers,
                                          const std::vector<std::string>* adminContacts,
                                          const std::vector<std::string>* billingContacts,
                                          const Period* period)
    : CreateCommand(StandardObjectType::DOMAIN(), name)
{
    std::vector<std::string>::const_iterator p;

    if (period)
        period->appendPeriod (xmlWriter, objElement);
    
    if (nameservers)
    {
        DOMElement *ns = xmlWriter->appendChild (objElement, "ns");
        for (p = nameservers->begin(); p != nameservers->end(); p++)        
			XMLHelper::setTextContent
				(xmlWriter->appendChild (ns, "hostObj"), *p);
    }
    
	if (registrantID)
	{
		XMLHelper::setTextContent
			(xmlWriter->appendChild(objElement, "registrant"), *registrantID);
	}
    
    if (adminContacts)
        for (p = adminContacts->begin(); p != adminContacts->end(); p++)
            xmlWriter->appendChild(objElement, "contact", *p, "type", "admin");
    
    if (techContacts)
        for (p = techContacts->begin(); p != techContacts->end(); p++)
            xmlWriter->appendChild(objElement, "contact", *p, "type", "tech");
    
    if (billingContacts)
        for (p = billingContacts->begin(); p != billingContacts->end(); p++)
            xmlWriter->appendChild(objElement, "contact", *p, "type", "billing");
    
	XMLHelper::setTextContent
		(xmlWriter->appendChild
        	(xmlWriter->appendChild
            	(objElement,
             	"authInfo"),
         	"pw"),
		 pw);
}
