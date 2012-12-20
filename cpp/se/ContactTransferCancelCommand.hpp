#ifndef __CONTACT_TRANSFER_CANCEL_COMMAND_HPP
#define __CONTACT_TRANSFER_CANCEL_COMMAND_HPP

#include "se/ContactTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to cancel the transfer of a contact object currently pending
 * transfer.  The transfer must have been initiated via a transfer request by
 * the client attempting to cancel the transfer.  Instances of this class
 * generate RFC3730 and RFC3733 compliant contact transfer EPP command service
 * elements via the toXML method with the transfer operation set to "cancel".
 *
 * @see ContactTransferResponse
 */ 
class ContactTransferCancelCommand : public ContactTransferCommand
{
public:
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the designated password and the 'cancel' transfer operation.
     *
     * @param id The identifier of the contact to cancel transfer of.
     *
     * @param pw The identified contact's password.
     */
	ContactTransferCancelCommand (const std::string &id, const std::string &pw)
		: ContactTransferCommand (TransferOp::CANCEL(), id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_CANCEL_COMMAND_HPP
