package com.ausregistry.jtoolkit2.se;

/**
 * Use this to cancel the transfer of a domain object currently pending
 * transfer.  The transfer must have been initiated via a transfer request by
 * the client attempting to cancel the transfer.  Instances of this class
 * generate RFC5730 and RFC5731 compliant domain transfer EPP command service
 * elements via the toXML method with the transfer operation set to "cancel".
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 */
public class DomainTransferCancelCommand extends DomainTransferCommand {
    private static final long serialVersionUID = 4459609896155243761L;

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the 'cancel' transfer operation.
     *
     * @param name The name of the domain to cancel transfer of.
     */
    public DomainTransferCancelCommand(String name) {
        super(TransferOp.CANCEL, name);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'cancel' transfer operation.
     *
     * @param name The name of the domain to cancel transfer of.
     *
     * @param pw The identified domain's password.
     */
    public DomainTransferCancelCommand(String name, String pw) {
        super(TransferOp.CANCEL, name, pw);
    }

    /**
     * Create a domain transfer command for the identified domain, specifying
     * the designated password and the 'cancel' transfer operation.
     *
     * @param name The name of the domain to cancel transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    public DomainTransferCancelCommand(String name, String roid, String pw) {
        super(TransferOp.CANCEL, name, roid, pw);
    }
}

