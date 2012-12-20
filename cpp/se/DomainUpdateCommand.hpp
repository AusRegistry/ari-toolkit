#ifndef __DOMAINUPDATECOMMAND_HPP
#define __DOMAINUPDATECOMMAND_HPP

#include "se/UpdateCommand.hpp"

class DomainAdd;
class DomainRem;

/** 
 * Use this to request the update of a domain object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3731 compliant
 * domain update EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a Response object.
 *
 * @see Response
 */
class DomainUpdateCommand : public UpdateCommand
{
public:
    /**
     * The set of attributes of a domain which may be updated as per
     * RFC3731.
     */
    DomainUpdateCommand (const std::string& name,
                         const std::string* pw = NULL,
                         const DomainAdd* add = NULL,
                         const DomainRem* rem = NULL,
                         const std::string* registrantID = NULL);
};
#endif // __DOMAINUPDATECOMMAND_HPP
