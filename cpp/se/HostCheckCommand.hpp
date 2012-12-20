#ifndef __HOST_CHECK_COMMAND_HPP
#define __HOST_CHECK_COMMAND_HPP

#include "se/CheckCommand.hpp"
#include "se/StandardObjectType.hpp"

/** 
 * A HostCheckCommand is used to check the availability of host objects
 * in a Registry.  Instances of this class generate RFC3730 and RFC3732
 * compliant host check EPP command service elements via the toXML method.
 *
 * @see HostCheckResponse
 */
class HostCheckCommand : public CheckCommand
{
public:
    /**
     * Check the availability of the single identified host.
     *
     * @param name The name of the host to check the availability of.
     */
    HostCheckCommand (const std::string &name)
        : CheckCommand(StandardObjectType::HOST(), name) {};
    
    /**
     * Check the availability of at least one host.
     *
     * @param names The names of the hosts to check the availability of.
     */
    HostCheckCommand (const std::vector<std::string> &names)
        : CheckCommand(StandardObjectType::HOST(), names) {};
};

#endif // __HOST_CHECK_COMMAND_HPP
