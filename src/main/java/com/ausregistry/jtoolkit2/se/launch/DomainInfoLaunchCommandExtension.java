package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Info command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Info functionality
 * using the Launch extension.</p>
 *
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see CommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainInfoLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;

    private String phaseName;
    private PhaseType phaseType;
    private String applicationID;
    private boolean includeMark;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element infoElement = xmlWriter.appendChild(extensionElement, "info",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(infoElement, "phase");
        infoElement.setAttribute("includeMark", String.valueOf(includeMark));
        if (phaseName != null) {
            phaseElement.setAttribute("name", phaseName);
        }
        phaseElement.setTextContent(phaseType.getPhaseType());

        if (applicationID != null) {
            final Element idElement = xmlWriter.appendChild(infoElement, "applicationID");
            idElement.setTextContent(applicationID);
        }

    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public void setIncludeMark(boolean includeMark) {
        this.includeMark = includeMark;
    }
}
