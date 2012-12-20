#ifndef __CHECKCOMMAND_HPP
#define __CHECKCOMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/StandardCommandType.hpp"

#include <string>
#include <vector>

/**
 * Representation of the EPP check command, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifiers of the objects to check the
 * availability of.
 */
class CheckCommand : public ObjectCommand
{
public:
    /**
     * Create a check command mapped to the specified object type to check the
     * availability of the identified object.
     *
     * @param objType The object mapping to use.
     *          
     * @param ident An object type-specific label identifying the object to
     * check the availability of.
     */
    CheckCommand (const ObjectType* objType, 
                  const std::string &ident)
        : ObjectCommand (StandardCommandType::CHECK(), objType, ident) {};
    
    /**
     * Create a check command mapped to the specified object type to check the
     * availability of the identified object.
     *
     * @param objType The object mapping to use.
     *
     * @param idents An object type-specific array of labels identifying the
     * objects to check the availability of.
     */
    CheckCommand (const ObjectType* objType,
                  const std::vector<std::string> &idents)
        : ObjectCommand (StandardCommandType::CHECK(), objType, idents) {};
};

#endif // __CHECKCOMMAND_HPP
