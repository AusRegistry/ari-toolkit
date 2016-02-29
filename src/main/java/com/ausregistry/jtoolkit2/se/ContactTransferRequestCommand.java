package com.ausregistry.jtoolkit2.se;

/**
 * Use this to request the transfer of a contact object from another client.
 * The contact object MUST NOT be sponsored by the client attempting to request
 * the transfer.  Instances of this class generate RFC5730 and RFC5733
 * compliant contact transfer EPP command service elements via the toXML method
 * with the transfer operation set to "request".
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferResponse
 */
public class ContactTransferRequestCommand extends ContactTransferCommand {
    private static final long serialVersionUID = -9000322724457879276L;

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param id The identifier of the contact to request transfer of.
     *
     * @param pw The identified contact's password.
     */
    public ContactTransferRequestCommand(String id, String pw) {
        super(TransferOp.REQUEST, id, pw);
    }
}

