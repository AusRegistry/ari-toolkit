package com.ausregistry.jtoolkit2.se.tmch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain check command, representing the Check aspect of the
 * Domain Name Trademark Clearing House extension.</p>
 *
 * <p>Use this to express the will to retrieve Lookup key, as part of the result of this command, which is being
 * submitted in as part of an EPP Domain Check command. The response expected
 * from a server should be handled by a Domain Check Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckCommand
 * @see <a href="http://ausregistry.github.io/doc/tmch-1.0/tmch-1.0.html">Domain Name Trademark Clearing House
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class TmchDomainCheckCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 4701921190014430174L;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.TMCH.getURI());
    }
}
