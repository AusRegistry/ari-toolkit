#ifndef __DOMAINADD_HPP
#define __DOMAINADD_HPP

#include "se/DomainAddRem.hpp"
#include "se/AddRemType.hpp"

/**
 * Use this to specify attributes to add to a domain object in a domain
 * update EPP command service element.  The DomainUpdateCommand uses an
 * instance of this to build the appropriate elements in order to request the
 * addition of these attributes to a domain object.
 */
class DomainAdd : public DomainAddRem
{
public:
    DomainAdd (const std::vector<std::string> *nameservers,
               const std::vector<std::string> *techContacts,
               const std::vector<std::string> *adminContacts,
               const std::vector<std::string> *billingContacts,
               const std::vector<Status> *statuses)
        : DomainAddRem (AddRemType::ADD(),
                        nameservers, techContacts, adminContacts,
                        billingContacts, statuses) {}
};

#endif // __DOMAINADD_HPP
