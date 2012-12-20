#ifndef __DOMAIN_INFO_COMMAND_HPP
#define __DOMAIN_INFO_COMMAND_HPP

#include "se/InfoCommand.hpp"
#include "se/StandardObjectType.hpp"

/**
 * Use this to request information about a domain object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3731 compliant
 * domain info EPP command service elements via the toXML method.
 *
 * @see DomainInfoResponse
 */ 
class DomainInfoCommand : public InfoCommand
{
public:
    /**
     * Create a domain info command with the specified identifier and
     * authorisation information.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     */
	DomainInfoCommand (const std::string &name,
                       const std::string &pw = "")
        : InfoCommand (StandardObjectType::DOMAIN(), name, pw) {};


    /**
     * Create a domain info command with the specified identifier and
     * authorisation information of an associated contact.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param roid The Repository Object Identifer of a contact object
     * associated with the identified domain.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     */
	DomainInfoCommand (const std::string &name,
					   const std::string &roid,
                       const std::string &pw)
        : InfoCommand (StandardObjectType::DOMAIN(), name, roid, pw) {};
};

#endif // __DOMAIN_INFO_COMMAND_HPP
