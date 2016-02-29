package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request information about a contact object provisioned in an EPP
 * Registry.  Instances of this class generate RFC5730 and RFC5733 compliant
 * contact info EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactInfoResponse
 */
public class ContactInfoCommand extends InfoCommand {
    private static final long serialVersionUID = 6091051138019921698L;

    /**
     * Create a contact info command with the specified identifier.
     *
     * @param id The identifier of the contact to retrieve information about.
     */
    public ContactInfoCommand(String id) {
        super(StandardObjectType.CONTACT, id);
    }

    /**
     * Create a contact info command with the specified identifier.
     *
     * @param id The identifier of the contact to retrieve information about.
     *
     * @param pw The password of the identified contact object (also known as
     * authInfo or authorisation information).
     */
    public ContactInfoCommand(String id, String pw) {
        super(StandardObjectType.CONTACT, id, pw);
    }
}

