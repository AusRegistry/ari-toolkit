#ifndef __AR_DOMAIN_UNRENEW_COMMAND_HPP
#define __AR_DOMAIN_UNRENEW_COMMAND_HPP

#include "se/ProtocolExtensionCommand.hpp"
#include <string>

class XMLGregorianCalendar;

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 domainUnrenew command
 * specified by the AusRegistry EPP extensions document.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
class ArDomainUnrenewCommand : public ProtocolExtensionCommand
{
public:
    ArDomainUnrenewCommand(const std::string & name, const XMLGregorianCalendar& exDate);
};

#endif // __AR_DOMAIN_UNRENEW_COMMAND_HPP

