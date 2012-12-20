#ifndef __HOST_INFO_COMMAND_HPP
#define __HOST_INFO_COMMAND_HPP

#include "se/InfoCommand.hpp"
#include "se/StandardObjectType.hpp"

/** 
 * Use this to request information about a host object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3732 compliant
 * host info EPP command service elements via the toXML method.
 *
 * @see HostInfoResponse
 */
class HostInfoCommand : public InfoCommand
{
public:
    /**
     * Create a host info command with the specified identifier.
     *
     * @param name The name of the host to retrieve information about.
     */
    HostInfoCommand (const std::string &name)
        : InfoCommand (StandardObjectType::HOST(), name) {};
};
#endif // __HOST_INFO_COMMAND_HPP
