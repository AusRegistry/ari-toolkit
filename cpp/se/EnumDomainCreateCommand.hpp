#ifndef __ENUMDOMAINCREATECOMMAND_HPP
#define __ENUMDOMAINCREATECOMMAND_HPP

#include "se/DomainCreateCommand.hpp"
#include "se/NAPTR.hpp"
#include "se/Period.hpp"

#include <string>
#include <vector>

/**
 * Use this to request provisioning of an ENUM domain object in an EPP
 * Registry.  Instances of this class generate domain create EPP service
 * elements compliant with RFCs 3730, 3731 and 4114 via the toXML method.
 */
class EnumDomainCreateCommand : public DomainCreateCommand
{
public:
    /**
     * Minimal constructor for creating a domain:create + e164epp:create
     * EPP command.  These parameters are the least required for a valid
     * ENUM domain create command.
     */
    EnumDomainCreateCommand (const std::string &name,
                             const std::string &pw,
                             const std::string *registrantID,
                             const std::vector<std::string> &techContacts);
    /**
     * Construct a domain:create + e164epp:create EPP command with NAPTR
     * records.  This is the least information required to provision an ENUM
     * domain with NAPTR records.
     */
    EnumDomainCreateCommand (const std::string &name,
                             const std::string &pw,
                             const std::string *registrantID,
                             const std::vector<std::string> &techContacts,
                             const std::vector<NAPTR> *naptrs);
    /**     
     * Full data specification constructor for a domain:create + e164epp:create
     * EPP command with NAPTR records.  Please refer to the
     * urn:ietf:params:xml:ns:e164epp-1.0 schema for specification of the
     * required fields.
     */     
    EnumDomainCreateCommand (const std::string &name,
                             const std::string &pw,
                             const std::string *registrantID,
                             const std::vector<std::string> &techContacts,
                             const std::vector<std::string> &adminContacts,
                             const std::vector<std::string> &billingContacts,
                             const std::vector<NAPTR> *naptrs,
                             const Period &period);
    /**         
     * Full data specification constructor for a domain:create + e164epp:create
     * EPP command with nameservers rather than NAPTR records.  This
     * constructor does not cause the e164epp extension element to be created,
     * since NAPTR records are not specified.
     */     
    EnumDomainCreateCommand (const std::string &name,
                             const std::string &pw,
                             const std::string *registrantID,
                             const std::vector<std::string> &techContacts,
                             const std::vector<std::string> &adminContacts,
                             const std::vector<std::string> &billingContacts,
                             const std::vector<std::string> &nameservers,
                             const Period &period);
private:
    void setExtension (const std::vector<NAPTR> * naptrs);
};

#endif // __ENUMDOMAINCREATECOMMAND_HPP
