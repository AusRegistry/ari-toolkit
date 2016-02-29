package com.ausregistry.jtoolkit2.se;

/**
 * Representation of the EPP check command, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifiers of the objects to check the
 * availability of.
 */
public abstract class CheckCommand extends ObjectCommand {

    private static final long serialVersionUID = -8818226184501184457L;

    /**
     * Create a check command mapped to the specified object type to check the
     * availability of the identified object.
     *
     * @param objType The object mapping to use.
     *
     * @param ident An object type-specific label identifying the object to
     * check the availability of.
     */
    public CheckCommand(ObjectType objType, String ident) {
        super(StandardCommandType.CHECK, objType, ident);
    }

    /**
     * Create a check command mapped to the specified object type to check the
     * availability of the identified object.
     *
     * @param objType The object mapping to use.
     *
     * @param idents An object type-specific array of labels identifying the
     * objects to check the availability of.
     */
    public CheckCommand(ObjectType objType, String[] idents) {
        super(StandardCommandType.CHECK, objType, idents);
    }
}

