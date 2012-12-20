#ifndef __CONTACT_TRANSFER_QUERY_COMMAND_HPP
#define __CONTACT_TRANSFER_QUERY_COMMAND_HPP

#include "se/ContactTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to query the transfer state of a contact object.  Instances of this
 * class generate RFC3730 and RFC3733 compliant contact transfer EPP command
 * service elements via the toXML method with the transfer operation set to
 * "query".
 *
 * @see ContactTransferResponse
 */     
class ContactTransferQueryCommand : public ContactTransferCommand
{
public:
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the 'query' transfer operation.
     *
     * @param id The identifier of the contact to query the transfer state of.
     */
    ContactTransferQueryCommand (const std::string &id)
        : ContactTransferCommand (TransferOp::QUERY(), id)
	{ }
    
    /**
     * Create a contact transfer command for the idenfitied contact, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param id The identifier of the contact to query the transfer state of.
     *
     * @param pw The identified contact's password.
     */
    ContactTransferQueryCommand (const std::string &id, const std::string &pw)
        : ContactTransferCommand (TransferOp::QUERY(), id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_QUERY_COMMAND_HPP
