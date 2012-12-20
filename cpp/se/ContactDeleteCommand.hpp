#ifndef __CONTACT_DELETE_COMMAND_HPP
#define __CONTACT_DELETE_COMMAND_HPP

#include "se/DeleteCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * Use this to request that a contact object be deleted from an EPP Registry.
 * Instances of this class generate RFC3730 and RFC3733 compliant contact
 * delete EPP command service elements via the toXML method.
 */
class ContactDeleteCommand : public DeleteCommand
{
public:
    /**
     * Delete the identified contact.
     *
     * @param id The identifier of the contact to delete.
     */
    ContactDeleteCommand (const std::string &id)
        : DeleteCommand (StandardObjectType::CONTACT(), id) {};
};

#endif // __CONTACT_DELETE_COMMAND_HPP
