package com.ausregistry.jtoolkit2.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The purpose of an XMLDocument is to provide a simple xpath evaluation
 * interface for extracting node values from an XML document.  Where the names
 * of elements are known in advance, non DOM-specific methods should be used in
 * order to reduce the dependency of client classes on the DOM interface.
 */
public class XMLDocument {
    private static XPathFactory xpathFactory;
    private static NamespaceContext nsCtx;

    private XPath xpath;
    private final Element root;
    private String sourceXMLString;

    static {
        xpathFactory = XPathFactory.newInstance();
        nsCtx = new NamespaceContextImpl();
    }

    /**
     * Create an XMLDocument rooted at the given element.
     *
     * @param root The root element of the XML document.
     */
    public XMLDocument(Element root) {
        this.root = root;
        xpath = XMLDocument.newXPath();
        xpath.setNamespaceContext(nsCtx);
    }

    XMLDocument(Element root, final String sourceXMLStringArg) {
        this.root = root;
        this.sourceXMLString = sourceXMLStringArg;
        xpath = XMLDocument.newXPath();
        xpath.setNamespaceContext(nsCtx);
    }

    private static XPath newXPath() {
        return xpathFactory.newXPath();
    }

    /**
     * Returns the XML used to produce the document.
     *
     * @return the XML
     */
    public String getSourceXMLString() {
        return sourceXMLString;
    }

    /**
     * Use an XMLWriter to serialize to XML form the DOM tree associated with
     * this XMLDocument.
     *
     * @return the string representation the document
     */
    @Override
    public String toString() {
        XMLWriter writer = XMLWriter.newInstance();
        writer.setRoot(root);
        try {
            return writer.toXML(XmlOutputConfig.defaultConfig());
        } catch (SAXException saxe) {
            return "";
        }
    }

    /**
     * Get the number of nodes returned by the given xpath expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The number of nodes.
     * @throws XPathExpressionException the XPath expression exception
     */
    public int getNodeCount(String query) throws XPathExpressionException {
        String result = xpath.evaluate(query, root);

        return Integer.parseInt(result);
    }

    /**
     * Get as a String the text content of a node identified by the given XPath
     * expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The text content of the identified node.
     * @throws XPathExpressionException the XPath expression exception
     */
    public String getNodeValue(String query) throws XPathExpressionException {
        return getNodeValue(query, root);
    }

    private String getNodeValue(String query, Node qRoot) throws XPathExpressionException {

        String countStr = xpath.evaluate("count(" + query + ")", qRoot);
        if (countStr.length() == 0 || Integer.valueOf(countStr) == 0) {
            return null;
        }

        return xpath.evaluate(query, qRoot);
    }

    /**
     * Get as a String the name of the node identified by the given XPath
     * expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The name of the identified node.
     * @throws XPathExpressionException the XPath expression exception
     */
    public String getNodeName(String query) throws XPathExpressionException {
        return getNodeName(query, root);
    }

    private String getNodeName(String query, Node qRoot) throws XPathExpressionException {

        Node node = (Node) xpath.evaluate(query, qRoot, XPathConstants.NODE);
        if (node != null) {
            return node.getLocalName();
        }
        return null;
    }

    /**
     * Get the names of all the nodes which are children of the node identified
     * by the given XPath expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The names of all the children nodes.
     * @throws XPathExpressionException the XPath expression exception
     */
    public String[] getChildNames(String query) throws XPathExpressionException {

        return getChildNames(query, root);
    }

    private String[] getChildNames(String query, Node qRoot) throws XPathExpressionException {

        String[] names;

        NodeList nodes = getElements(query + "/*", qRoot);
        if (nodes == null) {
            return null;
        }

        names = new String[nodes.getLength()];
        for (int i = 0; i < names.length; i++) {
            names[i] = nodes.item(i).getLocalName();
        }

        return names;
    }

    /**
     * Get the text content of each node identified by the given XPath
     * expression.
     *
     * @param query The XPath expression to evaluate.
     * @return An array of each identified node's text content.
     * @throws XPathExpressionException the XPath expression exception
     */
    public String[] getNodeValues(String query) throws XPathExpressionException {
        return getNodeValues(query, root);
    }

    private String[] getNodeValues(String query, Node qRoot) throws XPathExpressionException {

        String[] result;

        NodeList list = getElements(query, qRoot);
        if (list == null) {
            return null;
        }
        result = new String[list.getLength()];

        for (int i = 0; i < list.getLength(); i++) {
            result[i] = (list.item(i)).getTextContent();
        }

        return result;
    }

    /**
     * Get the node set identified by the given XPath expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The set of nodes identified by the given expression.
     * @throws XPathExpressionException the XPath expression exception
     */
    public NodeList getElements(String query) throws XPathExpressionException {
        return getElements(query, root);
    }

    private NodeList getElements(String query, Node qRoot) throws XPathExpressionException {

        NodeList nodes = (NodeList) xpath.evaluate(query, qRoot, XPathConstants.NODESET);

        if (nodes != null && nodes.getLength() > 0) {
            return nodes;
        } else {
            return null;
        }
    }

    /**
     * Get the Node identified by the given XPath expression.
     *
     * @param query The XPath expression to evaluate.
     * @return The identified Node.
     * @throws XPathExpressionException the XPath expression exception
     */
    public Node getElement(String query) throws XPathExpressionException {
        return (Node) xpath.evaluate(query, root, XPathConstants.NODE);
    }
}
