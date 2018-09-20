package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Host Internet addresses are configured and viewed via instances of this
 * class. InetAddress instances may be supplied to the HostCreateCommand and
 * HostUpdateCommand (indirectly via HostAddRem) constructors in order to
 * assign or remove Internet addresses to and from host objects. They are also
 * used to view Internet address information retrieved from a HostInfoResponse
 * instance.
 */
public class InetAddress implements Appendable {
    private static final long serialVersionUID = -1450632623902648843L;

    private IPVersion version;
    private String textRep;

    /**
     * Construct an IPv4 (default) InetAddress using its textual
     * representation.
     */
    public InetAddress(String textRep) {
        this(IPVersion.IPv4, textRep);
    }

    /**
     * Construct an InetAddress of the specified version (either IPv4 or IPv6)
     * using its textual representation.
     */
    public InetAddress(IPVersion version, String textRep) {
        this.version = version;
        this.textRep = textRep;
    }

    /**
     * Get the Internet Protocol version of this address.
     *
     * @see com.ausregistry.jtoolkit2.se.IPVersion Enumerates possible return
     * values.
     */
    public String getVersion() {
        return version.toString();
    }

    /**
     * Get the textual representation of this Internet address.
     */
    public String getTextRep() {
        return textRep;
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        return xmlWriter.appendChild(parent, "addr", textRep, "ip", getVersion());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "InetAddress [" + (version != null ? "version=" + version + ", " : "")
            + (textRep != null ? "textRep=" + textRep : "") + "]";
    }

}
