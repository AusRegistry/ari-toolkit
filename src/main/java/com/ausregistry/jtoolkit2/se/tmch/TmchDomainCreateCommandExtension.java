package com.ausregistry.jtoolkit2.se.tmch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create aspect of the
 * Domain Name Trademark Clearing House extension.</p>
 *
 * <p>Use this to identify the domain's encoded signed mark data as part of the result of this command, which is being
 * submitted in as part of an EPP Domain Create command compliant with RFC5730 and RFC5731. The response expected
 * from a server should be handled by a Domain Create Response.</p>
 *
 * @see DomainCreateCommand
 * @see <a href="http://ausregistry.github.io/doc/tmch-1.0/tmch-1.0.html">Domain Name Trademark Clearing House
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class TmchDomainCreateCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 2000050396747484091L;

    private String encodedSignedMarkData;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element tmchCreateElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.TMCH.getURI());
        xmlWriter.appendChild(tmchCreateElement, "smd", ExtendedObjectType.TMCH.getURI())
                .setTextContent(encodedSignedMarkData);
    }

    public void setEncodedSignedMarkData(String encodedSignedMarkData) {
        this.encodedSignedMarkData = encodedSignedMarkData;
    }
}
