#ifndef __TRANSFER_COMMAND_HPP
#define __TRANSFER_COMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/ObjectType.hpp"
#include "se/Period.hpp"
#include "se/TransferOp.hpp"

/**
 * Representation of the EPP transfer command, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped,
 * the type of transfer operation and specify the object-specific identifier of
 * the object to create a transfer command for.
 *
 * @see TransferResponse
 */
class TransferCommand : public ObjectCommand
{
public:
    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *  
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     */
    TransferCommand (const ObjectType* objType,
                     const TransferOp* operation,
                     const std::string &ident);
    
    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param pw The password of the object subject to the transfer command -
     * also referred to as authInfo or authorisation information.
     */
    TransferCommand (const ObjectType* objType,
                     const TransferOp* operation,
                     const std::string& ident,
                     const std::string& pw);
    
    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param period The validity period of the identified object should be
     * extended by this duration upon successful completion of the transfer
     * related to this command.
     *
     * @param pw The password of the object subject to the transfer command -
     * also referred to as authInfo or authorisation information.
     */
    TransferCommand (const ObjectType* objType,
                     const TransferOp* operation,
                     const std::string& ident,
                     const Period& period,
                     const std::string& pw);
                     
    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param roid The Repository Object Identifier of an object related to the
     * identified object subject to transfer which is considered a suitable
     * proxy for authorising transfer commands.
     *
     * @param pw The password of the related object identified by roid.
     */
    TransferCommand (const ObjectType* objType,
                     const TransferOp* operation,
                     const std::string& ident,
                     const std::string& roid,
                     const std::string& pw);

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param period The validity period of the identified object should be
     * extended by this duration upon successful completion of the transfer
     * related to this command.
     *
     * @param pw The password of the object subject to the transfer command -
     * also referred to as authInfo or authorisation information.
     */
    TransferCommand (const ObjectType* objType,
                     const TransferOp* operation,
                     const std::string& ident,
                     const Period& period,
                     const std::string& roid,
                     const std::string& pw);
private:
    void appendPW (const std::string &pw, const std::string &roid);
    void appendPW (const std::string &pw);
    void Init(const TransferOp* operation);
};

#endif // __TRANSFER_COMMAND_HPP
