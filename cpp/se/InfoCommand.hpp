#ifndef __INFO_COMMAND_HPP
#define __INFO_COMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/StandardCommandType.hpp"
#include "xml/XStr.hpp"

/**
 * Representation of the EPP info command, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifier of the object to request
 * information about.
 *
 * @see InfoResponse
 */
class InfoCommand : public ObjectCommand
{
public:
    /**     
     * Create an info command mapped to the specified object type to retrieve
     * information about the identified object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     *      
     * @param ident An object type-specific label identifying the object to
     * retrieve information about.
     *  
     * @param pw The password of the object to retrieve information about.
     * This is used to retrieve complete information about an object when the
     * object is sponsored by another client.
     */
    InfoCommand (const ObjectType *objType, 
                 const std::string &ident,
                 const std::string &pw = "")
        : ObjectCommand (StandardCommandType::INFO(), objType, ident)
    {
        if (pw == "")
            return;
        
        xmlWriter->appendChild(
				xmlWriter->appendChild(
					objElement, "authInfo"),
				"pw")->setTextContent(XStr(pw).str());
    };

    /**
     * Create an info command mapped to the specified object type to retrieve
     * information about the identified object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * retrieve information about.
     *
     * @param roid The Repository Object Identifer of an object associated with
     * the object to be queried.
     *
     * @param pw The password of the object to retrieve information about.
     * This is used to retrieve complete information about an object when the
     * object is sponsored by another client.
     */
	InfoCommand(const ObjectType* objType,
			    const std::string& ident,
				const std::string& roid,
				const std::string& pw)
		: ObjectCommand(StandardCommandType::INFO(), objType, ident)
	{
		xmlWriter->appendChild(
			xmlWriter->appendChild(objElement, "authInfo"),
			"pw",
			"roid",
			roid)->setTextContent(XStr(pw).str());

	}
};

#endif // __INFO_COMMAND_HPP
