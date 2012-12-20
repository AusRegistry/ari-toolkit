#ifndef __DOMAIN_TRANSFER_CANCEL_COMMAND_HPP
#define __DOMAIN_TRANSFER_CANCEL_COMMAND_HPP

#include "se/DomainTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to cancel the transfer of a domain object currently pending
 * transfer.  The transfer must have been initiated via a transfer request by
 * the client attempting to cancel the transfer.  Instances of this class
 * generate RFC3730 and RFC3731 compliant domain transfer EPP command service
 * elements via the toXML method with the transfer operation set to "cancel".
 *
 * @see DomainTransferResponse
 */
class DomainTransferCancelCommand : public DomainTransferCommand
{
public:
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'cancel' transfer operation.
     * 
     * @param name The name of the domain to cancel transfer of.
     *
     * @param pw The identified domain's password.
     */ 
    DomainTransferCancelCommand (const std::string &name, const std::string &pw)
        : DomainTransferCommand (TransferOp::CANCEL(), name, pw)
	{ }
    
    /** 
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'cancel' transfer operation.
     *
     * @param name The name of the domain to cancel transfer of.
     *  
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferCancelCommand (const std::string &name,
                                 const std::string &roid,
                                 const std::string &pw)
        : DomainTransferCommand (TransferOp::CANCEL(), name, roid, pw)
	{ }
};

#endif // __DOMAIN_TRANSFER_CANCEL_COMMAND_HPP
