package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;

/**
 * Use this to request the update of a host object provisioned in an EPP
 * Registry.  Instances of this class generate RFC5730 and RFC5732 compliant
 * host update EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a Response object.
 *
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public class HostUpdateCommand extends UpdateCommand {
    private static final long serialVersionUID = 7375360369820132301L;

    /**
     * Minimal constructor to be used solely by extension subclasses.
     */
    public HostUpdateCommand(String name) {
        this(name, null, null, null);
    }

    /**
     * The complete set of attributes of a host which may be updated as per
     * RFC5732.
     */
    public HostUpdateCommand(String name, HostAddRem add,
            HostAddRem rem, String newName) {

        super(StandardObjectType.HOST, name);

        if (add != null) {
            Element addElement = xmlWriter.appendChild(objElement, "add");
            add.appendToElement(xmlWriter, addElement);
        }

        if (rem != null) {
            Element remElement = xmlWriter.appendChild(objElement, "rem");
            rem.appendToElement(xmlWriter, remElement);
        }

        if (newName != null) {
            Element chgElement = xmlWriter.appendChild(objElement, "chg");
            xmlWriter.appendChild(chgElement, "name").setTextContent(newName);
        }
    }
}

