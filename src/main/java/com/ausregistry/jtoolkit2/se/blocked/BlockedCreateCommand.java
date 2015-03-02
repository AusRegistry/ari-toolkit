package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.se.*;

/**
 * Use this to create a Blocked Domain Name. Instances of this class
 * generate blocked create EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.blocked.BlockedCreateCommand
 */
public class BlockedCreateCommand extends ProtocolExtensionCommand {
    private static final long serialVersionUID = 4324879283895987704L;

    private final String id;
    private final String registrantContactId;

    /**
     * Create a Blocked Domain Name create command.
     */
    public BlockedCreateCommand(String domainName, String id, String registrantContactId) {
        super(new BlockedDomainCreateCommandType(), ExtendedObjectType.BLOCKED, domainName);
        this.id = id;
        this.registrantContactId = registrantContactId;

        xmlWriter.appendChild(objElement, "id").setTextContent(id);
        xmlWriter.appendChild(objElement, "registrant").setTextContent(registrantContactId);

    }

    @Override
    protected Extension getExtension() {
        return ExtensionImpl.BLOCKED;
    }
}
