package com.ausregistry.jtoolkit2.se;

/**
 * Use this to cancel the transfer of a contact object currently pending
 * transfer.  The transfer must have been initiated via a transfer request by
 * the client attempting to cancel the transfer.  Instances of this class
 * generate RFC5730 and RFC5733 compliant contact transfer EPP command service
 * elements via the toXML method with the transfer operation set to "cancel".
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferResponse
 */
public class ContactTransferCancelCommand extends ContactTransferCommand {
    private static final long serialVersionUID = 5095509367768233711L;

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the 'cancel' transfer operation.
     *
     * @param id The identifier of the contact to cancel transfer of.
     */
    public ContactTransferCancelCommand(String id) {
        super(TransferOp.CANCEL, id);
    }

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the designated password and the 'cancel' transfer operation.
     *
     * @param id The identifier of the contact to cancel transfer of.
     *
     * @param pw The identified contact's password.
     */
    public ContactTransferCancelCommand(String id, String pw) {
        super(TransferOp.CANCEL, id, pw);
    }
}

