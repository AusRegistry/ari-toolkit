package com.ausregistry.jtoolkit2.se.unspec;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Contact Update command, indicating the contact can be used as an EXTContact.  This will
 * be acomplished by the use of the EPP unspec extension with a special extContact value.</p>
 *
 * <p>Use this to command to mark a contact as an EXTContact which will cause NYC specific address validation
 * to be performed. </p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 */
public class ContactUpdateCommandUnspecExtension implements CommandExtension {

    private Boolean extContact;
    private String nexusCategory;

    public ContactUpdateCommandUnspecExtension(Boolean extContact, String nexusCategory) {
        if (extContact != null) {
            this.extContact = extContact.booleanValue();
        }
        this.nexusCategory = nexusCategory;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element unspecElement = xmlWriter.appendChild(extensionElement, "extension",
                ExtendedObjectType.UNSPEC.getURI());

        StringBuilder unspecText =  new StringBuilder();
        if (extContact != null) {
            unspecText.append(" extContact=" + (extContact.booleanValue() ? "Y" : "N"));
        }
        if (nexusCategory != null) {
            unspecText.append(" nexusCategory=" + nexusCategory);
        }

        xmlWriter.appendChild(unspecElement, "unspec",
                ExtendedObjectType.UNSPEC.getURI()).setTextContent(unspecText.toString());

    }
}
