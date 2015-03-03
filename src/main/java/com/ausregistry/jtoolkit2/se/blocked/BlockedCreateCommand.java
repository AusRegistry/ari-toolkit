package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.se.*;

/**
 * Use this to create a Blocked Domain Name. Instances of this class
 * generate blocked create EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.blocked.BlockedCreateCommand
 */
public class BlockedCreateCommand extends CreateCommand {
    private static final long serialVersionUID = 4324879283895987704L;

    /**
     * Create a Blocked Domain Name create command.
     */
    public BlockedCreateCommand(String domainName, String id, String registrantContactId) {
        super(ExtendedObjectType.BLOCKED, id);

        xmlWriter.appendChild(objElement, "name").setTextContent(domainName);
        xmlWriter.appendChild(objElement, "registrant").setTextContent(registrantContactId);
    }
}
