package com.ausregistry.jtoolkit2.se;

/**
 * Base class for protocol extension commands. Instances of this class are
 * responsible for building the part of the XML DOM tree common to all protocol
 * extension commands.
 */
public abstract class ProtocolExtensionCommand extends ObjectCommand {

    private static final long serialVersionUID = 4842063175523680995L;

    public ProtocolExtensionCommand(CommandType cmdType, ObjectType objType) {
        super(cmdType, objType);
    }

    public ProtocolExtensionCommand(CommandType cmdType, ObjectType objType,
            String ident) {
        super(cmdType, objType, ident);
    }

    public ProtocolExtensionCommand(CommandType cmdType, ObjectType objType,
            String ident, String attrName, String attrValue) {
        super(cmdType, objType, ident, attrName, attrValue);
    }

    @Override
    protected void initCmdElement() {
        command = xmlWriter.appendChild(xmlWriter.appendChild(
                xmlWriter.getRoot(), "extension"), "command",
                getExtension().getURI());

        command.setAttribute("xsi:schemaLocation",
                getExtension().getSchemaLocation());
    }

    protected abstract Extension getExtension();
}
