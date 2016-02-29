package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Represents a treatyOrStatute's protection from the Trademark Clearing House extension,
 * represented by the "protection" element of type "protectionType" in the "urn:ietf:params:xml:ns:mark-1.0" namespace,
 * defined in the "mark-1.0.xsd" schema.
 *
 */
public class TreatyOrStatuteProtection {

    private static final String RULING_EXPR = "mark:ruling";
    private static final String CC_EXPR = "mark:cc/text()";
    private static final String REGION_EXPR = "mark:region/text()";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String cc;

    private String region;

    private List<String> rulings = new ArrayList<String>();

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getRulings() {
        return rulings;
    }

    public void addRuling(String ruling) {
        rulings.add(ruling);
    }

    public void fromXML(XMLDocument xmlDocument) {
        try {
            String[] rulingArray = xmlDocument.getNodeValues(RULING_EXPR);
            if (rulingArray != null) {
                for (int i = 0; i < rulingArray.length; i++) {
                    rulings.add(rulingArray[i]);
                }
            }
            region = xmlDocument.getNodeValue(REGION_EXPR);
            cc = xmlDocument.getNodeValue(CC_EXPR);

        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }
    }
}
