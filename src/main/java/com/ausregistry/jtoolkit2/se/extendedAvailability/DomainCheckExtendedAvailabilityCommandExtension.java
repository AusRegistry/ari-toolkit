package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

public class DomainCheckExtendedAvailabilityCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -729779340619132291L;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.EX_AVAIL.getURI());
    }
}
