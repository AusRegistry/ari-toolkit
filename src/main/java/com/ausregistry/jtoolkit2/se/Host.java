package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * The Class Host are configured and it has name of host and ip addresses.
 */
public class Host implements Appendable {
    private static final long serialVersionUID = -8790808568589212577L;

    private String name;
    private InetAddress[] addresses;

    /**
     * Minimal information required as per RFC5733 for creation of a contact.
     */
    public Host(String name, InetAddress[] addresses) {
        this.name = name;
        this.addresses = addresses;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the addresses
     */
    public InetAddress[] getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(InetAddress[] addresses) {
        this.addresses = addresses;
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element hostAttr = xmlWriter.appendChild(parent, "hostAttr");
        if (name != null) {

            xmlWriter.appendChild(hostAttr, "hostName").setTextContent(name);
        }
        if (addresses != null && addresses.length > 0) {
            for (InetAddress inaddr : addresses) {
                assert inaddr != null;
                inaddr.appendHostAddrToElement(xmlWriter, hostAttr);
            }
        }

        return parent;
    }
}
