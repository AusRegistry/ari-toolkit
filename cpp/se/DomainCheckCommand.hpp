#ifndef __DOMAIN_CHECK_COMMAND_HPP
#define __DOMAIN_CHECK_COMMAND_HPP

#include "se/CheckCommand.hpp"
#include "se/StandardObjectType.hpp"

#include <string>
#include <vector>

/**
 * A DomainCheckCommand is used to check the availability of domain objects
 * in a Registry.  Instances of this class generate RFC3730 and RFC3731
 * compliant domain check EPP command service elements via the toXML method.
 *
 * @see DomainCheckResponse
 */
class DomainCheckCommand : public CheckCommand
{
public:
    /**
     * Check the availability of the single identified domain.
     *
     * @param name The name of the domain to check the availability of.
     */
    DomainCheckCommand (const std::string &name)
        : CheckCommand (StandardObjectType::DOMAIN(), name) {};
    
    /**
     * Check the availability of at least one domain.
     *
     * @param names The names of the domains to check the availability of.
     */
    DomainCheckCommand (std::vector<std::string> &names)
        : CheckCommand (StandardObjectType::DOMAIN(), names) {};
};

#endif // __DOMAIN_CHECK_COMMAND_HPP
