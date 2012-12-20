#ifndef __TRANSFER_OP_HPP
#define __TRANSFER_OP_HPP

#include "se/EnumType.hpp"

#include <string>
#include <vector>

/**
 * An enumeration of the transfer operation types defined for transfer commands
 * in RFC 3730.
 */
class TransferOp : public EnumType
{
public:
    TransferOp (const std::string &operation);
    
    static const TransferOp* QUERY();
    static const TransferOp* REQUEST();
    static const TransferOp* CANCEL();
    static const TransferOp* APPROVE();
    static const TransferOp* REJECT();
    static const TransferOp* value (const std::string &name);

	static void init();
        
private:
    static std::vector<const EnumType *> values;
};

#endif // __TRANSFER_OP_HPP
