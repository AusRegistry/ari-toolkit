#ifndef __DOMAIN_UPDATE_COMMAND_EXTENSION_HPP
#define __DOMAIN_UPDATE_COMMAND_EXTENSION_HPP

#include <string>

#include "common/ErrorPkg.hpp"
#include "se/Command.hpp"
#include "se/CommandExtension.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/IllegalArgException.hpp"

/**
 * This class models the <update> element as documented in 'Domain Expiry
 * Synchronisation Mapping for the Extensible Provisioning Protocol'.
 */
class DomainUpdateSyncCommandExtension : public CommandExtension
{
    public:
        DomainUpdateSyncCommandExtension(const XMLGregorianCalendar *exDate);
        virtual void addToCommand(const Command &command) const;
    private:
        std::string syncExpiryDate;

};

inline DomainUpdateSyncCommandExtension::DomainUpdateSyncCommandExtension(
        const XMLGregorianCalendar *exDate)
{
    if (exDate == NULL)
    {
        throw IllegalArgException(
                ErrorPkg::getMessage("se.domain.update.sync.exDate.missing"));
    }

    syncExpiryDate = EPPDateFormatter::toXSDateTime(*exDate);
}

#endif // __DOMAIN_UPDATE_COMMAND_EXTENSION_HPP
