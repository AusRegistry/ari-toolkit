package com.ausregistry.jtoolkit2.se.unspec;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;


/**
 * <p>Extension for the EPP Domain Create command, representing the Create  Domain aspect of the
 * Domain Name Unspec Extension.</p>
 *
 * <p>Use this to identify the unspec associated with this domain name as part of an EPP Domain Create
 * command compliant with RFC5730 and RFC5731. The extContact, UIN, Travel Industry Acknowledgement,
 * Reseleer, ReservationDomain, WhoisType or Publish values can be supplied depending on the usage.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.DomainCreateResponse
 */
public class DomainCreateCommandUnspecExtension implements CommandExtension {

    private static final long serialVersionUID = 5982521830455586062L;

    private String extContactId;
    private WhoisType whoisType;
    private Boolean publish;
    private Boolean reservationDomain;
    private String resellerName;
    private String resellerUrl;
    private String resellerPhone;
    private String uin;
    private Boolean travelIndustryAcknowledgement;

    @Deprecated
    public DomainCreateCommandUnspecExtension(String extContactId) {
        this.extContactId = extContactId;
    }

    public DomainCreateCommandUnspecExtension() {
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();

        final Element unspecElement = xmlWriter.appendChild(extensionElement, "extension",
                ExtendedObjectType.UNSPEC.getURI());

        StringBuilder unspecValue = new StringBuilder();
        if (extContactId != null) {
            unspecValue.append(" extContact=" + extContactId);
        }
        if (whoisType != null) {
            unspecValue.append(" WhoisType=" + whoisType.getValue());
        }
        if (publish != null) {
            unspecValue.append(" Publish=" + (publish ? "Y" : "N"));
        }
        if (reservationDomain != null && reservationDomain) {
            unspecValue.append(" ReservationDomain=Yes");
        }

        if (resellerName != null) {
            unspecValue.append(" ResellerName=").append(resellerName.replaceAll("\\s", "+"));
        }

        if (resellerUrl != null) {
            unspecValue.append(" ResellerUrl=").append(resellerUrl);
        }

        if (resellerPhone != null) {
            unspecValue.append(" ResellerPhone=").append(resellerPhone);
        }

        if (uin != null) {
            unspecValue.append(" UIN=").append(uin);
        } else if (travelIndustryAcknowledgement != null) {
            unspecValue.append(" TravelIndustry=").append(travelIndustryAcknowledgement ? "Y" : "N");
        }

        xmlWriter.appendChild(unspecElement, "unspec", ExtendedObjectType.UNSPEC.getURI())
                .setTextContent(unspecValue.toString().trim());

    }

    public void setExtContactId(String extContactId) {
        this.extContactId = extContactId;
    }

    public void setWhoisType(WhoisType whoisType) {
        this.whoisType = whoisType;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public void setReservationDomain(Boolean reservationDomain) {
        this.reservationDomain = reservationDomain;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
    }

    public void setResellerUrl(String resellerUrl) {
        this.resellerUrl = resellerUrl;
    }

    public void setResellerPhone(String resellerPhone) {
        this.resellerPhone = resellerPhone;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public void setTravelIndustryAcknowledgement(boolean travelIndustryAcknowledgement) {
        this.travelIndustryAcknowledgement = travelIndustryAcknowledgement;
    }
}
