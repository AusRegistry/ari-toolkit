package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 policyDelete command specified
 * by the AusRegistry EPP extensions document.  This should be used to delete
 * domains violating relevant policy, rather than at the request of the
 * registrant.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 */
public class ArDomainPolicyDeleteCommand extends ProtocolExtensionCommand {
    private static final long serialVersionUID = 3008976308409618621L;

    private static final CommandType POLICY_DELETE_COMMAND_TYPE = new ArPolicyDeleteCommandType();

    /**
     * @param name Required.
     * @param reason Required.
     * @throws IllegalArgumentException if {@code name} or {@code reason} is {@code null}.
     */
    public ArDomainPolicyDeleteCommand(String name, String reason) {
        super(POLICY_DELETE_COMMAND_TYPE, ExtendedObjectType.AR_DOMAIN, name);

        if (name == null || reason == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.ar.policyDelete.missing_arg"));
        }

        xmlWriter.appendChild(objElement, "reason").setTextContent(reason);
    }

    protected Extension getExtension() {
        return ExtensionImpl.AR;
    }
}

