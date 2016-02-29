package com.ausregistry.jtoolkit2.se;

/**
 * Representation of the EPP create command, as defined in RFC5730.
 * Subclasses of this must specify at a minimum the object to which the command
 * is mapped and the object-specific identifier of the object to create.
 */
public abstract class CreateCommand extends ObjectCommand {

    private static final long serialVersionUID = 6839564125865392586L;

    /**
     * Construct a create command of the given object type mapping with the
     * given object identifier.
     *
     * @param objType The type of object to which the create command is to be
     * mapped.
     *
     * @param ident The identifier of the object to be created.
     */
    public CreateCommand(ObjectType objType, String ident) {
        super(StandardCommandType.CREATE, objType, ident);
    }
}

