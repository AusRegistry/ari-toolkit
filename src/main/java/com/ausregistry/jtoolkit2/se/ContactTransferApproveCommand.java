package com.ausregistry.jtoolkit2.se;

/**
 * Use this to approve the transfer of a contact object currently pending
 * transfer.  The contact object must be sponsored by the client attempting to
 * approve the transfer.  Instances of this class generate RFC5730 and RFC5733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "approve".
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferResponse
 */
public class ContactTransferApproveCommand extends ContactTransferCommand {
    private static final long serialVersionUID = -8722539297610164487L;

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the 'approve' transfer operation.
     *
     * @param id The identifier of the contact to approve transfer of.
     */
    public ContactTransferApproveCommand(String id) {
        super(TransferOp.APPROVE, id);
    }

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the designated password and the 'approve' transfer operation.
     *
     * @param id The identifier of the contact to approve transfer of.
     *
     * @param pw The identified contact's password.
     */
    public ContactTransferApproveCommand(String id, String pw) {
        super(TransferOp.APPROVE, id, pw);
    }
}

