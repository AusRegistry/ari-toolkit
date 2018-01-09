package com.ausregistry.jtoolkit2.xml;

import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

final class XMLBuilderW3C implements XMLBuilder {
    private static final int DEFAULT_DOC_SIZE = 4096;
    private static final String SPACE = " ";

    private static final String DEFAULT_VERSION = "1.0";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_STANDALONE = false;

    private String version;
    private String encoding;
    private boolean standalone;

    private StringBuilder builder;
    private String pname;

    XMLBuilderW3C() {
        this(DEFAULT_VERSION, DEFAULT_ENCODING, DEFAULT_STANDALONE);
    }

    XMLBuilderW3C(String version, String encoding, boolean standalone) {
        pname = getClass().getPackage().getName();

        this.version = version;
        this.encoding = encoding;
        this.standalone = standalone;

        builder = new StringBuilder(DEFAULT_DOC_SIZE);
    }

    public String toXML(Element root, XmlOutputConfig xmlOutputConfig) {
        Logger.getLogger(pname + ".debug").finest("enter");
        writeDeclaration();
        dfs(root);

        Logger.getLogger(pname + ".debug").finest("exit");
        return new String(builder);
    }

    public void dfs(Element e) {
        Logger.getLogger(pname + ".debug").finest("enter");
        String name = e.getNodeName();

        write("<");
        write(name);

        String uri = e.getNamespaceURI();
        Node parent = e.getParentNode();
        Logger.getLogger(pname + ".debug").finer(uri);
        if (parent == null
                || (uri != null && !parent.getNamespaceURI().equals(uri))) {
            writeNS(uri);
        }

        if (e.hasAttributes()) {
            NamedNodeMap attributes = e.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                writeAttribute(attributes.item(i));
            }
        }

        if (e.hasChildNodes()) {
            write(">");

            NodeList children = e.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);

                if (child instanceof Text) {
                    write(child.getNodeValue());
                }

                if (child instanceof Element) {
                    dfs((Element) child);
                }
            }

            write("</");
            write(name);
            write(">");
        } else {
            write("/>");
        }

        Logger.getLogger(pname + ".debug").finest("exit");
    }

    private void writeDeclaration() {
        write("<?xml version=\"");
        write(version);
        write("\" encoding=\"");
        write(encoding);
        write("\" standalone=\"");
        write(standalone ? "yes\"" : "no\"");
        write("?>");

    }

    private void writeNS(String uri) {
        write(SPACE);
        write("xmlns=\"");
        write(uri);
        write("\"");
    }

    private void writeAttribute(Node attr) {
        write(SPACE);
        write(attr.getNodeName());
        write("=\"");
        write(attr.getNodeValue());
        write("\"");
    }

    private void write(String s) {
        builder.append(s);
    }
}

