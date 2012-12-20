#ifndef __AUDOMAINCREATECOMMAND_HPP
#define __AUDOMAINCREATECOMMAND_HPP

#include "common/Deprecated.hpp"
#include "se/DomainCreateCommand.hpp"

/**
 * Extension of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified
 * in RFC3731 to urn:au:params:xml:ns:auext-1.0.  .au domains must be
 * provisioned using this class rather than {@link
 * DomainCreateCommand}, as the au extension data
 * is mandatory.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML. 
 * The response expected from a server should be handled by a {@link
 * DomainCreateResponse} object.

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
class AuDomainCreateCommand : public DomainCreateCommand
{
public:
    /**
     * Minimal constructor for creating a domain:create + auext:create
     * EPP command.  These parameters are the least required for a valid
     * .au domain create command.
     */
      DEPRECATED(
            AuDomainCreateCommand (const std::string& name,
                           const std::string& pw,
                           const std::string* registrantID,
                           const std::vector<std::string>* techContacts,
                           const std::string &auEligibilityType,
                           int auPolicyReason,
                           const std::string& auRegistrantName));
    /**
     * Full data specification constructor for a domain:create + auext:create
     * EPP command.  Please refer to the urn:au:params:xml:ns:auext-1.1 schema
     * for specification of the required fields.
     * The mapping of parameter names to au extension fields is given in the
     * parameter documentation.
     *
     * @param name The name of the new domain.
     *
     * @param pw The password to assign to the domain (also known as authInfo
     * or authorisation information).
     *
     * @param registrantID The identifier of an existing contact to assign as
     * the registrant contact for this domain.  Failure to ensure the contact
     * exists prior to using them in this way will result in an EPP result of
     * '2303 "Object does not exist"'.
     *
     * @param techContacts The identifiers of existing contacts to assign as
     * technical contacts for this domain.  Failure to ensure the contacts
     * exist prior to using them in this way will result in an EPP result of
     * '2303 "Object does not exist"'.
     *
     * @param adminContacts See techContacts (substitute administrative for
     * technical).
     *
     * @param billingContacts See techContacts (substitute billing for
     * technical).
     *
     * @param nameservers The names of existing hosts to delegate the domain
     * being created to.  Failure to ensure the hosts exist prior to using them
     * in this way will result in an EPP result of '2303  "Object does not
     * exist"'.
     *
     * @param auEligibilityType auext:eligType.
     *
     * @param auPolicyReason auext:policyReason.
     *
     * @param auRegistrantName auext:registrantName.
     *
     * @param auRegistrantID auext:registrantID.
     *
     * @param auRegistrantIDType auext:registrantID type attribute.
     *
     * @param auEligibilityName auext:eligibilityName.
     *
     * @param auEligibilityID auext:eligibilityID.
     *
     * @param auEligibilityIDType auext:eligibilityID type attribute.
     */
      DEPRECATED(
            AuDomainCreateCommand (const std::string& name,
                           const std::string& pw,
                           const std::string* registrantID,
                           const std::vector<std::string>* techContacts,
                           const std::vector<std::string>* adminContacts,
                           const std::vector<std::string>* billingContacts,
                           const std::vector<std::string>* nameservers,
                           const std::string &auEligibilityType,
                           int   auPolicyReason,
                           const std::string& auRegistrantName,
                           const std::string* auRegistrantID,
                           const std::string* auRegistrantIDType,
                           const std::string* auEligibilityName,
                           const std::string* auEligibilityID,
                           const std::string* auEligibilityIDType));
private:
    void setExtension (const std::string& eligibilityType,
                       int PolicyReason, 
                       const std::string& registrantName);

    void setExtension (const std::string& eligibilityType,
                       int PolicyReason, 
                       const std::string& registrantName,
                       const std::string* registrantID,
                       const std::string& registrantIDType);

	/**
     * &lt;extension&gt;
     * &nbsp;&lt;create xmlns="urn:au:params:xml:ns:auext-1.1" xsi:schemaLocation="urn:au:params:xml:ns:auext-1.1 auext.1.1.xsd"&gt;
     * &nbsp;&nbsp;&lt;registrantName&gt;registrantName&lt;/registrantName&gt;
     * &nbsp;&nbsp;&lt;registrantID type="registrantIDType"&gt;registrantID&lt;/registrantID&gt;
     * &nbsp;&nbsp;&lt;eligibilityType&gt;eligibilityType&lt;/eligibilityType&gt;
     * &nbsp;&nbsp;&lt;eligibilityName&gt;eligibilityName&lt;/eligibilityName&gt;
     * &nbsp;&nbsp;&lt;eligibilityID type="eligibilityIDType"&gt;eligibilityID&lt;/eligibilityID&gt;
     * &nbsp;&nbsp;&lt;policyReason&gt;policyReason&lt;/policyReason&gt;
     * &nbsp;&lt;/create&gt;
     * &lt;/extension&gt;
     */

    void setExtension (const std::string& eligibilityType,
                       int policyReason, 
                       const std::string& registrantName,
                       const std::string* registrantID,
                       const std::string* registrantIDType,
                       const std::string* eligibilityName,
                       const std::string* eligibilityID,
                       const std::string* eligibilityIDType);
};

                           
#endif // __AUDOMAINCREATECOMMAND_HPP
