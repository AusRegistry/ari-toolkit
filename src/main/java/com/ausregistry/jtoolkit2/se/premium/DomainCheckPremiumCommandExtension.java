package com.ausregistry.jtoolkit2.se.premium;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

public class DomainCheckPremiumCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 2327272643303127953L;

    /**
     * @param command the domain-check command into which extension will be applied
     * @throws Exception
     */
    @Override
    public void addToCommand(Command command) throws Exception {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "check",
                ExtendedObjectType.PREMIUM.getURI());
    }
}
