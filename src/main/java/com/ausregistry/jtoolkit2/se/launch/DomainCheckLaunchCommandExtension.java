package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Check command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Check functionality
 * using the Launch extension.</p>
 *
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckCommand
 * @see DomainCheckLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;

    private String phaseName;
    private PhaseType phaseType;
    private String checkType;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "check",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(createElement, "phase");
        createElement.setAttribute("type", checkType);
        if (phaseName != null) {
            phaseElement.setAttribute("name", phaseName);
        }
        phaseElement.setTextContent(phaseType.getPhaseType());

    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

}
