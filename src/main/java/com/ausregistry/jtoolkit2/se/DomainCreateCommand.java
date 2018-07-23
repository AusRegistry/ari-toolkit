package com.ausregistry.jtoolkit2.se;

import static com.ausregistry.jtoolkit2.se.Host.hostObjects;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Mapping of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified in
 * RFC5731.  Command-response extensions to the domain:create command are
 * implemented as subclasses of this.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 */
public class DomainCreateCommand extends CreateCommand {
    protected static final String[] EMPTY_NAMESERVERS = null;
    private static final long serialVersionUID = -5572484023403174763L;

    /**
     * Minimal constructor for creating a domain:create EPP command.  This is
     * the least information required for a valid EPP domain create command.
     */
    public DomainCreateCommand(String name, String pw) {
        this(name, pw, null, null, null, null, EMPTY_NAMESERVERS, null);
    }

    public DomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts) {
        this(name, pw, registrantID, techContacts, null, null, EMPTY_NAMESERVERS, null);
    }

    public DomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts, String[] nameservers) {
        this(name, pw, registrantID, techContacts, null, null, nameservers, null);
    }

    public DomainCreateCommand(String name, String pw, String registrantID,
        String[] techContacts, String[] adminContacts,
        String[] billingContacts, String[] nameservers, Period period) {
        this(name, pw, registrantID, techContacts, adminContacts, billingContacts,
                hostObjects(nameservers), period, false);
    }

    public DomainCreateCommand(String name, String pw, String registrantID,
        String[] techContacts, String[] adminContacts,
        String[] billingContacts, Host[] nameservers, Period period) {
        this(name, pw, registrantID, techContacts, adminContacts, billingContacts,
                nameservers, period, true);
    }

    /**
     * Most verbose constructor for a domain:create EPP command. All core EPP
     * domain:create attributes may be set using this constructor.
     * nameserver will be passed as attribute in this command
     *
     * @throws IllegalArgumentException if {@code name} or {@code pw} are {@code null}.
     */
    private DomainCreateCommand(String name, String pw, String registrantID,
        String[] techContacts, String[] adminContacts,
        String[] billingContacts, Host[] nameservers, Period period, boolean asHostAttribute) {
        super(StandardObjectType.DOMAIN, name);

        if (name == null || pw == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                "se.domain.create.missing_arg"));
        }

        if (period != null) {
            period.appendPeriod(xmlWriter, objElement);
        }

        if (nameservers != null && nameservers.length > 0) {
            Element ns = xmlWriter.appendChild(objElement, "ns");
            if (asHostAttribute) {
                for (Host hostAttr : nameservers) {
                    hostAttr.appendToElement(xmlWriter, ns);
                }
            } else {
                for (Host hostObj : nameservers) {
                    xmlWriter.appendChild(ns, "hostObj").setTextContent(hostObj.getName());
                }
            }
        }

        if (registrantID != null) {
            xmlWriter.appendChild(objElement, "registrant").setTextContent(
                registrantID);
        }

        if (adminContacts != null) {
            for (String contactID : adminContacts) {
                xmlWriter.appendChild(objElement, "contact", contactID, "type", "admin");
            }
        }

        if (techContacts != null) {
            for (String contactID : techContacts) {
                xmlWriter.appendChild(objElement, "contact", contactID, "type", "tech");
            }
        }

        if (billingContacts != null) {
            for (String contactID : billingContacts) {
                xmlWriter.appendChild(objElement, "contact", contactID, "type", "billing");
            }
        }

        xmlWriter.appendChild(
            xmlWriter.appendChild(
                objElement,
                "authInfo"),
            "pw").setTextContent(pw);
    }
}

