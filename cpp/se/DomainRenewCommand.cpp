#include "se/DomainRenewCommand.hpp"

#include "se/StandardCommandType.hpp"
#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"
#include "se/EPPDateFormatter.hpp"

DomainRenewCommand::DomainRenewCommand (const std::string &name, 
                                        const XMLGregorianCalendar &exDate)
    : ObjectCommand(StandardCommandType::RENEW(),
                    StandardObjectType::DOMAIN(),
                    name)
{
    Init(name, exDate);
}

    
DomainRenewCommand::DomainRenewCommand (const std::string &name, 
                                        const XMLGregorianCalendar &exDate,
                                        const Period &period)
    : ObjectCommand(StandardCommandType::RENEW(),
                    StandardObjectType::DOMAIN(),
                    name)
{
    Init(name, exDate);
    
    period.appendPeriod (xmlWriter, objElement);
}


void DomainRenewCommand::Init (const std::string &name,
                               const XMLGregorianCalendar &exDate)
{
	XMLHelper::setTextContent
		(xmlWriter->appendChild (objElement, "curExpDate"),
		 EPPDateFormatter::toXSDateTime(exDate));
}
