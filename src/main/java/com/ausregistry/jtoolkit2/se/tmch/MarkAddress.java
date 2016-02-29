package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a holder's or contact's address from the Trademark Clearing House extension,
 * represented by the "address" element of type "addressType"
 * in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class MarkAddress {

    private static final String STREET_NODE_LOCAL_NAME = "street";
    private static final String CITY_EXPR = "mark:city/text()";
    private static final String SP_EXPR = "mark:sp/text()";
    private static final String PC_EXPR = "mark:pc/text()";
    private static final String CC_EXPR = "mark:cc/text()";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private List<String> streets = new ArrayList<String>();

    private String city;

    private String sp;

    private String pc;

    private String cc;

    public List<String> getStreets() {
        return streets;
    }

    public void setStreets(List<String> streets) {
        this.streets = streets;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void fromXML(XMLDocument xmlDocument) {

        try {
            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (STREET_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    streets.add(item.getTextContent());
                }
            }
            city = xmlDocument.getNodeValue(CITY_EXPR);
            sp = xmlDocument.getNodeValue(SP_EXPR);
            pc = xmlDocument.getNodeValue(PC_EXPR);
            cc = xmlDocument.getNodeValue(CC_EXPR);

        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }
    }
}
