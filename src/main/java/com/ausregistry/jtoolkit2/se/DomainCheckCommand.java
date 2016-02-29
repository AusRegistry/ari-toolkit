package com.ausregistry.jtoolkit2.se;

/**
 * A DomainCheckCommand is used to check the availability of domain objects
 * in a Registry.  Instances of this class generate RFC5730 and RFC5731
 * compliant domain check EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckResponse
 */
public class DomainCheckCommand extends CheckCommand {
    private static final long serialVersionUID = 3050267498759687925L;

    /**
     * Check the availability of the single identified domain.
     *
     * @param name The name of the domain to check the availability of.
     */
    public DomainCheckCommand(String name) {
        super(StandardObjectType.DOMAIN, name);
    }

    /**
     * Check the availability of at least one domain.
     *
     * @param names The names of the domains to check the availability of.
     */
    public DomainCheckCommand(String[] names) {
        super(StandardObjectType.DOMAIN, names);
    }
}

