package com.ausregistry.jtoolkit2.se.allocationToken;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;


/**
 * <p>Extension for the EPP Domain Create command, representing the Create  Domain aspect of the
 * Domain Name Allocation Token Extension.</p>
 *
 * <p>Use this to identify the allocationToken associated with this domain name as part of an EPP Domain Create
 * command compliant with RFC5730 and RFC5731. The "allocationToken" value
 * supplied, should match the membership Id and the secret key that are set for the zone.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.DomainCreateResponse
 * @see <a href="https://tools.ietf.org/html/draft-gould-allocation-token-03#page-10">Domain Name Allocation Token
 * Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainAllocationTokenCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5982521830455586062L;

    private final String allocationToken;

    public DomainAllocationTokenCommandExtension(String allocationToken) {
        this.allocationToken = allocationToken;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();

        final Element allocationTokenElement = xmlWriter.appendChild(extensionElement, "allocationToken",
                ExtendedObjectType.ALLOCATION_TOKEN.getURI());
        allocationTokenElement.setTextContent(allocationToken);
    }

}
