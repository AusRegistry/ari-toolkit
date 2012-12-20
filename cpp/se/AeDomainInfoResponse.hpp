#ifndef __AE_DOMAIN_INFO_RESPONSE_HPP
#define __AE_DOMAIN_INFO_RESPONSE_HPP

#include "common/Deprecated.hpp"
#include "se/DomainInfoResponse.hpp"

#include <string>

/**
 * Extension of the domain mapping of the EPP info response, as defined in
 * RFC3730 and RFC3731, to .ae domain names, the specification of which is in
 * the XML schema definition urn:X-ae:params:xml:ns:aeext-1.0.
 * Instances of this class provide an interface to access all of the
 * information available through EPP for a .ae domain name.
 * This relies on the instance first being initialised by a suitable EPP domain
 * info response using the method fromXML.  For flexibility, this
 * implementation extracts the data from the response using XPath queries, the
 * expressions for which are defined statically.

 * @deprecated AE eligibility extensions should now be managed through the
 *             @c <kvlist> extension defined in the
 *             <tt>urn:X-ar:params:xml:ns:kv-1.0</tt> namespace. This can be done
 *             through the toolkit by using a @c DomainInfoResponse and
 *             registering a @c DomainInfoKVResponseExtension object, which
 *             will contain the AE eligibility extensions.
 *
 *             See
 *             {@link DomainInfoResponse.registerExtension(ResponseExtension)}
 *             and
 *             {@link DomainInfoKVResponseExtension}.
 */
class AeDomainInfoResponse : public DomainInfoResponse
{
public:
    DEPRECATED(AeDomainInfoResponse());

    const std::string& getRegistrantName() const { return registrantName; };
    const std::string& getAERegistrantID() const { return registrantID; };
    const std::string& getRegistrantIDType() const { return registrantIDType; };
    const std::string& getEligibilityType() const { return eligibilityType; };
    const std::string& getEligibilityName() const { return eligibilityName; };
    const std::string& getEligibilityID() const { return eligibilityID; };
    const std::string& getEligibilityIDType() const { return eligibilityIDType; };
    int getPolicyReason() const { return policyReason; };

    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

private:
    static const std::string AEEXT_EXPR,
                 AE_PROPERTIES_EXPR,
                 AE_REGISTRANT_NAME_EXPR,
                 AE_REGISTRANT_ID_EXPR,
                 AE_REGISTRANT_ID_TYPE_EXPR,
                 AE_ELI_TYPE_EXPR,
                 AE_ELI_NAME_EXPR,
                 AE_ELI_ID_EXPR,
                 AE_ELI_ID_TYPE_EXPR,
                 AE_POLICY_REASON_EXPR;

    std::string registrantName,
        registrantID,
        registrantIDType,
        eligibilityType,
        eligibilityName,
        eligibilityID,
        eligibilityIDType;

    int policyReason;
};

#endif // __AE_DOMAIN_INFO_RESPONSE_HPP

