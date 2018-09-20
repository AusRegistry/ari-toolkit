package com.ausregistry.jtoolkit2.se;

import java.util.Arrays;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * The Class Host are configured and it has name of host and ip addresses.
 */
public class Host implements Appendable {
    private static final long serialVersionUID = -8790808568589212577L;

    private static final InetAddress[] EMPTY_ADDRESSES = {};

    private String name;
    private InetAddress[] addresses;

    /**
     * Minimal information required as per RFC5733 for creation of a contact.
     */
    public Host(String name, InetAddress[] addresses) {
        assert name != null;
        this.name = name;
        this.addresses = addresses;
    }

    public Host(String name) {
        this(name, EMPTY_ADDRESSES);
    }

    static Host[] hostObjects(String[] nameservers) {
        if (nameservers != null && nameservers.length > 0) {
            Host[] results = new Host[nameservers.length];
            for (int i = 0; i < nameservers.length; i++) {
                results[i] = new Host(nameservers[i]);
            }
            return results;
        }
        return null;
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

    @Override
    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element hostAttr = xmlWriter.appendChild(parent, "hostAttr");

        xmlWriter.appendChild(hostAttr, "hostName").setTextContent(name);
        if (addresses != null && addresses.length > 0) {
            for (InetAddress inaddr : addresses) {
                assert inaddr != null;
                xmlWriter.appendChild(hostAttr, "hostAddr", inaddr.getTextRep(), "ip", inaddr.getVersion());
            }
        }
        return parent;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Host [" + (name != null ? "name=" + name + ", " : "")
            + (addresses != null ? "addresses=" + Arrays.toString(addresses) : "") + "]";
    }

}
