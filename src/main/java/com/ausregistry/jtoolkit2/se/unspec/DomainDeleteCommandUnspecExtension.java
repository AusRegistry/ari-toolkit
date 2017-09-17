package com.ausregistry.jtoolkit2.se.unspec;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;


/**
 * <p>Extension for the EPP Domain Delete command, representing the Delete Domain aspect of the
 * Domain Name Unspec Extension.</p>
 *
 * <p>Use this to identify the unspec associated with this domain name as part of an EPP Domain Delete
 * command compliant with RFC5730 and RFC5731. The Reservation Domain value can be supplied depending on the usage.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainDeleteCommand
 */
public class DomainDeleteCommandUnspecExtension implements CommandExtension {

    private static final long serialVersionUID = 5982521830455586062L;

    private Boolean reservationDomain;

    public DomainDeleteCommandUnspecExtension() {
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();

        final Element unspecElement = xmlWriter.appendChild(extensionElement, "extension",
                ExtendedObjectType.UNSPEC.getURI());

        StringBuilder unspecValue = new StringBuilder();
        if (reservationDomain != null && reservationDomain) {
            unspecValue.append(" ReservationDomain=Yes");
        }
        xmlWriter.appendChild(unspecElement, "unspec", ExtendedObjectType.UNSPEC.getURI())
                .setTextContent(unspecValue.toString().trim());

    }

    public void setReservationDomain(Boolean reservationDomain) {
        this.reservationDomain = reservationDomain;
    }
}
