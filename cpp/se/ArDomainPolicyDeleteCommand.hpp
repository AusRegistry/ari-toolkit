#ifndef __AR_DOMAIN_POLICY_DELETE_COMMAND_HPP
#define __AR_DOMAIN_POLICY_DELETE_COMMAND_HPP

#include "se/Extension.hpp"
#include "se/ProtocolExtensionCommand.hpp"

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 policyDelete command specified
 * by the AusRegistry EPP extensions document.  This should be used to delete
 * domains violating relevant policy, rather than at the request of the
 * registrant.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
class ArDomainPolicyDeleteCommand : public ProtocolExtensionCommand
{
public:
    ArDomainPolicyDeleteCommand (
            const std::string &name,
            const std::string &reason);

private:
    Extension& getExtension() const;
};

#endif // __AR_DOMAIN_POLICY_DELETE_COMMAND_HPP

