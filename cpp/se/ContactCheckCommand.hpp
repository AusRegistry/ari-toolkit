#ifndef __CONTACT_CHECK_COMMAND_HPP
#define __CONTACT_CHECK_COMMAND_HPP

#include "se/CheckCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * A ContactCheckCommand is used to check the availability of contact objects
 * in a Registry.  Instances of this class generate RFC3730 and RFC3733
 * compliant contact check EPP command service elements via the toXML method.
 *
 * @see ContactCheckResponse
 */
class ContactCheckCommand : public CheckCommand
{
public:
    /**
     * Check the availability of the single identified contact.
     *
     * @param id The identifier of the contact to check the availability of.
     */
    ContactCheckCommand (const std::string &id)
        : CheckCommand (StandardObjectType::CONTACT(), id) {};
    
    /**
     * Check the availability of at least one contact.
     *  
     * @param ids The identifiers of the contacts to check the availability of.
     */ 
    ContactCheckCommand (std::vector<std::string> &ids)
        : CheckCommand (StandardObjectType::CONTACT(), ids) {};
};

#endif // __CONTACT_CHECK_COMMAND_HPP
