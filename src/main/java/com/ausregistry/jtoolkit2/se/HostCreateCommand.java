package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request that a host object be provisioned in an EPP Registry.
 * Instances of this class generate RFC5730 and RFC5732 compliant host create
 * EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.HostCreateResponse
 */
public class HostCreateCommand extends CreateCommand {
    private static final long serialVersionUID = -3161264324911932444L;

    /**
     * Provision a host with the specified details which constitute the
     * minimum valid parameters according to the EPP specification.  This is
     * the appropriate constructor to use for provisioning external hosts.
     *
     * @param name The new host's name.
     */
    public HostCreateCommand(String name) {
        this(name, null);
    }

    /**
     * Provision a host with the specified details.  This constructor allows
     * specification of any and all parameters for a host create command.
     *
     * @param name The new host's name.
     *
     * @param addresses The Internet addresses of the host to be provisioned.
     * These should only be specified if the parent domain is sponsored by the
     * client provisioning this host and the parent domain is provisioned in
     * the domain name registry in which this host is being provisioned.  That
     * is, external hosts must not be assigned Internet addresses.
     */
    public HostCreateCommand(String name, InetAddress[] addresses) {
        super(StandardObjectType.HOST, name);

        if (addresses != null) {
            for (InetAddress inaddr : addresses) {
                inaddr.appendToElement(xmlWriter, objElement);
            }
        }
    }
}

