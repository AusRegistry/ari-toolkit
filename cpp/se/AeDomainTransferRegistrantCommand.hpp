#ifndef __AE_DOMAIN_TRANSFER_REGISTRANT_COMMAND_HPP
#define __AE_DOMAIN_TRANSFER_REGISTRANT_COMMAND_HPP

#include "common/Deprecated.hpp"
#include "se/ProtocolExtensionCommand.hpp"
class XMLGregorianCalendar;
class Period;

/**
 * In cases where the legal registrant of a .ae domain name has changed, this
 * class should be used to request a transfer of registrant.  This is a
 * different action to correcting extension data which was originally specified
 * incorrectly, and should only be used in the situation described.  This
 * command will result in the validity period of the domain name being updated
 * and the requesting client being charged the usual create fee upon success of
 * this operation.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.

 * @deprecated AE eligibility extensions should now be managed through the
 *             @c <kvlist> extension defined in the
 *             <tt>urn:X-ar:params:xml:ns:kv-1.0</tt> namespace. The Registrant
 *             Transfer command that utilises this extension is defined in the
 *             <tt>urn:X-ar:params:xml:ns:registrant-1.0</tt> namespace. This can
 *             be done through the toolkit by using a
 *             @c DomainRegistrantTransferCommand and specifying
 *             @c "ae" as the kvListName.
 *
 *             See
 *             {@link DomainRegistrantTransferCommand}
 *             and
 *             {@link DomainRegistrantTransferCommand.addItem(std::string, std::string)}
 */
class AeDomainTransferRegistrantCommand : public ProtocolExtensionCommand
{
public:

    /**
     * Request that the named .ae domain name be transferred to the legal
     * entity specified by the given ae extension data.
     *
     * @param name The domain name to transfer.
     *
     * @param curExpDate The current expiry of the identified domain name.
     * This is required in order to prevent repeated transfer of the name due
     * to protocol transmission failures.
     *
     * @param eligibilityType
     *
     * @param policyReason
     *
     * @param registrantName
     *
     * @param explanation  An explanation of how the transfer was effected.
     *
     * @param registrantID
     *
     * @param registrantIDType
     *
     * @param eligibilityName
     *
     * @param eligibilityID
     *
     * @param eligibilityIDType
     *
     * @param period The desired new validity period, starting from the time
     * the transfer completes successfully.
     *
     * @param explanation An explanation of how the transfer was effected.
     */
    DEPRECATED(
    AeDomainTransferRegistrantCommand (const std::string& name,
                                       const XMLGregorianCalendar& curExpDate,
                                       const std::string& eligibilityType,
                                       int policyReason,
                                       const std::string& registrantName,
                                       const std::string& explanation,
                                       const std::string* registrantID = NULL,
                                       const std::string* registrantIDType = NULL,
                                       const std::string* eligibilityName = NULL,
                                       const std::string* eligibilityID = NULL,
                                       const std::string* eligibilityIDType = NULL,
                                       const Period* period = NULL));
};

#endif // __AE_DOMAIN_TRANSFER_REGISTRANT_COMMAND_HPP

