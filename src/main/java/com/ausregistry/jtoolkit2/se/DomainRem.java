package com.ausregistry.jtoolkit2.se;

/**
 * Use this to specify attributes to remove from a domain object in a domain
 * update EPP command service element.  The DomainUpdateCommand uses an
 * instance of this to build the appropriate elements in order to request the
 * removal of these attributes from a domain object.
 */
public class DomainRem extends DomainAddRem {
    private static final long serialVersionUID = 8320662022260490572L;

    /**
     * Remove associations from a domain.
     *
     * @param nameservers A list of hostnames to no longer delegate the
     * associated domain to.  To not remove any nameservers, this parameter
     * must be null.
     *
     * @param techContacts A list of technical contact identifiers to
     * disassociate from a domain.  To not remove any technical contacts,
     * this parameter must be null.
     *
     * @param adminContacts A list of admin contact identifiers to
     * disassociate from a domain.  To not remove any admin contacts,
     * this parameter must be null.
     *
     * @param billingContacts A list of billing contact identifiers to
     * disassociate from a domain.  To not remove any billing contacts,
     * this parameter must be null.
     *
     * @param statuses A list of statuses to remove from the domain.  To
     * not remove any statuses, this parameter must be null.
     */
    public DomainRem(String[] nameservers, String[] techContacts,
            String[] adminContacts, String[] billingContacts, Status[] statuses) {
        super(AddRemType.REM, nameservers, techContacts, adminContacts,
                billingContacts, statuses);
    }

    /**
     * Remove associations from a domain.
     *
     * @param nameservers A  host as attribute with
     * associated domain. To not remove any nameservers, this parameter
     * must be null.
     *
     * @param techContacts A list of technical contact identifiers to
     * disassociate from a domain. To not remove any technical contacts,
     * this parameter must be null.
     *
     * @param adminContacts A list of admin contact identifiers to
     * disassociate from a domain. To not remove any admin contacts,
     * this parameter must be null.
     *
     * @param billingContacts A list of billing contact identifiers to
     * disassociate from a domain. To not remove any billing contacts,
     * this parameter must be null.
     *
     * @param statuses A list of statuses to remove from the domain. To
     * not remove any statuses, this parameter must be null.
     */
    public DomainRem(Host[] nameservers, String[] techContacts,
        String[] adminContacts, String[] billingContacts, Status[] statuses) {
        super(AddRemType.REM, nameservers, techContacts, adminContacts,
            billingContacts, statuses);

    }
}
