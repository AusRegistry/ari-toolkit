package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request that a contact object be deleted from an EPP Registry.
 * Instances of this class generate RFC5730 and RFC5733 compliant contact
 * delete EPP command service elements via the toXML method.
 */
public class ContactDeleteCommand extends DeleteCommand {
    private static final long serialVersionUID = 8454618926166260306L;

    /**
     * Delete the identified contact.
     *
     * @param id The identifier of the contact to delete.
     */
    public ContactDeleteCommand(String id) {
        super(StandardObjectType.CONTACT, id);
    }
}

