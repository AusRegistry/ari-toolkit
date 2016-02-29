package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * This defines the operations or actions for sending XML to an EPP based server.  It defines the user level logger.
 */
abstract class SendSE implements Serializable {

    private static final long serialVersionUID = 8732674992510564553L;

    protected final transient XMLWriter xmlWriter;
    protected final transient Logger userLogger;

    private String xml;

    {
        final String pname = getClass().getPackage().getName();
        userLogger = Logger.getLogger(pname + ".user");
        xmlWriter = XMLWriter.newInstance();
    }

    protected SendSE() { }

    protected abstract String toXMLImpl() throws org.xml.sax.SAXException;

    /**
     * Serialize the EPP service element to XML.  This MUST be called prior
     * to attempting to use Java serialization on this object.  Failure to
     * do so will result in NullPointerException when toXML is called after
     * deserializing the object.
     *
     * @throws org.xml.sax.SAXException The XML representation of the command
     * failed schema validation.  Further attempts to serialize this command
     * will also fail.
     */
    public final String toXML() throws org.xml.sax.SAXException {
        if (xml == null) {
            xml = toXMLImpl();
        }

        return xml;
    }
}

