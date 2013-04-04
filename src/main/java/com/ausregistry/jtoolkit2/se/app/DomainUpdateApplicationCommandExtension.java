package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Use this to set domain update application extension to an EPP domain update command.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainUpdateCommand
 */
public class DomainUpdateApplicationCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5202343696850193788L;
    private String applicationId;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "update",
                ExtendedObjectType.APP.getURI());

        xmlWriter.appendChild(createElement, "id", ExtendedObjectType.APP.getURI())
                .setTextContent(applicationId);

    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
