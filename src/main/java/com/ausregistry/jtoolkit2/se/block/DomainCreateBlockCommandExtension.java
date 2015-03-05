package com.ausregistry.jtoolkit2.se.block;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create Block aspects of the Block extension.</p>
 *
 * <p>Use this to identify the block client assigned id that this command is being submitted
 * in as part of an EPP Domain Create command compliant with RFC5730 and RFC5731. The response expected from a
 * server should be handled by a Domain Create Response.</p>
 *
 * @see DomainCreateCommand
 * @see <a href="http://ausregistry.github.io/doc/block-1.0/block-1.0.html">Block Extension Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateBlockCommandExtension implements CommandExtension {
    private static final long serialVersionUID = 4324879283895987704L;

    private String id;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.BLOCK.getURI());
        if (id != null) {
            xmlWriter.appendChild(createElement, "id", ExtendedObjectType.BLOCK.getURI()).setTextContent(id);
        }
    }

    public void setId(String id) {
        this.id = id;
    }
}
