package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;

/**
 * Standard and extension EPP command service elements are modelled by
 * subclasses of the Command class.  All such classes provide the means to
 * serialize their data to XML format as a valid EPP command, as well as
 * constructors sufficiently flexible to create any valid EPP command of that
 * type, and a method to query the type of command represented by an instance
 * of the class.
 */
public abstract class Command extends SendSE {

    private static final long serialVersionUID = 1683406928215004832L;

    private static final int APPEND_EXTENSION_STATUS_SUCCESS = 0;
    private static final int APPEND_EXTENSION_STATUS_FAILED = 1;
    private static final int APPEND_EXTENSION_STATUS_FAILED_NULL_COMMAND_EXTENSION = 2;

    protected org.w3c.dom.Element cmdElement;
    protected org.w3c.dom.Element command;
    protected org.w3c.dom.Element extension;

    private final CommandType cmdType;

    /**
     * @throws IllegalArgumentException if {@code commandType} is {@code null}.
     */
    public Command(final CommandType commandType) {
        if (commandType == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.command.type.missing"));
        }

        cmdType = commandType;
        initCmdElement();
        cmdElement = xmlWriter.appendChild(command, cmdType.getCommandName());
    }

    public org.w3c.dom.Element getExtensionElement() {
        return extension;
    }

    protected void initCmdElement() {
        command = xmlWriter.appendChild(xmlWriter.getRoot(), "command");
    }

    public CommandType getCommandType() {
        return cmdType;
    }

    /**
     * Serialize the EPP command service element to XML.
     *
     * @throws org.xml.sax.SAXException The XML representation of the command
     * failed schema validation.  Further attempts to serialize this command
     * will also fail.
     */
    protected String toXMLImpl(XmlOutputConfig xmlOutputConfig) throws org.xml.sax.SAXException {
        xmlWriter.appendChild(command, "clTRID").setTextContent(CLTRID.nextVal());
        return xmlWriter.toXML(xmlOutputConfig);
    }

    public int appendExtension(final CommandExtension ce) {
        int result = APPEND_EXTENSION_STATUS_FAILED;
        if (ce == null) {
            result = APPEND_EXTENSION_STATUS_FAILED_NULL_COMMAND_EXTENSION;
        } else {
            try {
                if (extension == null) {
                    extension = xmlWriter.appendChild(command, "extension");
                }
                ce.addToCommand(this);
                result = APPEND_EXTENSION_STATUS_SUCCESS;
            } catch (final Exception e) {
                userLogger.warning(e.getMessage());
                userLogger.warning(ErrorPkg.getMessage("Command.appendExtension.0"));
            }
        }
        return result;
    }

    public XMLWriter getXmlWriter() {
        return xmlWriter;
    }

}
