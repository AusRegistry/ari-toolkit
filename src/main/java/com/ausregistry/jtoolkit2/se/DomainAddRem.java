package com.ausregistry.jtoolkit2.se;

import static com.ausregistry.jtoolkit2.se.Host.hostObjects;

import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Specification of how to write the add and rem elements to a domain update
 * command.  Use subclasses of this to set attributes to add or remove from a
 * domain object.
 */
public abstract class DomainAddRem implements Appendable {

    private static final long serialVersionUID = -5023022665651367748L;

    private String type;
    private Host[] hosts;
    private boolean asHostAttrType;

    private String[] techContacts;
    private String[] adminContacts;
    private String[] billingContacts;
    private Status[] statuses;

    /**
     * Maximal specification of the attribute values which may be added or
     * removed from a domain. Each of the parameters is optional, but at least
     * one must be specified.
     */
    public DomainAddRem(AddRemType type, String[] nameservers,
        String[] techContacts, String[] adminContacts,
        String[] billingContacts, Status[] statuses) {

        this(type, hostObjects(nameservers), techContacts, adminContacts, billingContacts, statuses);
        this.asHostAttrType = false;
    }

    /**
     * Maximal specification of the attribute values which may be added or
     * removed from a domain. Each of the parameters is optional, but at least
     * one must be specified.
     */
    public DomainAddRem(AddRemType type, Host[] nameservers,
        String[] techContacts, String[] adminContacts,
        String[] billingContacts, Status[] statuses) {
        this.type = type.toString();

        if (nameservers != null) {
            this.hosts = nameservers.clone();
        }
        if (techContacts != null) {
            this.techContacts = techContacts.clone();
        }
        if (adminContacts != null) {
            this.adminContacts = adminContacts.clone();
        }
        if (billingContacts != null) {
            this.billingContacts = billingContacts.clone();
        }
        if (statuses != null) {
            this.statuses = statuses.clone();
        }

        this.asHostAttrType = true;
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element addRem = xmlWriter.appendChild(parent, type);

        if (hosts != null) {
            Element ns = xmlWriter.appendChild(addRem, "ns");
            for (Host hostAttr : hosts) {
                if (asHostAttrType) {
                    hostAttr.appendToElement(xmlWriter, ns);
                } else {
                    xmlWriter.appendChild(ns, "hostObj").setTextContent(hostAttr.getName());
                }
            }
        }

        if (techContacts != null) {
            for (String contactID : techContacts) {
                xmlWriter.appendChild(addRem, "contact", contactID, "type",
                        "tech");
            }
        }

        if (adminContacts != null) {
            for (String contactID : adminContacts) {
                xmlWriter.appendChild(addRem, "contact", contactID, "type",
                        "admin");
            }
        }

        if (billingContacts != null) {
            for (String contactID : billingContacts) {
                xmlWriter.appendChild(addRem, "contact", contactID, "type",
                        "billing");
            }
        }

        if (statuses != null) {
            for (Status status : statuses) {
                Element s = xmlWriter.appendChild(addRem, "status", status
                        .getRationale(), "s", status.toString());
                if (status.getLanguage() != null) {
                    s.setAttribute("lang", status.getLanguage());
                }
            }
        }

        return addRem;
    }

}

