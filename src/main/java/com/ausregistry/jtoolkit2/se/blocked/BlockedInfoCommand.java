package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.InfoCommand;

/**
 * Use this to request information about blocked domain objects. Instances of this class
 * generate blocked domain info EPP command service elements via the toXML method.
 *
 * @see BlockedInfoResponse
 */
public class BlockedInfoCommand extends InfoCommand {
    private static final long serialVersionUID = -3106443818428865374L;

    /**
     * Create a Blocked Domain Name info command.
     */
    public BlockedInfoCommand(String id) {
        super(ExtendedObjectType.BLOCKED);

        xmlWriter.appendChild(objElement, "id").setTextContent(id);
    }
}
