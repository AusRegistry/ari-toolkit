package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Represents a treatyOrStatute's protection from the Trademark Clearing House extension,
 * represented by the "protection" element of type "protectionType" in the "urn:ietf:params:xml:ns:mark-1.0" namespace,
 * defined in the "mark-1.0.xsd" schema.
 *
 */
public class TmchProtection {
    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private static final String RULING_EXPR = "@ruling";
    private static final String CC_EXPR = "mark:cc/text()";
    private static final String REGION_EXPR = "mark:region/text()";

    private String cc;

    private String region;

    private String ruling;

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

    public String getRuling() {
        return ruling;
    }

    public void setRuling(String ruling) {
        this.ruling = ruling;
    }

    public void fromXML(XMLDocument xmlDocument) {
        try {
            ruling = xmlDocument.getNodeValue(RULING_EXPR);
            region = xmlDocument.getNodeValue(REGION_EXPR);
            cc = xmlDocument.getNodeValue(CC_EXPR);

        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }
    }
}
