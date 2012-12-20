#ifndef __DOMAINADDREM_HPP
#define __DOMAINADDREM_HPP

#include <xercesc/dom/DOMElement.hpp>
#include "se/Appendable.hpp"
#include "se/Status.hpp"
#include <vector>
#include <string>

class AddRemType;

/**
 * Specification of how to write the add and rem elements to a domain update
 * command.  Use subclasses of this to set attributes to add or remove from a
 * domain object.
 */
class DomainAddRem : public Appendable
{
public:
    /**
     * Maximal specification of the attribute values which may be added or
     * removed from a domain. Each of the parameters is optional, but at least
     * one must be specified.
     */
    DomainAddRem(const AddRemType* type,
                 const std::vector<std::string>* nameservers,
                 const std::vector<std::string>* techContacts,
                 const std::vector<std::string>* adminContacts,
                 const std::vector<std::string>* billingContacts,
                 const std::vector<Status>* statuses);
	virtual ~DomainAddRem(){};
    
    virtual xercesc::DOMElement* appendToElement(
			XMLWriter *xmlWriter, xercesc::DOMElement *parent) const;

private:
    std::string type;
    std::vector<std::string> nameservers;
    std::vector<std::string> techContacts;
    std::vector<std::string> adminContacts;
    std::vector<std::string> billingContacts;
    
    std::vector<Status> statuses;
};

#endif // __DOMAINADDREM_HPP
