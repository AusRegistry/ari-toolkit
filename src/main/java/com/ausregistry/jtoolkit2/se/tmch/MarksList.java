package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a trademark from the Trademark Clearing House extension, represented by the "mark" element
 * of type "markType" in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class MarksList {

    private static final String TRADEMARK_NODE_LOCAL_NAME = "trademark";
    private static final String TREATY_OR_STATUTE_NODE_LOCAL_NAME = "treatyOrStatute";
    private static final String COURT_NODE_LOCAL_NAME = "court";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private List<AbstractMark> marks;

    public void fromXML(XMLDocument xmlDocument) {
        try {
            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            marks = new ArrayList<AbstractMark>(childNodes.getLength());
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                AbstractMark abstractMark;
                if (TRADEMARK_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    abstractMark = new Trademark();
                } else if (TREATY_OR_STATUTE_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    abstractMark = new TreatyOrStatute();
                } else if (COURT_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    abstractMark = new CourtValidatedMark();
                } else {
                    continue;
                }
                abstractMark.fromXML(new XMLDocument((Element) item));
                marks.add(abstractMark);
            }
        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }
    }

    public List<? extends AbstractMark> getMarks() {
        return marks;
    }

    public void setMarks(List<AbstractMark> marks) {
        this.marks = marks;
    }
}
