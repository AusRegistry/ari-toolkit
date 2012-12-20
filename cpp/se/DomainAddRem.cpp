#include "se/DomainAddRem.hpp"
#include "se/AddRemType.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XMLWriter.hpp"

using namespace xercesc;

DomainAddRem::DomainAddRem(const AddRemType* type,
                           const std::vector<std::string> *nameservers,
                           const std::vector<std::string> *techContacts,
                           const std::vector<std::string> *adminContacts,
                           const std::vector<std::string> *billingContacts,
                           const std::vector<Status>      *statuses)
    : type(type->toString())
{
    if (nameservers)
        this->nameservers = *nameservers;

    if (techContacts)        
        this->techContacts = *techContacts;
    
    if (adminContacts)
        this->adminContacts = *adminContacts;
    
    if (billingContacts)
        this->billingContacts = *billingContacts;
    
    if (statuses)
        this->statuses = *statuses;
}

DOMElement * DomainAddRem::appendToElement(XMLWriter *xmlWriter,
                                           DOMElement *parent) const
{
    int listSize = 0;
    
    DOMElement *addRem = xmlWriter->appendChild (parent, type);
    
    if ((listSize = nameservers.size()) > 0)
    {
        DOMElement *ns = xmlWriter->appendChild (addRem, "ns");
        
        for (int i = 0; i < listSize; i++)
			XMLHelper::setTextContent
				(xmlWriter->appendChild (ns, "hostObj"),
				 nameservers[i]);
    }
    
    if ((listSize = techContacts.size()) > 0)            
    {
        for (int i = 0; i < listSize; i++)
            xmlWriter->appendChild (addRem, 
                                    "contact", 
                                    techContacts[i],
                                    "type", 
                                    "tech");
    }
    
    if ((listSize = adminContacts.size()) > 0)
    {
        for (int i = 0; i < listSize; i++)
            xmlWriter->appendChild (addRem,
                                    "contact",
                                    adminContacts[i],
                                    "type",
                                    "admin");
    }

    if ((listSize = billingContacts.size()) > 0)
    {
        for (int i = 0; i < listSize; i++)
            xmlWriter->appendChild (addRem,
                                    "contact",
                                    billingContacts[i],
                                    "type",
                                    "billing");
    }
    
    if ((listSize = statuses.size()) > 0)
    {
        for (int i = 0; i < listSize; i++)
        {
            const Status &status = statuses[i];
            
            DOMElement *s = 
                xmlWriter->appendChild (addRem,
                                        "status",
                                        status.getRationale(),
                                        "s",
                                        status.toString());
            if (status.getLanguage() != "")
				XMLHelper::setAttribute (s, "lang", status.getLanguage());
        }
        // End for
    }
    
    return addRem;
}
