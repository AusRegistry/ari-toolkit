#ifndef __HOST_DELETE_COMMAND_HPP
#define __HOST_DELETE_COMMAND_HPP

#include "se/DeleteCommand.hpp"
#include "se/StandardObjectType.hpp"

/** 
 * Use this to request that a host object be deleted from an EPP Registry.
 * Instances of this class generate RFC3730 and RFC3732 compliant host delete
 * EPP command service elements via the toXML method.
 *
 * @see Response
 */
class HostDeleteCommand : public DeleteCommand
{
public:
    /**
     * Delete the identified host.
     *
     * @param name The name of the host to delete.
     */
    HostDeleteCommand (const std::string &name)
        : DeleteCommand (StandardObjectType::HOST(), name) {};
};

#endif // __HOST_DELETE_COMMAND_HPP
