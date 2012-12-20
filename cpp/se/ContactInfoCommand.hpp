#ifndef __CONTACT_INFO_COMMAND_HPP
#define __CONTACT_INFO_COMMAND_HPP

#include "se/InfoCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * Use this to request information about a contact object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3733 compliant
 * contact info EPP command service elements via the toXML method.
 *
 * @see ContactInfoResponse
 */ 
class ContactInfoCommand : public InfoCommand
{
public:
    /**
     * Create a contact info command with the specified identifier.
     *
     * @param id The identifier of the contact to retrieve information about.
     */
    ContactInfoCommand (const std::string &id)
        : InfoCommand (StandardObjectType::CONTACT(), id) {};
    
    /**
     * Create a contact info command with the specified identifier.
     *
     * @param id The identifier of the contact to retrieve information about.
     *
     * @param pw The password of the identified contact object (also known as
     * authInfo or authorisation information).
     */
    ContactInfoCommand (const std::string &id, const std::string &pw)
        : InfoCommand (StandardObjectType::CONTACT(), id, pw) {};
};


#endif // __CONTACT_INFO_COMMAND_HPP
