package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Update command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Update functionality
 * using the Launch extension.</p>
 *
 * <p>Use this to identify the domain name application to update as part of an EPP Domain Update command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a generic Response object.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainUpdateCommand
 * @see com.ausregistry.jtoolkit2.se.Response
 * @see CommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainUpdateLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;

    private String phaseName;
    private PhaseType phaseType;
    private String applicationId;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element updateElement = xmlWriter.appendChild(extensionElement, "update",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(updateElement, "phase");

        phaseElement.setTextContent(phaseType.getPhaseType());

        if (phaseName != null) {
            phaseElement.setAttribute("name", phaseName);
        }

        if (applicationId != null) {
            xmlWriter.appendChild(updateElement, "applicationID").setTextContent(applicationId);
        }

    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
