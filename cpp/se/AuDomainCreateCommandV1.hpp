#ifndef __AUDOMAINCREATECOMMANDV1_HPP
#define __AUDOMAINCREATECOMMANDV1_HPP

#include "common/Deprecated.hpp"
#include "se/DomainCreateCommand.hpp"

/**
 * Extension of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified
 * in RFC3731 to urn:au:params:xml:ns:auext-1.0.  .au domains must be
 * provisioned using this class rather than {@link
 * com.ausregistry.jtoolkit2.se.DomainCreateCommand}, as the au extension data
 * is mandatory.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 * The response expected from a server should be handled by a {@link
 * com.ausregistry.jtoolkit2.se.DomainCreateResponse} object.
 *
 * @deprecated AU eligibility extensions should now be managed through the
 *             @c <kvlist> extension defined in the
 *             <tt>urn:X-ar:params:xml:ns:kv-1.0</tt> namespace. This can be done
 *             through the toolkit by using a @c DomainCreateCommand and
 *             appending a @c DomainKVCommandExtension object containing
 *             the AU eligibility extensions.
 *
 *             See
 *             {@link DomainCreateCommand.appendExtension(CommandExtension)}
 *             and
 *             {@link DomainKVCommandExtension}.
 */
class AuDomainCreateCommandV1 : public DomainCreateCommand
{
public:
    AuDomainCreateCommandV1(
            const std::string& name,
            const std::string& pw,
            const std::string* registrantID,
            const std::vector<std::string>* techContacts,
            const std::string& auEligibilityType,
            int auPolicyReason,
            const std::string& auRegistrantName);

    AuDomainCreateCommandV1(
            const std::string& name,
            const std::string& pw,
            const std::string* registrantID,
            const std::vector<std::string>* techContacts,
            std::vector<std::string>* adminContacts,
            std::vector<std::string>* billingContacts,
            std::vector<std::string>* nameservers,
            const std::string &auEligibilityType,
            int auPolicyReason,
            const std::string& auRegistrantName,
            std::string* auRegistrantID,
            std::string* auRegistrantIDType,
            std::string* auEligibilityName,
            std::string* auEligibilityID,
            std::string* auEligibilityIDType);

private:
    void setExtension(
            const std::string& eligibilityType,
            int PolicyReason, 
            const std::string& registrantName);

    void setExtension(
            const std::string& eligibilityType,
            int PolicyReason,
            const std::string& registrantName,
            const std::string* registrantID,
            const std::string& registrantIDType);

    void setExtension(
            const std::string& eligibilityType,
            int policyReason,
            const std::string& registrantName,
            const std::string* registrantID,
            const std::string* registrantIDType,
            const std::string* eligibilityName,
            const std::string* eligibilityID,
            const std::string* eligibilityIDType);
};


#endif // __AUDOMAINCREATECOMMANDV1_HPP
