#ifndef __DOMAIN_RENEW_COMMAND_HPP
#define __DOMAIN_RENEW_COMMAND_HPP

#include "se/ObjectCommand.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/Period.hpp"

/**
 * Use this to request the renewal of a domain object provisioned in an EPP
 * Registry.  The requesting client must be the sponsoring client of the domain
 * object.  Instances of this class generate RFC3730 and RFC3731 compliant
 * domain renew EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a DomainRenewResponse
 * object.
 *
 * @see DomainRenewResponse
 */
class DomainRenewCommand : public ObjectCommand
{
public:
    DomainRenewCommand (const std::string &name, const XMLGregorianCalendar& exDate);
    DomainRenewCommand (const std::string &name, 
                        const XMLGregorianCalendar &exDate,
                        const Period &period);
private:
	void Init (const std::string &name,
			   const XMLGregorianCalendar &exDate);
};

#endif // __DOMAIN_RENEW_COMMAND_HPP
