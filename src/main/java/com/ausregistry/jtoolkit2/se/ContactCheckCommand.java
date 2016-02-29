package com.ausregistry.jtoolkit2.se;

/**
 * A ContactCheckCommand is used to check the availability of contact objects
 * in a Registry.  Instances of this class generate RFC5730 and RFC5733
 * compliant contact check EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactCheckResponse
 */
public class ContactCheckCommand extends CheckCommand {
    private static final long serialVersionUID = -6528727076639715585L;

    /**
     * Check the availability of the single identified contact.
     *
     * @param id The identifier of the contact to check the availability of.
     */
    public ContactCheckCommand(String id) {
        super(StandardObjectType.CONTACT, id);
    }

    /**
     * Check the availability of at least one contact.
     *
     * @param ids The identifiers of the contacts to check the availability of.
     */
    public ContactCheckCommand(String[] ids) {
        super(StandardObjectType.CONTACT, ids);
    }
}

