package com.ausregistry.jtoolkit2.se;

/**
 * The superclass of all contact transfer command classes.  Subclasses are
 * responsible for specifying the kind of transfer operation, but hiding the
 * implementation from the user.
 */
public abstract class ContactTransferCommand extends TransferCommand {
	public ContactTransferCommand(TransferOp operation, String id) {
		super(StandardObjectType.CONTACT, operation, id);
	}

	public ContactTransferCommand(TransferOp operation, String id,
			String pw) {
		super(StandardObjectType.CONTACT, operation, id, pw);
	}
}

