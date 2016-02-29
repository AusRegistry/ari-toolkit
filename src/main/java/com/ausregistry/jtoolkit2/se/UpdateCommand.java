package com.ausregistry.jtoolkit2.se;

/**
 * Representation of the EPP update command, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifier of the object to update.
 *
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public abstract class UpdateCommand extends ObjectCommand {

    private static final long serialVersionUID = -5658605879430546350L;

    /**
     * Create an update command mapped to the specified object type to update
     * the identified object.
     *
     * @param objType The type of object to which the update command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * update.
     */
    public UpdateCommand(ObjectType objType, String ident) {
        super(StandardCommandType.UPDATE, objType, ident);
    }
}

