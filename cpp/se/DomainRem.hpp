#ifndef __DOMAINREM_HPP
#define __DOMAINREM_HPP

#include "se/DomainAddRem.hpp"

/**
 * Use this to specify attributes to remove from a domain object in a domain
 * update EPP command service element.  The DomainUpdateCommand uses an
 * instance of this to build the appropriate elements in order to request the
 * removal of these attributes from a domain object.
 */
class DomainRem : public DomainAddRem
{
public:
    DomainRem(const std::vector<std::string> *nameservers,
              const std::vector<std::string> *techContacts,
              const std::vector<std::string> *adminContacts,
              const std::vector<std::string> *billingContacts,
              const std::vector<Status> *statuses)
        : DomainAddRem(AddRemType::REM(),
                       nameservers, techContacts, adminContacts,
                       billingContacts, statuses)
	{ }
};

#endif // __DOMAINREM_HPP
