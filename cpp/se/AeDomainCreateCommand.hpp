#ifndef __AEDOMAINCREATECOMMAND_HPP
#define __AEDOMAINCREATECOMMAND_HPP

#include "common/Deprecated.hpp"
#include "se/DomainCreateCommand.hpp"

/**
 * Extension of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified
 * in RFC3731 to urn:X-ae:params:xml:ns:aeext-1.0.  .ae domains must be
 * provisioned using this class rather than {@link DomainCreateCommand}, as the
 * ae extension data is mandatory.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML. 
 * The response expected from a server should be handled by a {@link
 * DomainCreateResponse} object.
 *
 * @deprecated AE eligibility extensions should now be managed through the
 *             @c <kvlist> extension defined in the
 *             <tt>urn:X-ar:params:xml:ns:kv-1.0</tt> namespace. This can be done
 *             through the toolkit by using a @c DomainCreateCommand and
 *             appending a @c DomainKVCommandExtension object containing
 *             the AE eligibility extensions.
 *
 *             See
 *             {@link DomainCreateCommand.appendExtension(CommandExtension)}
 *             and
 *             {@link DomainKVCommandExtension}.
 */                 
class AeDomainCreateCommand : public DomainCreateCommand
{
public:
    /**
     * Minimal constructor for creating a domain:create + aeext:create
     * EPP command.  These parameters are the least required for a valid
     * .ae domain create command.
     */
    DEPRECATED(
       AeDomainCreateCommand (const std::string& name,
                              const std::string& pw,
                              const std::string* registrantID,
                              const std::vector<std::string>* techContacts,
                              const std::string &aeEligibilityType,
                              int aePolicyReason,
                              const std::string& aeRegistrantName));
    /**
     * Full data specification constructor for a domain:create + aeext:create
     * EPP command.  Please refer to the urn:X-ae:params:xml:ns:aeext-1.0 schema
     * for specification of the required fields.
     * The mapping of parameter names to ae extension fields is given in the
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
     * @param period The initial registration period of the domain object.  A
     * server may define a default initial registration period if not specified
     * by the client.
     *
     * @param aeEligibilityType aeext:eligType.
     *
     * @param aePolicyReason aeext:policyReason.
     *
     * @param aeRegistrantName aeext:registrantName.
     *
     * @param aeRegistrantID aeext:registrantID.
     *
     * @param aeRegistrantIDType aeext:registrantID type attribute.
     *
     * @param aeEligibilityName aeext:eligibilityName.
     *
     * @param aeEligibilityID aeext:eligibilityID.
     *
     * @param aeEligibilityIDType aeext:eligibilityID type attribute.
     */
    DEPRECATED(
       AeDomainCreateCommand (const std::string& name,
                              const std::string& pw,
                              const std::string* registrantID,
                              const std::vector<std::string>* techContacts,
                              const std::vector<std::string>* adminContacts,
                              const std::vector<std::string>* billingContacts,
                              const std::vector<std::string>* nameservers,
                  const Period *period,
                              const std::string &aeEligibilityType,
                              int   aePolicyReason,
                              const std::string& aeRegistrantName,
                              const std::string* aeRegistrantID,
                              const std::string* aeRegistrantIDType,
                              const std::string* aeEligibilityName,
                              const std::string* aeEligibilityID,
                              const std::string* aeEligibilityIDType));
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
     * &nbsp;&lt;create xmlns="urn:X-ae:params:xml:ns:aeext-1.0"&gt;
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

                           
#endif // __AEDOMAINCREATECOMMAND_HPP
