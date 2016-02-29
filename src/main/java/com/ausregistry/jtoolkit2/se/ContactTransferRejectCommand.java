package com.ausregistry.jtoolkit2.se;

/**
 * Use this to reject the transfer of a contact object currently pending
 * transfer.  The contact object must be sponsored by the client attempting to
 * reject the transfer.  Instances of this class generate RFC5730 and RFC5733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "reject".
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferResponse
 */
public class ContactTransferRejectCommand extends ContactTransferCommand {
    private static final long serialVersionUID = -3441305501001365568L;

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the 'reject' transfer operation.
     *
     * @param id The identifier of the contact to reject transfer of.
     */
    public ContactTransferRejectCommand(String id) {
        super(TransferOp.REJECT, id);
    }

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the designated password and the 'reject' transfer operation.
     *
     * @param id The identifier of the contact to reject transfer of.
     *
     * @param pw The identified contact's password.
     */
    public ContactTransferRejectCommand(String id, String pw) {
        super(TransferOp.REJECT, id, pw);
    }
}

