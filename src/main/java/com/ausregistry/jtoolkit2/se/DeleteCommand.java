package com.ausregistry.jtoolkit2.se;

/**
 * Representation of the EPP delete command, as defined in RFC5730.
 * Subclasses of this must specify at a minimum the object to which the command
 * is mapped and the object-specific identifier of the object to delete.
 */
public abstract class DeleteCommand extends ObjectCommand {

    private static final long serialVersionUID = -4790273172825054472L;

    /**
     * Construct a delete command of the given object type mapping with the
     * given object identifier.
     *
     * @param objType The type of object to which the delete command is to be
     * mapped.
     *
     * @param ident The identifier of the object to be created.
     */
    public DeleteCommand(ObjectType objType, String ident) {
        super(StandardCommandType.DELETE, objType, ident);
    }
}

