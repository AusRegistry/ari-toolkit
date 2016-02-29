package com.ausregistry.jtoolkit2.se.launch;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.NamespaceContextImpl;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Registration
 * aspects of the Domain Name Launch extension.  The current implementation is to provide Domain Create functionality
 * using the Launch extension for registrations only, either in fcfs sunrise (requiring smd) or claims (requiring
 * noticeID)</p>
 *
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see DomainCreateLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateLaunchCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;

    private String phaseName;
    private String encodedSignedMarkData;
    private LaunchCreateType launchCreateType;
    private PhaseType phaseType;
    private String noticeId;
    private GregorianCalendar notAfterDateTime;
    private GregorianCalendar acceptedDateTime;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.LAUNCH.getURI());
        final Element phaseElement = xmlWriter.appendChild(createElement, "phase");

        if (launchCreateType != null) {
            createElement.setAttribute("type", launchCreateType.getCreateType());
        }

        phaseElement.setTextContent(phaseType.getPhaseType());

        if (phaseName != null) {
            phaseElement.setAttribute("name", phaseName);
        }
        if (noticeId != null) {
            appendClaimsNotice(xmlWriter, createElement);
        }

        final NamespaceContextImpl namespaceContext = new NamespaceContextImpl();
        if (encodedSignedMarkData != null) {
            appendSignedMarkData(xmlWriter, createElement, namespaceContext);
        }
    }

    private void appendSignedMarkData(XMLWriter xmlWriter, Element createElement,
                                      NamespaceContextImpl namespaceContext) {
        xmlWriter.appendChild(createElement, "encodedSignedMark", namespaceContext.getNamespaceURI("smd"))
                    .setTextContent(encodedSignedMarkData);
    }

    private void appendClaimsNotice(XMLWriter xmlWriter, Element createElement) {
        Element noticeElement = xmlWriter.appendChild(createElement, "notice");
        xmlWriter.appendChild(noticeElement, "noticeID", ExtendedObjectType.LAUNCH.getURI())
                    .setTextContent(noticeId);

        if (notAfterDateTime != null) {
            xmlWriter.appendChild(noticeElement, "notAfter", ExtendedObjectType.LAUNCH.getURI())
                    .setTextContent(EPPDateFormatter.toXSDateTime(notAfterDateTime));
        }

        if (acceptedDateTime != null) {
            xmlWriter.appendChild(noticeElement, "acceptedDate", ExtendedObjectType.LAUNCH.getURI())
                    .setTextContent(EPPDateFormatter.toXSDateTime(acceptedDateTime));
        }
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

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public void setNotAfterDateTime(GregorianCalendar notAfterDateTime) {
        this.notAfterDateTime = notAfterDateTime;
    }

    public void setAcceptedDateTime(GregorianCalendar acceptedDateTime) {
        this.acceptedDateTime = acceptedDateTime;
    }
}
