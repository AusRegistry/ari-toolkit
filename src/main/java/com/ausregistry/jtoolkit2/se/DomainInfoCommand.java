package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.Attribute;

/**
 * Use this to request information about a domain object provisioned in an EPP
 * Registry.  Instances of this class generate RFC5730 and RFC5731 compliant
 * domain info EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoResponse
 */
public class DomainInfoCommand extends InfoCommand {
    private static final long serialVersionUID = -9129030981710943397L;

    /**
     * Create a domain info command with the specified identifier.
     *
     * @param name The name of the domain to retrieve information about.
     */
    public DomainInfoCommand(String name) {
            super(StandardObjectType.DOMAIN, name);
    }

    /**
     * Create a domain info command with the specified identifier and host attribute.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param hostAttribute The identifier and value of the host attribute to be supplied
     */
    public DomainInfoCommand(String name, Attribute hostAttribute) {
        super(StandardObjectType.DOMAIN, name, hostAttribute);
    }

    /**
     * Create a domain info command with the specified identifier,
     * authorisation information and host attribute.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     */
    public DomainInfoCommand(String name, String pw) {
        super(StandardObjectType.DOMAIN, name, pw);
    }

    /**
     * Create a domain info command with the specified identifier and
     * authorisation information.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     *
     * @param hostAttribute The identifier and value of the host attribute to be supplied
     */
    public DomainInfoCommand(String name, String pw, Attribute hostAttribute) {
        super(StandardObjectType.DOMAIN, name, pw, hostAttribute);
    }

    /**
     * Create a domain info command with the specified identifier and
     * authorisation information of an associated contact.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param roid The Repository Object Identifer of a contact object
     * associated with the identified domain.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     */
    public DomainInfoCommand(String name, String roid, String pw) {
        super(StandardObjectType.DOMAIN, name, roid, pw);
    }

    /**
     * Create a domain info command with the specified identifier,
     * authorisation information of an associated contact and host attribute.
     *
     * @param name The name of the domain to retrieve information about.
     *
     * @param roid The Repository Object Identifer of a contact object
     * associated with the identified domain.
     *
     * @param pw The password of the identified domain object (also known as
     * authInfo or authorisation information).
     *
     * @param hostAttribute The identifier and value of the host attribute to be supplied
     */
    public DomainInfoCommand(String name, String roid, String pw, Attribute hostAttribute) {
        super(StandardObjectType.DOMAIN, name, roid, pw, hostAttribute);
    }

}

