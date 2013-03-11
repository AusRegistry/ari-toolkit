package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Use this to set domain info application extension to an EPP domain info command.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see com.ausregistry.jtoolkit2.se.launch.DomainInfoApplicationResponseExtension
 */
public class DomainInfoApplicationCommandExtension  implements CommandExtension {

    private static final long serialVersionUID = 5799892330484406301L;
    
    private String applicationId;

    @Override
    public void addToCommand(Command command) throws Exception {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "info",
                ExtendedObjectType.LAUNCH.getURI());

        xmlWriter.appendChild(createElement, "id", ExtendedObjectType.LAUNCH.getURI()).setTextContent(applicationId);
        
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
