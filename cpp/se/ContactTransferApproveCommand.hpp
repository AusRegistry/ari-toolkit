#ifndef __CONTACT_TRANSFER_APPROVE_COMMAND_HPP
#define __CONTACT_TRANSFER_APPROVE_COMMAND_HPP

#include "se/ContactTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to approve the transfer of a contact object currently pending
 * transfer.  The contact object must be sponsored by the client attempting to
 * approve the transfer.  Instances of this class generate RFC3730 and RFC3733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "approve".
 *
 * @see ContactTransferResponse
 */ 
class ContactTransferApproveCommand : public ContactTransferCommand
{
public:
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the designated password and the 'approve' transfer operation.
     *
     * @param id The identifier of the contact to approve transfer of.
     *
     * @param pw The identified contact's password.
     */
	ContactTransferApproveCommand (const std::string &id, const std::string &pw)
		: ContactTransferCommand (TransferOp::APPROVE(), id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_APPROVE_COMMAND_HPP
