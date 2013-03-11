package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Use this to set domain delete application extension to an EPP domain delete command.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainDeleteCommand
 */
public class DomainDeleteApplicationCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;
    private String applicationId;

    @Override
    public void addToCommand(Command command) throws Exception {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "delete",
                ExtendedObjectType.LAUNCH.getURI());

        xmlWriter.appendChild(createElement, "id", ExtendedObjectType.LAUNCH.getURI()).setTextContent
                (applicationId);

    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
