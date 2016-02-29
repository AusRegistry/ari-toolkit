package com.ausregistry.jtoolkit2.se;

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 policy domainUndelete command
 * specified by the AusRegistry EPP extensions document.  This should only be
 * used to request reversing the deleting of domains which have been deleted due to policy
 * violation using the policy delete operation.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
public class ArDomainPolicyUndeleteCommand extends ProtocolExtensionCommand {
    private static final long serialVersionUID = 420815971854196895L;

    private static final CommandType POLICY_UNDELETE_COMMAND_TYPE = new ArPolicyUndeleteCommandType();

    public ArDomainPolicyUndeleteCommand(String name) {
        super(POLICY_UNDELETE_COMMAND_TYPE, ExtendedObjectType.AR_DOMAIN, name);
    }

    @Override
    protected Extension getExtension() {
        return ExtensionImpl.AR;
    }
}

