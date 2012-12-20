package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

import org.w3c.dom.Element;

/**
 * Use this to specify attributes to add to or remove from a host object via a
 * host update EPP service element, implemented in HostUpdateCommand.  This
 * class implements writing the add and rem elements to a host update command.
 */
public class HostAddRem implements java.io.Serializable {
    private static final long serialVersionUID = -8398402734698629729L;

    private InetAddress[] addresses;
    private Status[] statuses;

    /**
     * Each of the parameters is optional, but at least one must be specified.
     */
    public HostAddRem(InetAddress[] addresses, Status[] statuses) {
        if (addresses != null) {
            this.addresses = addresses.clone();
        }
        if (statuses != null) {
            this.statuses = statuses.clone();
        }
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        if (addresses != null && addresses.length > 0) {
            for (InetAddress inaddr : addresses) {
                assert inaddr != null;
                inaddr.appendToElement(xmlWriter, parent);
            }
        }

        if (statuses != null) {
            for (Status status : statuses) {
                xmlWriter.appendChild(parent, "status", status.getRationale(),
                        "s", status.toString());

            }
        }

        return parent;
    }
}

