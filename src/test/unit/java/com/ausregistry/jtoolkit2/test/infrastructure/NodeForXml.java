package com.ausregistry.jtoolkit2.test.infrastructure;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import org.mockito.ArgumentMatcher;
import org.w3c.dom.Node;

public class NodeForXml extends ArgumentMatcher<Node> {

    private final String xmlString;

    public NodeForXml(String xmlString) {
        this.xmlString = xmlString;
    }

    public boolean matches(Object actualObject) {
        if (!(actualObject instanceof Node)) {
            return false;
        }

        Node actualNode = (Node) actualObject;

        String nodeTextContent = nodeToString(actualNode);
        return xmlString.replaceAll("\\s", "").equals(nodeTextContent.replaceAll("\\s", ""));
    }

    private String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            te.printStackTrace();
        }
        return sw.toString();
    }
}
