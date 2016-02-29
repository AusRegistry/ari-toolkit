package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request that a host object be deleted from an EPP Registry.
 * Instances of this class generate RFC5730 and RFC5732 compliant host delete
 * EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public class HostDeleteCommand extends DeleteCommand {
    private static final long serialVersionUID = -6471660003899153889L;

    /**
     * Delete the identified host.
     *
     * @param name The name of the host to delete.
     */
    public HostDeleteCommand(String name) {
        super(StandardObjectType.HOST, name);
    }
}

