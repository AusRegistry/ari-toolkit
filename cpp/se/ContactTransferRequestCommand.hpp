#ifndef __CONTACT_TRANSFER_REQUEST_COMMAND_HPP
#define __CONTACT_TRANSFER_REQUEST_COMMAND_HPP

#include "se/ContactTransferCommand.hpp"
#include "se/TransferOp.hpp"

#include <string>

/**
 * Use this to request the transfer of a contact object from another client.
 * The contact object MUST NOT be sponsored by the client attempting to request
 * the transfer.  Instances of this class generate RFC3730 and RFC3733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "request".
 *
 * @see ContactTransferResponse
 */
class ContactTransferRequestCommand : public ContactTransferCommand
{
public:
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param id The identifier of the contact to request transfer of.
     *
     * @param pw The identified contact's password.
     */
    ContactTransferRequestCommand (const std::string &id, const std::string &pw)
        : ContactTransferCommand (TransferOp::REQUEST(), id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_REQUEST_COMMAND_HPP
