package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request information about a host object provisioned in an EPP
 * Registry.  Instances of this class generate RFC5730 and RFC5732 compliant
 * host info EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.HostInfoResponse
 */
public class HostInfoCommand extends InfoCommand {
    private static final long serialVersionUID = 8483563284887107960L;

    /**
     * Create a host info command with the specified identifier.
     *
     * @param name The name of the host to retrieve information about.
     */
    public HostInfoCommand(String name) {
        super(StandardObjectType.HOST, name);
    }
}

