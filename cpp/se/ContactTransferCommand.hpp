#ifndef __CONTACT_TRANSFER_COMMAND_HPP
#define __CONTACT_TRANSFER_COMMAND_HPP

#include "se/TransferCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * The superclass of all contact transfer command classes.  Subclasses are
 * responsible for specifying the kind of transfer operation, but hiding the
 * implementation from the user.
 */
class ContactTransferCommand : public TransferCommand
{
public:
    ContactTransferCommand (const TransferOp *operation, const std::string &id)
        : TransferCommand (StandardObjectType::CONTACT(), operation, id)
	{ }
    
    ContactTransferCommand (const TransferOp *operation,
                            const std::string &id,
                            const std::string &pw)
        : TransferCommand (StandardObjectType::CONTACT(),
                           operation, id, pw)
	{ }
};

#endif // __CONTACT_TRANSFER_COMMAND_HPP
