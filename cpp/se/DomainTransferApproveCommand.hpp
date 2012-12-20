#ifndef __DOMAIN_TRANSFER_APPROVE_COMMAND_HPP
#define __DOMAIN_TRANSFER_APPROVE_COMMAND_HPP

#include "se/DomainTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to approve the transfer of a domain object currently pending
 * transfer.  The domain object must be sponsored by the client attempting to
 * approve the transfer.  Instances of this class generate RFC3730 and RFC3731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "approve".
 *
 * @see DomainTransferResponse
 */
class DomainTransferApproveCommand : public DomainTransferCommand
{
public:
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'approve' transfer operation.
     * 
     * @param name The name of the domain to approve transfer of.
     *
     * @param pw The identified domain's password.
     */ 
    DomainTransferApproveCommand (const std::string &name, 
                                  const std::string &pw)
        : DomainTransferCommand (TransferOp::APPROVE(), name, pw)
	{ }
    
    /** 
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'approve' transfer operation.
     *
     * @param name The name of the domain to approve transfer of.
     *  
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferApproveCommand (const std::string &name,
                                  const std::string &roid,
                                  const std::string &pw)
        : DomainTransferCommand (TransferOp::APPROVE(), name, roid, pw)
	{ }
};

#endif // __DOMAIN_TRANSFER_APPROVE_COMMAND_HPP
