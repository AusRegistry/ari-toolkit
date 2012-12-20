#ifndef __AU_DOMAIN_MODIFY_REGISTRANT_COMMAND_HPP
#define __AU_DOMAIN_MODIFY_REGISTRANT_COMMAND_HPP

#include "common/Deprecated.hpp"
#include "se/DomainUpdateCommand.hpp"

/**
 * An extension of the domain mapping of the EPP update command, as defined in
 * RFC3730 and RFC3731, to .au domain names, the specification of which is in
 * the XML schema definition urn:au:params:xml:ns:auext-1.1.
 * This class should only be used to correct au extension data for .au domain
 * names, and only where the legal registrant has not changed.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 *
 * @deprecated AU eligibility extensions should now be managed through the
 *             @c <kvlist> extension defined in the
 *             <tt>urn:X-ar:params:xml:ns:kv-1.0</tt> namespace. This can be done
 *             through the toolkit by using a @c DomainUpdateCommand and
 *             appending a @c DomainKVCommandExtension object containing
 *             the AU eligibility extensions.
 *
 *             See
 *             {@link DomainUpdateCommand.appendExtension(CommandExtension)}
 *             and
 *             {@link DomainKVCommandExtension}.
 */
class AuDomainModifyRegistrantCommand : public DomainUpdateCommand 
{
public:
      DEPRECATED(
            AuDomainModifyRegistrantCommand(const std::string& name,
                                    const std::string& registrantName,
                                    const std::string& explanation,
                                    const std::string* eligibilityType = NULL,
                                    int policyReason = 0,
                                    const std::string* registrantID = NULL,
                                    const std::string* registrantIDType = NULL,
                                    const std::string* eligibilityName = NULL,
                                    const std::string* eligibilityID = NULL,
                                    const std::string* eligibilityIDType = NULL));
};

#endif // __AU_DOMAIN_MODIFY_REGISTRANT_COMMAND_HPP

