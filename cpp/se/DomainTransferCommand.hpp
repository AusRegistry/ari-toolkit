#ifndef __DOMAIN_TRANSFER_COMMAND_HPP
#define __DOMAIN_TRANSFER_COMMAND_HPP

#include "se/TransferCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * The superclass of all domain transfer command classes.  Subclasses are
 * responsible for specifying the kind of transfer operation, but hiding the
 * implementation from the user.
 */
class DomainTransferCommand : public TransferCommand
{
public:
    DomainTransferCommand (const TransferOp *operation,
                           const std::string &name)
        : TransferCommand (StandardObjectType::DOMAIN(), operation, name) {};
    
    DomainTransferCommand (const TransferOp *operation,
                           const std::string &name,
                           const std::string &pw)
        : TransferCommand (StandardObjectType::DOMAIN(),
                           operation, name, pw) {};
    
    DomainTransferCommand (const TransferOp *operation,
                           const std::string &name,
                           const std::string &roid,
                           const std::string &pw)
        : TransferCommand (StandardObjectType::DOMAIN(),
                           operation, name, roid, pw) {};
                           
    DomainTransferCommand (const TransferOp *operation,
                           const std::string &name,
                           const Period &period,
                           const std::string &pw)
        : TransferCommand (StandardObjectType::DOMAIN(),
                           operation, name, period, pw) {};

    DomainTransferCommand (const TransferOp *operation,
                           const std::string &name,
                           const Period &period,
                           const std::string &roid,
                           const std::string &pw)
        : TransferCommand (StandardObjectType::DOMAIN(),
                           operation, name, period, roid, pw) {};
};

#endif // __DOMAIN_TRANSFER_COMMAND_HPP
