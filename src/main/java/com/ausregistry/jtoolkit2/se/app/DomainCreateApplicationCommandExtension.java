package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create Application aspect of the
 * Domain Name Application extension.</p>
 *
 * <p>Use this to identify the domain name application phase this command is being submitted in as part of an
 * EPP Domain Create command compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a {@link DomainCreateApplicationResponse} object.</p>
 *
 * @see DomainCreateCommand
 * @see DomainCreateApplicationResponse
 * @see <a href="http://ausregistry.github.io/doc/application-1.0/application-1.0.html">Domain Name Application
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateApplicationCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;
    private String phase;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.APP.getURI());

        xmlWriter.appendChild(createElement, "phase", ExtendedObjectType.APP.getURI()).setTextContent(phase);
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
