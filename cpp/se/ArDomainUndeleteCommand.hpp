#ifndef __ARDOMAIN_UNDELETE_COMMAND_HPP
#define __ARDOMAIN_UNDELETE_COMMAND_HPP

#include "se/ProtocolExtensionCommand.hpp"

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 domainUndelete command
 * specified by the AusRegistry EPP extensions document.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
class ArDomainUndeleteCommand: public ProtocolExtensionCommand
{
public:
    ArDomainUndeleteCommand(const std::string &name);
};

#endif // __ARDOMAIN_UNDELETE_COMMAND_HPP

