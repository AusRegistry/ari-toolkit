package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request that a domain object be deleted from an EPP Registry.
 * Instances of this class generate RFC5730 and RFC5731 compliant domain
 * delete EPP command service elements via the toXML method.
 */
public class DomainDeleteCommand extends DeleteCommand {
    private static final long serialVersionUID = -2941660288275937680L;

    /**
     * Delete the identified domain.
     *
     * @param name The name of the domain to delete.
     */
    public DomainDeleteCommand(String name) {
        super(StandardObjectType.DOMAIN, name);
    }
}

