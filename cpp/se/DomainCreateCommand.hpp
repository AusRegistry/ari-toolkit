#ifndef __DOMAINCREATECOMMAND_HPP
#define __DOMAINCREATECOMMAND_HPP

#include "se/CreateCommand.hpp"
#include "se/Period.hpp"

/**
 * Mapping of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified in
 * RFC3731.  Command-response extensions to the domain:create command are
 * implemented as subclasses of this.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 */
class DomainCreateCommand : public CreateCommand
{
public:
    /**
	 * Constructor for a domain:create EPP command.  All core EPP domain:create
	 * attributes may be set using this constructor.
     */
    DomainCreateCommand (const std::string& name,
                         const std::string& pw,
                         const std::string* registrantID,
                         const std::vector<std::string>* techContacts = NULL,
                         const std::vector<std::string>* nameservers = NULL,
                         const std::vector<std::string>* adminContacts = NULL,
                         const std::vector<std::string>* billingContacts = NULL,
                         const Period* period = NULL);
};

#endif // __DOMAINCREATECOMMAND_HPP
