#ifndef __AR_DOMAIN_POLICY_UNDELETE_COMMAND_HPP
#define __AR_DOMAIN_POLICY_UNDELETE_COMMAND_HPP

#include "se/ProtocolExtensionCommand.hpp"

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 policy domainUndelete command
 * specified by the AusRegistry EPP extensions document.  This should only be
 * used to request undeletion of domains which have been deleted due to policy
 * violation using the policy delete operation.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
class ArDomainPolicyUndeleteCommand : public ProtocolExtensionCommand
{
public:
    ArDomainPolicyUndeleteCommand(const std::string &name);
};

#endif // __AR_DOMAIN_POLICY_UNDELETE_COMMAND_HPP

