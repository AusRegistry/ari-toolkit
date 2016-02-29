package com.ausregistry.jtoolkit2.se;

/**
 * Use this to query the transfer state of a contact object.  Instances of this
 * class generate RFC5730 and RFC5733 compliant contact transfer EPP command
 * service elements via the toXML method with the transfer operation set to
 * "query".
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferResponse
 */
public class ContactTransferQueryCommand extends ContactTransferCommand {
    private static final long serialVersionUID = -3757779708062910617L;

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the 'query' transfer operation.
     *
     * @param id The identifier of the contact to query the transfer state of.
     */
    public ContactTransferQueryCommand(String id) {
        super(TransferOp.QUERY, id);
    }

    /**
     * Create a contact transfer command for the identified contact, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param id The identifier of the contact to query the transfer state of.
     *
     * @param pw The identified contact's password.
     */
    public ContactTransferQueryCommand(String id, String pw) {
        super(TransferOp.QUERY, id, pw);
    }
}

