package com.ausregistry.jtoolkit2.se;

/**
 * Use this to approve the transfer of a domain object currently pending
 * transfer.  The domain object must be sponsored by the client attempting to
 * approve the transfer.  Instances of this class generate RFC5730 and RFC5731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "approve".
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 */
public class DomainTransferApproveCommand extends DomainTransferCommand {
    private static final long serialVersionUID = 5057086047385703151L;

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the 'approve' transfer operation.
     *
     * @param name The name of the domain to approve transfer of.
     */
    public DomainTransferApproveCommand(String name) {
        super(TransferOp.APPROVE, name);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'approve' transfer operation.
     *
     * @param name The name of the domain to approve transfer of.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferApproveCommand(String name, String pw) {
        super(TransferOp.APPROVE, name, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'approve' transfer operation.
     *
     * @param name The name of the domain to approve transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    public DomainTransferApproveCommand(String name, String roid, String pw) {
        super(TransferOp.APPROVE, name, roid, pw);
    }
}

