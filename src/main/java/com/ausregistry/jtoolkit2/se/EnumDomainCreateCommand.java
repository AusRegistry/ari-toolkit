package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;


/**
 * Use this to request provisioning of an ENUM domain object in an EPP
 * Registry.  Instances of this class generate domain create EPP service
 * elements compliant with RFC5730, RFC5731 and RFC4114 via the toXML method.
 */
public final class EnumDomainCreateCommand extends DomainCreateCommand {
    private static final long serialVersionUID = 1661618997961186028L;

    /**
     * Minimal constructor for creating a domain:create + e164epp:create
     * EPP command.  These parameters are the least required for a valid
     * ENUM domain create command.
     */
    public EnumDomainCreateCommand(String name, String pw,
            String registrantID, String[] techContacts) {
        super(name, pw, registrantID, techContacts);
    }

    /**
     * Construct a domain:create + e164epp:create EPP command with NAPTR
     * records.  This is the least information required to provision an ENUM
     * domain with NAPTR records.
     */
    public EnumDomainCreateCommand(String name, String pw,
            String registrantID, String[] techContacts,
            NAPTR[] naptrs) {
        super(name, pw, registrantID, techContacts);
        setExtension(naptrs);
    }

    /**
     * Full data specification constructor for a domain:create + e164epp:create
     * EPP command with NAPTR records.  Please refer to the
     * urn:ietf:params:xml:ns:e164epp-1.0 schema for specification of the
     * required fields.
     */
    public EnumDomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts, String[] adminContacts,
            String[] billingContacts,
            NAPTR[] naptrs, Period period) {
        super(name, pw, registrantID,
                techContacts, adminContacts, billingContacts,
                EMPTY_NAMESERVERS, period);
        setExtension(naptrs);
    }

    /**
     * Full data specification constructor for a domain:create + e164epp:create
     * EPP command with nameservers rather than NAPTR records.  This
     * constructor does not cause the e164epp extension element to be created,
     * since NAPTR records are not specified.
     */
    public EnumDomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts, String[] adminContacts,
            String[] billingContacts,
            String[] nameservers, Period period) {
        super(name, pw, registrantID, techContacts,
                adminContacts, billingContacts, nameservers, period);
    }

    private void setExtension(NAPTR[] naptrs) {
        if (naptrs == null || naptrs.length == 0) {
            return;
        }

        extension = xmlWriter.appendChild(command, "extension");
        Element extensionElement = xmlWriter.appendChild(extension, "create", ExtensionImpl.E164.getURI());

        for (NAPTR naptr : naptrs) {
            naptr.appendToElement(xmlWriter, extensionElement);
        }
    }
}

