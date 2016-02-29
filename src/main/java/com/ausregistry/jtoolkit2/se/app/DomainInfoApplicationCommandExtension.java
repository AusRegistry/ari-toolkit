package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Info command, representing the Application Info aspect of the
 * Domain Name Application extension.</p>
 *
 * <p>Use this to mark the ID of a domain name application to retrieve info for as part of an EPP Domain Info command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a Domain Info Response with the Domain Info Application Response extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see DomainInfoApplicationResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/application-1.0/application-1.0.html">Domain Name Application
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainInfoApplicationCommandExtension  implements CommandExtension {

    private static final long serialVersionUID = 5799892330484406301L;

    private String applicationId;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "info",
                ExtendedObjectType.APP.getURI());

        xmlWriter.appendChild(createElement, "id", ExtendedObjectType.APP.getURI()).setTextContent(applicationId);

    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
