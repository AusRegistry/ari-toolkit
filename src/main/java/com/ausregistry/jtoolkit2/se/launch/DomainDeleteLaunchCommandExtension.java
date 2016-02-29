package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Delete command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Delete functionality
 * using the Launch extension for registrations only, either in FCFS sunrise (requiring smd) or claims (requiring
 * noticeID)</p>
 *
 *
 * @see com.ausregistry.jtoolkit2.se.DomainDeleteCommand
 * @see DomainDeleteLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07#section-3.5">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainDeleteLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 3670438475221045820L;

    private String phaseName;
    private String applicationId;
    private PhaseType phaseType;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element deleteElement = xmlWriter.appendChild(extensionElement, "delete",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(deleteElement, "phase");

        phaseElement.setTextContent(phaseType.getPhaseType());

        if (phaseName != null) {
            phaseElement.setAttribute("name", phaseName);
        }

        if (applicationId != null) {
            xmlWriter.appendChild(deleteElement, "applicationID").setTextContent(applicationId);
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
