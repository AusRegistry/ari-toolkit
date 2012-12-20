#ifndef __HOST_CREATE_COMMAND_HPP
#define __HOST_CREATE_COMMAND_HPP

#include "se/CreateCommand.hpp"
#include "se/InetAddress.hpp"

/** 
 * Use this to request that a host object be provisioned in an EPP Registry.
 * Instances of this class generate RFC3730 and RFC3732 compliant host create
 * EPP command service elements via the toXML method.
 *
 * @see HostCreateResponse
 */
class HostCreateCommand : public CreateCommand
{
public:
    /**
     * Provision a host with the specified details.  This constructor allows
     * specification of any and all parameters for a host create command.
     *
     * @param name The new host's name.
     *
     * @param addresses The Internet addresses of the host to be provisioned.
     * These should only be specified if the parent domain is sponsored by the
     * client provisioning this host and the parent domain is provisioned in
     * the domain name registry in which this host is being provisioned.  That
     * is, external hosts must not be assigned Internet addresses.
     */
    HostCreateCommand (const std::string &name,
                       const std::vector<InetAddress> *addresses = NULL);
};

#endif // __HOST_CREATE_COMMAND_HPP
