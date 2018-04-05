package com.ausregistry.jtoolkit2.se.unspec;

import static com.ausregistry.jtoolkit2.se.ExtendedObjectType.UNSPEC;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Transfer Request command, representing the Transfer aspects of the EPP.</p>
 *
 * <p>Use this to command to transfer a domain name. The response expected from a
 * server should be handled by a Domain Transfer Response.</p>
 *
 * <p>This extension is also used to specify the UIN for transfer in the .travel zone.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferRequestCommand
 */
public class DomainTransferCommandUnspecExtension implements CommandExtension {
    private String uin;

    public DomainTransferCommandUnspecExtension(String uin) {
        this.uin = uin;
    }

    @Override
    public void addToCommand(Command command) {
        if (uin != null) {
            addUnspecWithContent(command, "UIN=" + uin);
        }
    }

    private void addUnspecWithContent(Command command, String content) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element transferElement = xmlWriter.appendChild(extensionElement, "extension",
                UNSPEC.getURI());

        xmlWriter.appendChild(transferElement, "unspec", UNSPEC.getURI()).setTextContent(content);
    }
}
