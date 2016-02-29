package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a trademark from the Trademark Clearing House extension, represented by the "trademark" element
 * of type "trademarkType" in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class Trademark extends AbstractMark {

    private static final String JURISDICTION_EXPR = "mark:jurisdiction/text()";
    private static final String CLASS_NODE_LOCAL_NAME = "class";
    private static final String AP_ID_EXPR = "mark:apId/text()";
    private static final String AP_DATE_EXPR = "mark:apDate/text()";
    private static final String REG_NUM_EXPR = "mark:regNum/text()";
    private static final String REG_DATE_EXPR = "mark:regDate/text()";
    private static final String EX_DATE_EXPR = "mark:exDate/text()";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String jurisdiction;

    private List<String> classes = new ArrayList<String>();

    private String apId;

    private Date apDate;

    private String regNum;

    private Date regDate;

    private Date exDate;

    @Override
    public void fromXML(XMLDocument xmlDocument) {
        super.fromXML(xmlDocument);
        try {
            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (CLASS_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    classes.add(item.getTextContent());
                }
            }
            jurisdiction = xmlDocument.getNodeValue(JURISDICTION_EXPR);
            apId = xmlDocument.getNodeValue(AP_ID_EXPR);
            regDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(REG_DATE_EXPR)).getTime();
            if (xmlDocument.getNodeValue(AP_DATE_EXPR) != null) {
                apDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(AP_DATE_EXPR)).getTime();
            }
            regNum = xmlDocument.getNodeValue(REG_NUM_EXPR);
            exDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(EX_DATE_EXPR)).getTime();

        } catch (Exception e) {
            maintLogger.warning(e.getMessage());
        }

    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public Date getApDate() {
        return apDate;
    }

    public void setApDate(Date apDate) {
        this.apDate = apDate;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getExDate() {
        return exDate;
    }

    public void setExDate(Date exDate) {
        this.exDate = exDate;
    }
}
