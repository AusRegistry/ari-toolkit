#ifndef __HOSTUPDATECOMMAND_HPP
#define __HOSTUPDATECOMMAND_HPP

#include "se/UpdateCommand.hpp"
#include "se/HostAddRem.hpp"

/**
 * Use this to request the update of a host object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3732 compliant
 * host update EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a Response object.
 *
 * @see Response
 */
class HostUpdateCommand : public UpdateCommand
{
public:
    /**
     * The set of attributes of a host which may be updated as per
     * RFC3732.
     */
    HostUpdateCommand (const std::string &name,
                       const HostAddRem *add = NULL,
                       const HostAddRem *rem = NULL,
                       const std::string *newName = NULL);
};

#endif // __HOSTUPDATECOMMAND_HPP
