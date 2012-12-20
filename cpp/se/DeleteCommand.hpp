#ifndef __DELETE_COMMAND_HPP
#define __DELETE_COMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/ObjectType.hpp"
#include "se/StandardObjectType.hpp"
#include "se/StandardCommandType.hpp"

#include <string>

/**
 * Representation of the EPP delete command, as defined in RFC3730.
 * Subclasses of this must specify at a minimum the object to which the command
 * is mapped and the object-specific identifier of the object to delete.
 */
class DeleteCommand : public ObjectCommand
{
public:
    /**
     * Construct a delete command of the given object type mapping with the
     * given object identifier.
     *
     * @param objType The type of object to which the delete command is to be
     * mapped.
     *
     * @param ident The identifier of the object to be created.
     */
    DeleteCommand (const ObjectType* objType, const std::string& ident)
        : ObjectCommand (StandardCommandType::DELETE(), objType, ident) {};
};

#endif // __DELETE_COMMAND_HPP
