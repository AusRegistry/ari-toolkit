package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a treatyOrStatute from the Trademark Clearing House extension,
 * represented by the "treatyOrStatute" element of type "treatyOrStatuteType"
 * in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class TreatyOrStatute extends AbstractMark {

    private static final String PROTECTION_NODE_LOCAL_NAME = "protection";
    private static final String REFNUM_EXPR = "mark:refNum/text()";
    private static final String PRODATE_EXPR = "mark:proDate/text()";
    private static final String TITLE_EXPR = "mark:title/text()";
    private static final String EXECDATE_EXPR = "mark:execDate/text()";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private List<TreatyOrStatuteProtection> treatyOrStatuteProtections = new ArrayList<TreatyOrStatuteProtection>();

    private String refNum;

    private Date proDate;

    private String title;

    private Date execDate;

    public List<TreatyOrStatuteProtection> getTreatyOrStatuteProtections() {
        return treatyOrStatuteProtections;
    }

    public void setTreatyOrStatuteProtections(List<TreatyOrStatuteProtection> treatyOrStatuteProtections) {
        this.treatyOrStatuteProtections = treatyOrStatuteProtections;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getExecDate() {
        return execDate;
    }

    public void setExecDate(Date execDate) {
        this.execDate = execDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDocument) {
        super.fromXML(xmlDocument);
        try {
            Node element = xmlDocument.getElement(".");
            NodeList childNodes = element.getChildNodes();
            TreatyOrStatuteProtection treatyOrStatuteProtection;
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);

                if (PROTECTION_NODE_LOCAL_NAME.equals(item.getLocalName())) {
                    treatyOrStatuteProtection = new TreatyOrStatuteProtection();
                    treatyOrStatuteProtection.fromXML(new XMLDocument((Element) item));
                    treatyOrStatuteProtections.add(treatyOrStatuteProtection);
                }
            }
            refNum = xmlDocument.getNodeValue(REFNUM_EXPR);
            title = xmlDocument.getNodeValue(TITLE_EXPR);
            proDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(PRODATE_EXPR)).getTime();
            execDate = DatatypeConverter.parseDate(xmlDocument.getNodeValue(EXECDATE_EXPR)).getTime();

        } catch (Exception e) {
            maintLogger.warning(e.getMessage());
        }

    }
}
