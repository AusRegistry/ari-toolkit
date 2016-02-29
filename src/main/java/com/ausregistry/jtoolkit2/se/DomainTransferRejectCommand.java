package com.ausregistry.jtoolkit2.se;

/**
 * Use this to reject the transfer of a domain object currently pending
 * transfer.  The domain object must be sponsored by the client attempting to
 * reject the transfer.  Instances of this class generate RFC5730 and RFC5731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "reject".
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 */
public class DomainTransferRejectCommand extends DomainTransferCommand {
    private static final long serialVersionUID = -4558124546837429882L;

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the 'reject' transfer operation.
     *
     * @param name The name of the domain to reject transfer of.
     */
    public DomainTransferRejectCommand(String name) {
        super(TransferOp.REJECT, name);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param name The name of the domain to reject transfer of.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferRejectCommand(String name, String pw) {
        super(TransferOp.REJECT, name, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param name The name of the domain to reject transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied ROID.
     */
    public DomainTransferRejectCommand(String name, String roid, String pw) {
        super(TransferOp.REJECT, name, roid, pw);
    }
}

