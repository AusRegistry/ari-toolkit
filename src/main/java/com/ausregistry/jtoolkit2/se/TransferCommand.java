package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Representation of the EPP transfer command, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped,
 * the type of transfer operation and specify the object-specific identifier of
 * the object to create a transfer command for.
 *
 * @see com.ausregistry.jtoolkit2.se.TransferResponse
 */
public abstract class TransferCommand extends ObjectCommand {

    private static final long serialVersionUID = 2577041132486433812L;

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform. Required.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @throws IllegalArgumentException if {@code operation} is {@code null}.
     */
    public TransferCommand(ObjectType objType, TransferOp operation,
            String ident) {

        super(StandardCommandType.TRANSFER, objType, ident);

        if (operation == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.transfer.operation.missing"));
        }

        cmdElement.setAttribute("op", operation.toString());
    }

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param pw The password of the object subject to the transfer command -
     * also referred to as authInfo or authorisation information.
     */
    public TransferCommand(ObjectType objType, TransferOp operation,
            String ident, String pw) {

        this(objType, operation, ident);

        appendPW(pw);
    }

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param period The validity period of the identified object should be
     * extended by this duration upon successful completion of the transfer
     * related to this command. Required.
     *
     * @param pw The password of the object subject to the transfer command -
     * also referred to as authInfo or authorisation information.
     *
     * @throws IllegalArgumentException if {@code period} is {@code null}.
     */
    public TransferCommand(ObjectType objType, TransferOp operation,
            String ident, Period period, String pw) {

        this(objType, operation, ident);

        if (period == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.transfer.period.missing"));
        }

        period.appendPeriod(xmlWriter, objElement);
        appendPW(pw);
    }

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param roid The Repository Object Identifier of an object related to the
     * identified object subject to transfer which is considered a suitable
     * proxy for authorising transfer commands.
     *
     * @param pw The password of the related object identified by roid.
     */
    public TransferCommand(ObjectType objType, TransferOp operation,
            String ident, String roid, String pw) {
        this(objType, operation, ident, null, roid, pw);
    }

    /**
     * Create a transfer command of the specified operation type mapped to the
     * specified object type for the identified object.
     *
     * @param objType The type of object to which the transfer command is to be
     * mapped.
     *
     * @param operation The type of transfer operation to perform.
     *
     * @param ident An object type-specific label identifying the object
     * subject to the transfer command.
     *
     * @param period The validity period of the identified object should be
     * extended by this duration upon successful completion of the transfer
     * related to this command.
     *
     * @param roid The Repository Object Identifier of an object related to the
     * identified object subject to transfer which is considered a suitable
     * proxy for authorising transfer commands.
     *
     * @param pw The password of the related object identified by ROID.
     */
    public TransferCommand(ObjectType objType, TransferOp operation,
            String ident, Period period, String roid, String pw) {

        this(objType, operation, ident);

        if (period != null) {
            period.appendPeriod(xmlWriter, objElement);
        }

        if (roid != null) {
            appendPW(pw, roid);
        } else {
            appendPW(pw);
        }
    }

    private void appendPW(String pw, String roid) {
        if (pw == null || roid == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.transfer.pw_roid.missing"));
        }

        xmlWriter.appendChild(
                xmlWriter.appendChild(
                    objElement,
                    "authInfo"),
                "pw",
                new String[] {"roid"},
                new String[] {roid}).setTextContent(pw);
    }

    private void appendPW(String pw) {
        if (pw == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.transfer.pw.missing"));
        }

        xmlWriter.appendChild(
                xmlWriter.appendChild(
                    objElement,
                    "authInfo"),
                "pw").setTextContent(pw);
    }
}

