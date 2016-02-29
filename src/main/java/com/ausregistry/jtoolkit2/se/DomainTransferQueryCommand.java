package com.ausregistry.jtoolkit2.se;

/**
 * Use this to query the transfer state of a domain object.  Instances of this
 * class generate RFC5730 and RFC5731 compliant domain transfer EPP command
 * service elements via the toXML method with the transfer operation set to
 * "query".
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 */
public class DomainTransferQueryCommand extends DomainTransferCommand {
    private static final long serialVersionUID = 4925536271075701036L;

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     */
    public DomainTransferQueryCommand(String name) {
        super(TransferOp.QUERY, name);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferQueryCommand(String name, String pw) {
        super(TransferOp.QUERY, name, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied ROID.
     */
    public DomainTransferQueryCommand(String name, String roid, String pw) {
        super(TransferOp.QUERY, name, roid, pw);
    }
}

