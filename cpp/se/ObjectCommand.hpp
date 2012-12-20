#ifndef __OBJECTCOMMAND_HPP
#define __OBJECTCOMMAND_HPP

#include "se/Command.hpp"
#include "se/Extension.hpp"
#include "se/ObjectType.hpp"

/**
 * Superclass of all command classes which implement an object-mapped EPP
 * command such as create, delete, update, transfer, info and check.
 * Non-abstract subclasses must specify the command and object type, and not
 * expose assignment of those to the user.
 */
class ObjectCommand : public Command
{
public:
    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which take multiple object identifiers as parameters.
     */
	ObjectCommand (const CommandType* commandType,
                   const ObjectType* objectType,
                   const std::string& ident);
                   
    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which operate on a single object.
     */
    ObjectCommand (const CommandType* commandType,
                   const ObjectType* objectType,
                   const std::vector<std::string>& idents);
    
    const ObjectType* getObjectType() const { return objType; };
    

protected:
    DOMElement *objElement;

private:
    const ObjectType *objType;
    
    void Init(const CommandType* commandType,
              const ObjectType* objectType,
              const std::vector<std::string>& idents);
};

#endif // __OBJECTCOMMAND_HPP

