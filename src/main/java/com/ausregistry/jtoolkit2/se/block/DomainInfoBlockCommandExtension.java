package com.ausregistry.jtoolkit2.se.block;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Info command, representing the Block Info aspect of the Block extension.</p>
 *
 * <p>Use this to mark the ID of a Block to retrieve info for as part of an EPP Domain Info command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a Domain Info Response with the Block Info Response extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see DomainInfoBlockResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/block-1.0/block-1.0.html">Block Extension Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainInfoBlockCommandExtension implements CommandExtension {
    private static final long serialVersionUID = -3106443818428865374L;

    private String id;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element infoElement = xmlWriter.appendChild(extensionElement, "info",
                ExtendedObjectType.BLOCK.getURI());

        xmlWriter.appendChild(infoElement, "id", ExtendedObjectType.BLOCK.getURI()).setTextContent(id);
    }

    public void setId(String id) {
        this.id = id;
    }
}
