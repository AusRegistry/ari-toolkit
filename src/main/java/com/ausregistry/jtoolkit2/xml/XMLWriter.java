package com.ausregistry.jtoolkit2.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * An XMLWriter provides a simple interface to build a DOM tree and serialize it to XML format. XMLWriters are namespace
 * aware and may be configured to validate the generated XML.
 *
 * Uses the maintenance, support and user level loggers.
 */
public abstract class XMLWriter {
    private static final String PACKAGE_NAME = XMLWriter.class.getPackage().getName();

    /*
     * This lock is a workaround to an implementation flaw in com.sun.xml.internal.stream.XMLOutputFactoryImpl which
     * results in the output stream assigned to a XMLStreamWriter being over-written by another thread. This is resolved
     * in Java 7.
     */
    private static final Object WORKAROUND_LOCK = new Object();

    protected String xml;
    protected String version;
    protected String encoding;
    protected boolean standalone;
    protected Logger supportLogger = Logger.getLogger(PACKAGE_NAME + ".support");
    protected Logger userLogger = Logger.getLogger(PACKAGE_NAME + ".user");
    protected Logger maintLogger = Logger.getLogger(PACKAGE_NAME + ".maint");

    /**
     * Get an instance of the default implementation of XMLWriter.
     */
    public static XMLWriter newInstance() {
        return new EPPWriter();
    }
    /**
     * Get the root element of the DOM tree associated with this writer.
     *
     * @return The root Element of the DOM tree.
     */
    public abstract Element getRoot();

    protected abstract SAXParser newSAXParser() throws SAXException, ParserConfigurationException;

    protected abstract boolean isParserValidating();

    protected abstract XMLBuilder getXMLBuilder();

    /**
     * Set the root element of the DOM tree associated with this writer.
     *
     * @param newRoot The DOM Element to assign as the root.
     */
    protected abstract void setRoot(Element newRoot);

    protected abstract Element createElement(String uri, String name);

    /**
     * Append a new child element to the specified element. The new element will inherit the namespace of the parent.
     *
     * @param parent The element which will be the parent element of the new element.
     * @param name The name of the new element (tag).
     *
     * @return The new child element.
     */
    public Element appendChild(Element parent, String name) {
        return appendChild(parent, name, parent.getNamespaceURI());
    }

    /**
     * Append a new child element to the specified element. The new element will be in the namespace identified by the
     * given URI.
     *
     * @param parent The element which will be the parent element of the new element.
     * @param name The name of the new element (tag).
     *
     * @param uri Namespace URI for the new child element.
     */
    public Element appendChild(Element parent, String name, String uri) {
        Element newElement = createElement(uri, name);
        parent.appendChild(newElement);

        return newElement;
    }

    /**
     * Append a DOM Element with the given name and attribute to the specified parent Element. The new
     * {@link org.w3c.dom.Element} will be in the namespace of its parent.
     *
     * @param parent The parent of the new Element.
     * @param name The name of the new Element.
     * @param attrName The name of the attribute of the new Element.
     * @param attrValue The value of the attribute named {@code attrName}.
     *
     * @return The new Element.
     */
    public Element appendChild(Element parent, String name, String attrName, String attrValue) {
        Element newElement = appendChild(parent, name);
        newElement.setAttribute(attrName, attrValue);

        return newElement;
    }

    /**
     * Append a DOM Element with the given name, text content value and attribute to the specified parent Element. The
     * new {@link org.w3c.dom.Element} will be in the namespace of its parent.
     *
     * @param parent The parent of the new Element.
     * @param name The name of the new Element.
     * @param value The text content to assign to the text node child of the new Element.
     * @param attrName The name of the attribute of the new Element.
     * @param attrValue The value of the attribute named {@code attrName}.
     *
     * @return The new Element.
     */
    public Element appendChild(Element parent, String name, String value, String attrName, String attrValue) {
        Element newElement = appendChild(parent, name, attrName, attrValue);
        newElement.setTextContent(value);

        return newElement;
    }

    /**
     * Append a DOM Element with the given name and attributes to the specified parent Element. The two arrays
     * {@code attrNames} and {@code attrValues} must have the same length and be ordered such that the nth element of
     * {@code attrValues} corresponds to the attribute with the name given by the nth element of {@code attrNames}. The
     * new {@link org.w3c.dom.Element} will be in the namespace of its parent.
     *
     * @param parent The parent of the new Element.
     * @param name The name of the new Element.
     * @param attrNames The names of the attributes of the new Element.
     * @param attrValues The values of each of the attributes {@code attrName}.
     *
     * @return The new Element.
     */
    public Element appendChild(Element parent, String name, String[] attrNames, String[] attrValues) {
        return appendChild(parent, name, parent.getNamespaceURI(), null, attrNames, attrValues);
    }

    /**
     * Append a DOM Element with the given name and attributes to the specified parent Element. The new element is in
     * the namespace identified the the given URI. The two arrays {@code attrNames} and {@code attrValues} must have the
     * same length and be ordered such that the nth element of {@code attrValues} corresponds to the attribute with the
     * name given by the nth element of {@code attrNames}.
     *
     * @param parent The parent of the new Element.
     * @param name The name of the new Element.
     * @param uri The URI of the new Element's namespace.
     * @param attrNames The names of the attributes of the new Element.
     * @param attrValues The values of each of the attributes {@code attrName}.
     *
     * @return The new Element.
     */
    public Element appendChild(Element parent, String name, String uri, String value, String[] attrNames,
            String[] attrValues) {
        Element newElement = appendChild(parent, name, uri);

        for (int i = 0; i < attrNames.length && i < attrValues.length; i++) {
            newElement.setAttribute(attrNames[i], attrValues[i]);
        }

        /*
         * if (uri != null && uri != parent.getNamespaceURI()) { newElement.setAttribute("xmlns", uri); }
         */

        if (value != null) {
            newElement.setTextContent(value);
        }

        return newElement;
    }

    /**
     * Append multiple DOM Elements to the given Element, each having a specified text content. The text content of each
     * new element is defined by the {@code values} array. The new {@link org.w3c.dom.Element} will be in the namespace
     * of its parent.
     *
     * @param parent The parent of the new Elements.
     * @param name The name of the new Elements.
     */
    public void appendChildren(Element parent, String name, String[] values) {
        for (String value : values) {
            Element child = appendChild(parent, name);
            child.setTextContent(value);
        }
    }

    /**
     * Serialize to XML format the DOM tree currently associated with this XMLWriter. If validation is enabled, then an
     * alternative SAX error handler may be provided as described in
     *
     * @return the string
     * @throws SAXException the SAX exception
     * {@link com.ausregistry.jtoolkit2.xml.HandlerFactory}. That handler will receive notification of
     * parsing/validation failures.
     */
    public String toXML(XmlOutputConfig xmlOutputConfig) throws SAXException {
        if (xml != null) {
            return xml;
        }

        if (!isParserValidating()) {
            generateXmlLocked(xmlOutputConfig);

            supportLogger.info(xml);
            return xml;
        }

        xml = getXMLBuilder().toXML(getRoot(), xmlOutputConfig);

        try {
            validate();
        } catch (SAXException saxe) {
            generateXmlLocked(xmlOutputConfig);

            try {
                validate();
            } catch (SAXException se) {
                userLogger.warning(xml);
                throw se;
            } catch (Exception e) {
            }
        } catch (ParserConfigurationException pce) {
            userLogger.warning(pce.getMessage());
            userLogger.warning(ErrorPkg.getMessage("xml.parser.operation.unsupported"));

            generateXmlLocked(xmlOutputConfig);
        } catch (IOException ioe) {
            userLogger.warning(ioe.getMessage());
            maintLogger.warning(ioe.getMessage());

            generateXmlLocked(xmlOutputConfig);
        }

        supportLogger.info(xml);
        return xml;
    }

    /**
     * See the description of the WORKAROUND_LOCK class member for the rationale behind this method.
     */
    private void generateXmlLocked(XmlOutputConfig xmlOutputConfig) {
        synchronized (XMLWriter.WORKAROUND_LOCK) {
            xml = getXMLBuilder().toXML(getRoot(), xmlOutputConfig);
        }
    }

    private void validate() throws IOException, ParserConfigurationException, SAXException {

        InputStream in = new ByteArrayInputStream(xml.getBytes());
        DefaultHandler handler = HandlerFactory.newInstance();
        SAXParser saxParser = newSAXParser();
        saxParser.parse(in, handler);
    }
}
