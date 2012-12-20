#ifndef __CONTACT_TRANSFER_REJECT_COMMAND_HPP
#define __CONTACT_TRANSFER_REJECT_COMMAND_HPP

#include "se/ContactTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to reject the transfer of a contact object currently pending
 * transfer.  The contact object must be sponsored by the client attempting to
 * reject the transfer.  Instances of this class generate RFC3730 and RFC3733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "reject".
 *
 * @see ContactTransferResponse
 */
class ContactTransferRejectCommand : public ContactTransferCommand
{
public:
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param id The identifier of the contact to reject transfer of.
     *
     * @param pw The identified contact's password.
     */
	ContactTransferRejectCommand(const std::string &id, const std::string &pw)
		: ContactTransferCommand(TransferOp::REJECT(), id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_REJECT_COMMAND_HPP
