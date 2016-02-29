package com.ausregistry.jtoolkit2.se;

/**
 * The superclass of all domain transfer command classes.  Subclasses are
 * responsible for specifying the kind of transfer operation, but hiding the
 * implementation from the user.
 */
public abstract class DomainTransferCommand extends TransferCommand {

    private static final long serialVersionUID = -5730286980387444992L;

    public DomainTransferCommand(TransferOp operation, String name) {
        super(StandardObjectType.DOMAIN, operation, name);
    }

    public DomainTransferCommand(TransferOp operation, String name,
            String pw) {
        super(StandardObjectType.DOMAIN, operation, name, pw);
    }

    public DomainTransferCommand(TransferOp operation, String name,
            String roid, String pw) {
        super(StandardObjectType.DOMAIN, operation, name, roid, pw);
    }

    public DomainTransferCommand(TransferOp operation, String name,
            Period period, String pw) {
        super(StandardObjectType.DOMAIN, operation, name, period, pw);
    }

    public DomainTransferCommand(TransferOp operation, String name,
            Period period, String roid, String pw) {
        super(StandardObjectType.DOMAIN, operation, name, period, roid, pw);
    }
}

