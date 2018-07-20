package com.ausregistry.jtoolkit2.se;

/**
 * Use this to specify attributes to add to a domain object in a domain update
 * EPP command service element.  The DomainUpdateCommand uses an instance of
 * this to build the appropriate elements in order to request the addition of
 * these attributes to a domain object.
 */
public class DomainAdd extends DomainAddRem {
    private static final long serialVersionUID = -1134547077407469383L;

    /**
     * Add associations to a domain.
     *
     * @param nameservers A list of hostnames to delegate the associated domain
     * to.  To not add any nameservers, this parameter must be null.
     *
     * @param techContacts A list of technical contact identifiers to associate
     * with a domain.  To not add any technical contacts, this parameter must
     * be null.
     *
     * @param adminContacts A list of admin contact identifiers to associate
     * with a domain.  To not add any admin contacts, this parameter must be
     * null.
     *
     * @param billingContacts A list of billing contact identifiers to
     * associate with a domain.  To not add any billing contacts, this
     * parameter must be null.
     *
     * @param statuses A list of statuses to add to a domain.  To not add any
     * statuses, this parameter must be null.
     */
    public DomainAdd(String[] nameservers, String[] techContacts,
            String[] adminContacts, String[] billingContacts,
            Status[] statuses) {

        super(AddRemType.ADD, nameservers, techContacts, adminContacts,
                billingContacts, statuses);
    }

    /**
     * Add associations to a domain.
     *
     * @param nameservers A list of host as attribute with the associated domain
     * To not add any nameservers, this parameter must be null.
     *
     * @param techContacts A list of technical contact identifiers to associate
     * with a domain.  To not add any technical contacts, this parameter must
     * be null.
     *
     * @param adminContacts A list of admin contact identifiers to associate
     * with a domain.  To not add any admin contacts, this parameter must be
     * null.
     *
     * @param billingContacts A list of billing contact identifiers to
     * associate with a domain.  To not add any billing contacts, this
     * parameter must be null.
     *
     * @param statuses A list of statuses to add to a domain.  To not add any
     * statuses, this parameter must be null.
     */
    public DomainAdd(Host[] nameservers, String[] techContacts,
        String[] adminContacts, String[] billingContacts, Status[] statuses) {

        super(AddRemType.ADD, nameservers, techContacts, adminContacts,
            billingContacts, statuses);
    }
}

