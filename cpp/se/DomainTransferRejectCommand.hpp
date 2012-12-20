#ifndef __DOMAIN_TRANSFER_REJECT_COMMAND_HPP
#define __DOMAIN_TRANSFER_REJECT_COMMAND_HPP

#include "se/DomainTransferCommand.hpp"

/**
 * Use this to reject the transfer of a domain object currently pending
 * transfer.  The domain object must be sponsored by the client attempting to
 * reject the transfer.  Instances of this class generate RFC3730 and RFC3731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "reject".
 *
 * @see DomainTransferResponse
 */
class DomainTransferRejectCommand : public DomainTransferCommand
{
public:
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param name The name of the domain to reject transfer of.
     *
     * @param pw The identified domain's password.
     */
    DomainTransferRejectCommand (const std::string &name, const std::string &pw)
        : DomainTransferCommand (TransferOp::REJECT(), name, pw)
	{ }
    
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param name The name of the domain to reject transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferRejectCommand (const std::string &name,
                                 const std::string &roid,
                                 const std::string &pw)
        : DomainTransferCommand (TransferOp::REJECT(), name, roid, pw)
	{ }
};

#endif // __DOMAIN_TRANSFER_REJECT_COMMAND_HPP
