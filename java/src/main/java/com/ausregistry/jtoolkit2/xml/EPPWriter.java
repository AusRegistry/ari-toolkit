package com.ausregistry.jtoolkit2.xml;

import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * An EPP-specific implementation of an XMLWriter. Instances of this class set the xml declaration and root element
 * appropriately for EPP service elements and also set the namespace attributes of the root element. This implementation
 * uses a DocumentBuilder to create the XML document which contains the EPP service element.
 *
 * Uses the debug and user level loggers.
 */
public class EPPWriter extends XMLWriter {
    private static final DocumentBuilderFactory dbFactory;
    private static final SAXParserFactory spFactory;

    static {
        dbFactory = DocumentBuilderFactory.newInstance();
        spFactory = SAXParserFactory.newInstance();
        spFactory.setNamespaceAware(true);
        spFactory.setValidating(false);
        EPPSchemaProvider.setValidating(true);
        spFactory.setSchema(EPPSchemaProvider.getSchema());
    }

    private Document doc;
    private Element eppElement;
    private final Logger debugLogger;
    private final String pname;

    {
        pname = getClass().getPackage().getName();
        debugLogger = Logger.getLogger(pname + ".debug");
        userLogger = Logger.getLogger(pname + ".user");
    }

    /**
     * Creates an EPP service element lexical representation generator with default xml declaration and root element
     * attributes.
     */
    public EPPWriter() {
        this("1.0", "UTF-8", false, "urn:ietf:params:xml:ns:epp-1.0", "http://www.w3.org/2001/XMLSchema-instance",
                "urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd");
    }

    /**
     * Creates an EPP service element lexical representation generator with the specified xml declaration and root
     * element attributes.
     */
    protected EPPWriter(String version, String encoding, boolean standalone, String eppNamespace, String xsi,
            String eppSchemaLocation) {

        debugLogger.finest("enter");

        this.version = version;
        this.encoding = encoding;
        this.standalone = standalone;

        xml = null;
        try {
            DocumentBuilder docBuilder;
            docBuilder = dbFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            eppElement = doc.createElementNS(eppNamespace, "epp");
            eppElement.setAttribute("xmlns:xsi", xsi);
            eppElement.setAttribute("xsi:schemaLocation", eppSchemaLocation);
            doc.appendChild(eppElement);
        } catch (javax.xml.parsers.ParserConfigurationException pce) {
            userLogger.severe(pce.getMessage());
            userLogger.severe(ErrorPkg.getMessage("EPPWriter.init.0"));
        } finally {
            debugLogger.finest("exit");
        }
    }

    /**
     * Get the <code>epp</code> element, which is the root of the XML tree upon which the lexical representation will be
     * based.
     */
    @Override
    public Element getRoot() {
        return eppElement;
    }

    @Override
    protected final void setRoot(Element newRoot) {
        eppElement = newRoot;
    }

    @Override
    protected final XMLBuilder getXMLBuilder() {
        return new XMLBuilderSJSXP(version, encoding, standalone);
    }

    @Override
    protected final SAXParser newSAXParser() throws SAXException, ParserConfigurationException {

        return spFactory.newSAXParser();
    }

    @Override
    protected final boolean isParserValidating() {
        return (spFactory.getSchema() != null);
    }

    @Override
    protected final Element createElement(String uri, String name) {
        return doc.createElementNS(uri, name);
    }
}
