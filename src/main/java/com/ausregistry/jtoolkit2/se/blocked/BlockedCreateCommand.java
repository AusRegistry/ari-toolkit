package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.se.*;
import org.w3c.dom.Element;

/**
 * Use this to create a Blocked Domain Name. Instances of this class
 * generate RFC5730 compliant fund info EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.blocked.BlockedCreateCommand
 */
public class BlockedCreateCommand extends ProtocolExtensionCommand {
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
