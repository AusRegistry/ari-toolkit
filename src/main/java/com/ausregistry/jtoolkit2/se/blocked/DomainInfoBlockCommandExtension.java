package com.ausregistry.jtoolkit2.se.blocked;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Info command, representing the Application Info aspect of the
 * Blocked Domain Name extension.</p>
 *
 * <p>Use this to mark the ID of a domain block to retrieve info for as part of an EPP Domain Info command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a Domain Info Response with the Blocked Domain Info Response extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see DomainInfoBlockResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/blocked-1.0/blocked-1.0.html">Blocked Domain Name
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainInfoBlockCommandExtension implements CommandExtension {
    private static final long serialVersionUID = -3106443818428865374L;

    private String id;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element infoElement = xmlWriter.appendChild(extensionElement, "info",
                ExtendedObjectType.BLOCKED.getURI());

        xmlWriter.appendChild(infoElement, "id", ExtendedObjectType.BLOCKED.getURI()).setTextContent(id);
    }

    public void setId(String id) {
        this.id = id;
    }
}
