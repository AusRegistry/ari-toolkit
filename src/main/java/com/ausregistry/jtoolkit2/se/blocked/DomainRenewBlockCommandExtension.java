package com.ausregistry.jtoolkit2.se.blocked;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Renew Block Domain
 * aspects of the Blocked Domain Name extension.</p>
 *
 * <p>Use this to identify the block client assigned id that this command is being submitted
 * in as part of an EPP Domain Renew command compliant with RFC5730 and RFC5731. The response expected from a
 * server should be handled by a Domain Renew Response with the Blocked Domain Renew Response extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 * @see DomainRenewBlockResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/blocked-1.0/blocked-1.0.html">Blocked Domain Name
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRenewBlockCommandExtension implements CommandExtension {
    private static final long serialVersionUID = 9181066107823620601L;

    private String id;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element renewElement = xmlWriter.appendChild(extensionElement, "renew",
                ExtendedObjectType.BLOCKED.getURI());
        if (id != null) {
            xmlWriter.appendChild(renewElement, "id", ExtendedObjectType.BLOCKED.getURI()).setTextContent(id);
        }
    }

    public void setId(String id) {
        this.id = id;
    }
}
