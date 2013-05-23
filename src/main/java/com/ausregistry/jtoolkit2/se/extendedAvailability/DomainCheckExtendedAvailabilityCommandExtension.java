package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extended Availability Check extension for EPP Domain Check command.</p>
 *
 * <p>Use this to request information about a domain names extended availability details as part of an EPP Domain Check
 * command. The response expected from a server should be handled by a Domain Check Extended Availability Response
 * Extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckCommand
 * @see com.ausregistry.jtoolkit2.se.extendedAvailability.DomainCheckExtendedAvailabilityResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/exAvail-1.0/exAvail-1.0.html">Domain Name Check Extended Availability
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckExtendedAvailabilityCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -729779340619132291L;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.EX_AVAIL.getURI());
    }
}
