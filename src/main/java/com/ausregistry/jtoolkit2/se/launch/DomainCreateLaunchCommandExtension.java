package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.tmch.SignedMarkData;
import com.ausregistry.jtoolkit2.xml.NamespaceContextImpl;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Create functionality
 * using the Launch extension for a zone in First Come First Serve Sunrise phase.</p>
 *
 *
 * @see DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.launch.DomainCreateLaunchCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/application-1.0/application-1.0.html">Domain Name Application
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;

    private String phaseName;
    private String applicationId;
    private String encodedSignedMarkData;
    private LaunchCreateType launchCreateType;
    private PhaseType phaseType;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(createElement, "phase");
        if(phaseName!=null){
            phaseElement.setAttribute("name", phaseName);
        }
        final NamespaceContextImpl namespaceContext = new NamespaceContextImpl();
        createElement.setAttribute("type", launchCreateType.getCreateType());
        phaseElement.setTextContent(phaseType.getPhaseType());
        xmlWriter.appendChild(createElement,"encodedSignedMark",namespaceContext.getNamespaceURI("smd"))
                .setTextContent(encodedSignedMarkData);

    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public void setEncodedSignedMarkData(String encodedSignedMarkData) {
        this.encodedSignedMarkData = encodedSignedMarkData;
    }

    public void setLaunchCreateType(LaunchCreateType type) {
        this.launchCreateType = type;
    }

}
