package com.ausregistry.jtoolkit2.se;

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 domainUndelete command
 * specified by the AusRegistry EPP extensions document. Use this class to
 * generate an AusRegistry-compliant XML document, given simple input
 * parameters. The toXML method in Command serialises this object to XML.
 */
public class ArDomainUndeleteCommand extends ProtocolExtensionCommand {
    private static final long serialVersionUID = 420815971854196895L;

    private static final CommandType UNDELETE_COMMAND_TYPE = new ArUndeleteCommandType();

    public ArDomainUndeleteCommand(String name) {
        super(UNDELETE_COMMAND_TYPE, ExtendedObjectType.AR_DOMAIN, name);
    }

    protected Extension getExtension() {
        return ExtensionImpl.AR;
    }
}

