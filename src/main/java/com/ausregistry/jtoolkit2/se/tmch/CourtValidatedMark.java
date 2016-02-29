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
 * Represents a court from the Trademark Clearing House extension,
 * represented by the "court" element of type "courtType"
 * in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class CourtValidatedMark extends AbstractMark {

    private static final String REGION_NODE_LOCAL_NAME = "region";
    private static final String REFNUM_EXPR = "mark:refNum/text()";
    private static final String PRODATE_EXPR = "mark:proDate/text()";
    private static final String CC_EXPR = "mark:cc/text()";
    private static final String COURTNAME_EXPR = "mark:courtName/text()";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String refNum;

    private Date proDate;

    private String cc;

    private List<String> regions = new ArrayList<String>();

    private String courtName;

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> region) {
        this.regions = region;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    @Override
    public void fromXML(XMLDocument xmlDocument) {
        super.fromXML(xmlDocument);
        try {
            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);

                if (REGION_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    regions.add(item.getTextContent());
                }
            }
            refNum = xmlDocument.getNodeValue(REFNUM_EXPR);
            cc = xmlDocument.getNodeValue(CC_EXPR);
            proDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(PRODATE_EXPR)).getTime();
            courtName = xmlDocument.getNodeValue(COURTNAME_EXPR);

        } catch (Exception e) {
            maintLogger.warning(e.getMessage());
        }
    }
}
