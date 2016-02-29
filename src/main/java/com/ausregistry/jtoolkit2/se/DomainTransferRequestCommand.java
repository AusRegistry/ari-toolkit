package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request the transfer of a domain object from another client.
 * The domain object MUST NOT be sponsored by the client attempting to request
 * the transfer.  Instances of this class generate RFC5730 and RFC5731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "request".
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 */
public class DomainTransferRequestCommand extends DomainTransferCommand {
    private static final long serialVersionUID = 4786406676232060462L;

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferRequestCommand(String name, String pw) {
        super(TransferOp.REQUEST, name, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied ROID.
     */
    public DomainTransferRequestCommand(String name, String roid, String pw) {
        super(TransferOp.REQUEST, name, roid, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param period The period of time to extend the validity period of the
     * domain by upon approval of the transfer.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferRequestCommand(String name, Period period, String pw) {
        super(TransferOp.REQUEST, name, period, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param period The period of time to extend the validity period of the
     * domain by upon approval of the transfer.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied ROID.
     */
    public DomainTransferRequestCommand(String name, Period period,
            String roid, String pw) {
        super(TransferOp.REQUEST, name, period, roid, pw);
    }
}

