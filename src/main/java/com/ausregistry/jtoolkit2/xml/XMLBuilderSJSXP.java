package com.ausregistry.jtoolkit2.xml;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * A prefixless XML document builder implementation.  That is, a default
 * namespace is always in effect on each element.  This is generally optimal
 * for EPP, since there are very few nodes in the tree at which the namespace
 * changes.  This is only published outside the scope of the package to support
 * representation of {@link org.w3c.dom.Node}s as an XML String.
 *
 *
 * Uses the debug, maintenance and user level loggers.
 */
public final class XMLBuilderSJSXP implements XMLBuilder {
    private static final int BUFFER_SIZE = 4096;
    private static XMLOutputFactory xmlOutputFactory;
    /// XML encoding.
    private static String enc;
    /// XML version.
    private static String vers;

    private ByteArrayOutputStream bufferStream;
    private XMLStreamWriter writer;
    private String pname;
    private NamespaceContext namespaceContext;
    private final Package classPackage;

    static {
        xmlOutputFactory = XMLOutputFactory.newInstance();
        enc = "UTF-8";
        vers = "1.0";
    }

    public XMLBuilderSJSXP() {
        this(vers, enc, false);
    }

    XMLBuilderSJSXP(String version, String encoding, boolean standalone) {
        vers = version;
        enc = encoding;

        try {
            writer = xmlOutputFactory.createXMLStreamWriter(bufferStream,
                    encoding);
        } catch (XMLStreamException xse) {
            Logger.getLogger(pname + ".maint").severe(xse.getMessage());
            Logger.getLogger(pname + ".user").severe(
                    ErrorPkg.getMessage("xml.writer.config.fail.msg"));
        }
    }

    static void setVersion(String version) {
        vers = version;
    }

    static void setEncoding(String encoding) {
        enc = encoding;
    }

    {
        classPackage = getClass().getPackage();
        pname = classPackage.getName();
        namespaceContext = new NamespaceContextImpl();
        bufferStream = new ByteArrayOutputStream(BUFFER_SIZE);
    }

    @Override
    public String toXML(Element root, XmlOutputConfig xmlOutputConfig) {
        return toXML(root, enc, vers, xmlOutputConfig);
    }

    public String toXML(Element root, String encoding, String version, XmlOutputConfig xmlOutputConfig) {
        try {
            writer.writeStartDocument(encoding, version);
            String toolkitVersion = getToolkitVersion();
            if (!toolkitVersion.isEmpty()) {
                writer.writeComment(toolkitVersion);
            }
            dfs(root, xmlOutputConfig);
            writer.flush();
        } catch (XMLStreamException xse) {
            Logger.getLogger(pname + ".maint").severe(xse.getMessage());
            xse.printStackTrace();
            Logger.getLogger(pname + ".user").severe(
                    ErrorPkg.getMessage("xml.writer.process.fail.msg"));
        }
        return bufferStream.toString();
    }

    public String partialToXML(Node topNode, XmlOutputConfig xmlOutputConfig) {
        try {
            writer.setNamespaceContext(namespaceContext);
            dfs((Element) topNode, (Element) topNode, xmlOutputConfig);
            writer.flush();
        } catch (XMLStreamException xse) {
            Logger.getLogger(pname + ".maint").severe(xse.getMessage());
            xse.printStackTrace();
            Logger.getLogger(pname + ".user").severe(
                    ErrorPkg.getMessage("xml.writer.process.fail.msg"));
        }

        return bufferStream.toString();
    }

    private void dfs(Element e, XmlOutputConfig xmlOutputConfig) throws XMLStreamException {
        writer.setNamespaceContext(namespaceContext);
        dfs(e, null, xmlOutputConfig);
    }

    private void dfs(Element e, Element parent, XmlOutputConfig xmlOutputConfig) throws XMLStreamException {
        String uri = e.getNamespaceURI();
        String prefix = xmlOutputConfig.needsPrefixNamespace() ? namespaceContext.getPrefix(uri) : e.lookupPrefix(uri);

        String localName = e.getLocalName();
        if (localName == null) {
            localName = e.getNodeName();
        }
        Logger.getLogger(pname + ".debug").finer("Writing element " + localName);

        if (prefix == null) {
            writer.setDefaultNamespace(uri);
        } else {
            writer.setPrefix(prefix, uri);
        }
        if (e.hasChildNodes()) {
            if (prefix == null) {
                writer.writeStartElement(uri, localName);
            } else {
                writer.writeStartElement(prefix, localName, uri);
            }
        } else {
            if (prefix == null) {
                writer.writeEmptyElement(uri, localName);
            } else {
                writer.writeEmptyElement(prefix, localName, uri);
            }
        }
        if (parent == null
                || (uri != null && !parent.getNamespaceURI().equals(uri))) {
            Logger.getLogger(pname + ".debug").finer(
                    "Writing default namespace for element " + e.getLocalName());

            if (prefix == null) {
                writer.writeDefaultNamespace(uri);
            } else if (!e.hasAttribute("xmlns:" + prefix)) {
                writer.writeNamespace(prefix, uri);
            }
        }

        if (e.hasAttributes()) {
            NamedNodeMap attributes = e.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                Node attr = attributes.item(i);
                String name = attr.getNodeName();
                String value = attr.getNodeValue();
                writer.writeAttribute(name, value);
            }
        }

        if (e.hasChildNodes()) {
            NodeList children = e.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);

                if (child instanceof Text) {
                    writer.writeCharacters(child.getNodeValue());
                }

                if (child instanceof Element) {
                    dfs((Element) child, e, xmlOutputConfig);
                }
            }
        }

        if (e.hasChildNodes()) {
            writer.writeEndElement();
        }
    }

    private String getToolkitVersion() {
        String specificationTitle = classPackage.getSpecificationTitle();
        if (specificationTitle != null) {
            return specificationTitle + " v" + classPackage.getSpecificationVersion();
        } else {
            return "";
        }
    }

}
