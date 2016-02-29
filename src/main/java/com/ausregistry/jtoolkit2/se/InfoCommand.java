package com.ausregistry.jtoolkit2.se;


import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.xml.Attribute;

/**
 * Representation of the EPP info command, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped
 * and specify the object-specific identifier of the object to request
 * information about.
 *
 * @see com.ausregistry.jtoolkit2.se.InfoResponse
 */
public abstract class InfoCommand extends ObjectCommand {

    private static final long serialVersionUID = 4398391101973433044L;

    /**
     * Create an info command mapped to the specified object type to retrieve
     * information about the identified object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * retrieve information about.
     */
    public InfoCommand(ObjectType objType, String ident) {
        super(StandardCommandType.INFO, objType, ident);
    }

    /**
     * Create an info command mapped to the specified object type to retrieve
     * information about the related object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     */
    public InfoCommand(ObjectType objType) {
        super(StandardCommandType.INFO, objType);
    }

    public InfoCommand(StandardObjectType objType, String ident, Attribute attribute) {
        super(StandardCommandType.INFO, objType, ident, attribute.getType(), attribute.getValue());
    }

    /**
     * Create an info command mapped to the specified object type to retrieve
     * information about the identified object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * retrieve information about.
     *
     * @param pw The password of the object to retrieve information about.
     * This is used to retrieve complete information about an object when the
     * object is sponsored by another client.
     */
    public InfoCommand(ObjectType objType, String ident, String pw) {
        this(objType, ident);
        appendAuthInfo(pw);
    }

    public InfoCommand(StandardObjectType objType, String ident, String pw, Attribute attribute) {
        this(objType, ident, attribute);
        appendAuthInfo(pw);
    }

    /**
     * Create an info command mapped to the specified object type to retrieve
     * information about the identified object.
     *
     * @param objType The type of object to which the info command is to be
     * mapped.
     *
     * @param ident An object type-specific label identifying the object to
     * retrieve information about.
     *
     * @param roid The Repository Object Identifer of an object associated with
     * the object to be queried.
     *
     * @param pw The password of the object to retrieve information about.
     * This is used to retrieve complete information about an object when the
     * object is sponsored by another client.
     */
    public InfoCommand(ObjectType objType, String ident,
            String roid, String pw) {
        this(objType, ident);

        appendAuthInfoWithRoid(roid, pw);
    }

    public InfoCommand(StandardObjectType objType, String ident, String roid, String pw, Attribute attribute) {
        this(objType, ident, attribute);

        appendAuthInfoWithRoid(roid, pw);
    }


    private void appendAuthInfo(String pw) {
        if (pw == null) {
            return;
        }

        xmlWriter.appendChild(
                xmlWriter.appendChild(objElement, "authInfo"), "pw").setTextContent(pw);
    }

    private void appendAuthInfoWithRoid(String roid, String pw) {
        if (pw == null) {
            return;
        }

        if (roid == null || pw == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.info.missing_arg"));
        }

        xmlWriter.appendChild(
                xmlWriter.appendChild(objElement, "authInfo"),
                        "pw", new String[] {"roid"}, new String[] {roid}).setTextContent(pw);
    }
}


