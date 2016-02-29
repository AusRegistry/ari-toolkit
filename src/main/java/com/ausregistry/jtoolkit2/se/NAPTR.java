package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * This class models Naming Authority Pointer (NAPTR) resource records.
 * Naming Authority Pointer (NAPTR) resource records are associated with
 * ENUM domain names via the e164 extended create and update EPP commands.
 * Instances of this class are used to construct NAPTR records to assign
 * to ENUM domain objects, or to view attributes of NAPTR records already
 * assigned to ENUM domain objects provisioned in an EPP Registry.
 *
 * @see com.ausregistry.jtoolkit2.se.EnumDomainCreateCommand Associate NAPTR
 * records with a new ENUM domain object, rather than delegating to
 * nameservers.
 *
 * @see com.ausregistry.jtoolkit2.se.EnumDomainUpdateCommand Add or remove
 * NAPTR record associations to/from an ENUM domain object.
 *
 * @see com.ausregistry.jtoolkit2.se.EnumDomainInfoResponse Report assocations
 * between a domain object and NAPTRs.
 */
public class NAPTR implements Appendable {
    private static final long serialVersionUID = 4895525383333709751L;

    private int order;
    private int preference;
    private String flags;
    private String svc;
    private String regex;
    private String replacement;

    public NAPTR(int order, int preference, char[] flags,
                 String service) {
        this.order = order;
        this.preference = preference;
        this.flags = new String(flags);
        this.svc = service;
    }

    public NAPTR(int order, int preference, char[] flags,
                 String service, String regex, String replacement) {

        this(order, preference, flags, service);
        this.regex = regex;
        this.replacement = replacement;
    }

    public int getOrder() {
        return order;
    }

    public int getPreference() {
        return preference;
    }

    public String getFlags() {
        return flags;
    }

    public String getService() {
        return svc;
    }

    public String getRegex() {
        return regex;
    }

    public String getReplacement() {
        return replacement;
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element e164Naptr = xmlWriter.appendChild(parent, "naptr");

        xmlWriter.appendChild(e164Naptr, "order").setTextContent(
                String.valueOf(order));
        xmlWriter.appendChild(e164Naptr, "pref").setTextContent(
                String.valueOf(preference));
        xmlWriter.appendChild(e164Naptr, "flags").setTextContent(
                flags);
        xmlWriter.appendChild(e164Naptr, "svc").setTextContent(
                svc);
        if (regex != null) {
            xmlWriter.appendChild(e164Naptr, "regex").setTextContent(
                    regex);
        }
        if (replacement != null) {
            xmlWriter.appendChild(e164Naptr, "repl").setTextContent(
                    replacement);
        }

        return e164Naptr;
    }
}

