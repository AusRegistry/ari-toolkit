#ifndef __CREATECOMMAND_HPP
#define __CREATECOMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/StandardCommandType.hpp"

/**
 * Representation of the EPP create command, as defined in RFC3730.
 * Subclasses of this must specify at a minimum the object to which the command
 * is mapped and the object-specific identifier of the object to create.
 */
class CreateCommand : public ObjectCommand
{
public:
    /**
     * Construct a create command of the given object type mapping with the
     * given object identifier.
     *
     * @param objType The type of object to which the create command is to be
     * mapped.
     *
     * @param ident The identifier of the object to be created.
     */
    CreateCommand(const ObjectType* objType,
                  const std::string& ident)
        : ObjectCommand(StandardCommandType::CREATE(), objType, ident)
	{ }
};
#endif // __CREATECOMMAND_HPP
