package com.ausregistry.jtoolkit2.se.unspec;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Contact Create command, indicating the contact can be used as an EXTContact.  This will
 * be acomplished by the use of the EPP unspec extension with a special extContact value.</p>
 *
 * <p>Use this to command to mark a contact as an EXTContact which will cause NYC specific address validation
 * to be performed. </p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 */
public class ContactCreateCommandUnspecExtension implements CommandExtension {

    private final boolean extContact;

    public ContactCreateCommandUnspecExtension(boolean extContact) {
        this.extContact = extContact;
    }

    @Override
    public void addToCommand(Command command) {
            final XMLWriter xmlWriter = command.getXmlWriter();
            final Element extensionElement = command.getExtensionElement();
            final Element unspecElement = xmlWriter.appendChild(extensionElement, "extension",
                    ExtendedObjectType.UNSPEC.getURI());

            xmlWriter.appendChild(unspecElement, "unspec",
                    ExtendedObjectType.UNSPEC.getURI()).setTextContent(" extContact=" + (extContact ? "Y" : "N"));
    }
}
