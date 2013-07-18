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
 * Represents an abstract mark from the Trademark Clearing House extension,
 * represented by the "abstractMark" element of type "abstractMarkType" in the
 * "urn:ietf:params:xml:ns:mark-1.0" namespace,
 * defined in the "mark-1.0.xsd" schema.
 *
 */
public abstract class AbstractMark {

    private static final String HOLDER_EXPR = "holder";
    private static final String CONTACT_EXPR = "contact";
    private static final String LABEL_EXPR = "label";

    private Logger maintLogger =
            Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String id;

    private String markName;

    private List<MarkHolder> holders = new ArrayList<MarkHolder>();

    private List<MarkContact> contacts = new ArrayList<MarkContact>();

    private List<String> labels = new ArrayList<String>();

    private String goodsAndServices;


    public void fromXML(XMLDocument xmlDocument) {
        try {
            id = xmlDocument.getNodeValue("mark:id/text()");
            markName = xmlDocument.getNodeValue("mark:markName/text()");
            goodsAndServices = xmlDocument.getNodeValue("mark:goodsAndServices/text()");

            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (HOLDER_EXPR.equals(item.getLocalName())) {
                    MarkHolder markHolder = new MarkHolder();
                    markHolder.fromXML(new XMLDocument((Element) item));
                    holders.add(markHolder);
                } else if (CONTACT_EXPR.equals(item.getLocalName())) {
                    MarkContact markContact = new MarkContact();
                    markContact.fromXML(new XMLDocument((Element) item));
                    contacts.add(markContact);
                } else if (LABEL_EXPR.equals(item.getLocalName())) {
                    labels.add(item.getTextContent());
                }
            }
        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }

}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public List<MarkHolder> getHolders() {
        return holders;
    }

    public void setHolders(List<MarkHolder> holders) {
        this.holders = holders;
    }

    public List<MarkContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<MarkContact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getGoodsAndServices() {
        return goodsAndServices;
    }

    public void setGoodsAndServices(String goodsAndServices) {
        this.goodsAndServices = goodsAndServices;
    }
}
