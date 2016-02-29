package com.ausregistry.jtoolkit2.se;

/**
 * A HostCheckCommand is used to check the availability of host objects
 * in a Registry.  Instances of this class generate RFC5730 and RFC5732
 * compliant host check EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.HostCheckResponse
 */
public class HostCheckCommand extends CheckCommand {
    private static final long serialVersionUID = 8765121289983170340L;

    /**
     * Check the availability of the single identified host.
     *
     * @param name The name of the host to check the availability of.
     */
    public HostCheckCommand(String name) {
        super(StandardObjectType.HOST, name);
    }

    /**
     * Check the availability of at least one host.
     *
     * @param names The names of the hosts to check the availability of.
     */
    public HostCheckCommand(String[] names) {
        super(StandardObjectType.HOST, names);
    }
}

