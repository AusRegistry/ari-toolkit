package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Superclass of all command classes which implement an object-mapped EPP
 * command such as create, delete, update, transfer, info and check.
 * Non-abstract subclasses must specify the command and object type, and not
 * expose assignment of those to the user.
 */
public abstract class ObjectCommand extends Command {
    private static final long serialVersionUID = 6432275322668381115L;
    protected org.w3c.dom.Element objElement;
    private ObjectType objType;

    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which take multiple object identifiers as parameters.
     *
     * @throws IllegalArgumentException if {@code objectType} or {@code idents} are {@code null}, or if
     * {@code idents} is empty.
     */
    public ObjectCommand(CommandType commandType, ObjectType objectType,
            String[] idents) {

        super(commandType);

        if (objectType == null || idents == null || idents.length == 0) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.object.missing_arg"));
        }

        objType = objectType;

        commonInit();

        xmlWriter.appendChildren(objElement, objectType.getIdentType(), idents);
    }

    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which operate on a single object.
     *
     * @throws IllegalArgumentException if {@code objectType} or {@code ident} are {@code null}.
     */
    public ObjectCommand(CommandType commandType, ObjectType objectType,
            String ident) {

        super(commandType);

        if (objectType == null || ident == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.object.missing_arg"));
        }

        objType = objectType;

        commonInit();

        xmlWriter.appendChild(
                objElement, objType.getIdentType()).setTextContent(ident);
    }

    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which operates on objects related to user.
     *
     * @throws IllegalArgumentException if {@code objectType} is {@code null}.
     */
    public ObjectCommand(CommandType commandType, ObjectType objectType) {

        super(commandType);

        if (objectType == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.object.missing_arg"));
        }

        objType = objectType;

        commonInit();
    }

    /**
     * Construct the DOM tree component common to all object-mapped commands
     * which operate on a single object, and assign it the attribute name and value.
     *
     * @throws IllegalArgumentException if {@code objectType} or {@code ident} are {@code null}.
     */
    public ObjectCommand(CommandType commandType, ObjectType objectType,
            String ident, String attrName, String attrValue) {

        super(commandType);

        if (objectType == null || ident == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.object.missing_arg"));
        }

        objType = objectType;

        commonInit();

        xmlWriter.appendChild(
                objElement, objType.getIdentType(), attrName, attrValue).setTextContent(ident);
    }

    private void commonInit() {
        objElement = xmlWriter.appendChild(
                cmdElement,
                getCommandType().getCommandName(),
                objType.getURI());

        objElement.setAttribute(
                "xsi:schemaLocation",
                objType.getSchemaLocation());
    }

    ObjectType getObjectType() {
        return objType;
    }
}

