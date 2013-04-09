package com.ausregistry.jtoolkit2.se.premium;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Check command, representing the Premium Fee Check aspect of the
 * Premium Domain Name extension.</p>
 *
 * <p>Use this to request information about a premium domain name fee as part of an EPP Domain Check command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a Domain Check Premium Response.</p>
 *
 * @see DomainCheckCommand
 * @see DomainCheckPremiumResponse
 * @see <a href="http://ausregistry.github.io/doc/premium-1.0/premium-1.0.html">Premium Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPremiumCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 2327272643303127953L;

    /**
     * @param command the domain-check command into which extension will be applied
     */
    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.PREMIUM.getURI());
    }
}
