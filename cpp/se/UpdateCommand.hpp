#ifndef __UPDATECOMMAND_HPP
#define __UPDATECOMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/StandardCommandType.hpp"

/** 
 * Representation of the EPP update command, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifier of the object to update.
 *
 * @see Response
 */ 
class UpdateCommand : public ObjectCommand
{
public:
    /**
     * Create an update command mapped to the specified object type to update
     * the identified object.
     *
     * @param objType The type of object to which the update command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * update.
     */
    UpdateCommand (const ObjectType* objType,
                   const std::string& ident)
        : ObjectCommand(StandardCommandType::UPDATE(), objType, ident) {};
};

#endif // __UPDATECOMMAND_HPP
