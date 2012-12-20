#ifndef __DOMAIN_DELETE_COMMAND_HPP
#define __DOMAIN_DELETE_COMMAND_HPP

#include "se/DeleteCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * Use this to request that a domain object be deleted from an EPP Registry.
 * Instances of this class generate RFC3730 and RFC3731 compliant domain
 * delete EPP command service elements via the toXML method. 
 */
class DomainDeleteCommand : public DeleteCommand
{
public:
    /**
     * Delete the identified domain.
     *
     * @param name The name of the domain to delete.
     */
    DomainDeleteCommand (const std::string& name)
        : DeleteCommand(StandardObjectType::DOMAIN(), name) {};
};
#endif // __DOMAIN_DELETE_COMMAND_HPP
