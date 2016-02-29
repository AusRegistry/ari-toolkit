package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;

import static com.ausregistry.jtoolkit2.se.ExtendedObjectType.SIGNED_MARK_DATA;
import static com.ausregistry.jtoolkit2.se.ExtendedObjectType.MARK;

/**
 * Represents a signedMarkData from the Trademark Clearing House extension, represented by the "signedMark" element
 * of type "signedMarkType" in the "urn:ietf:params:xml:ns:signedMark-1.0" namespace,
 * defined in the "signedMark-1.0.xsd" schema.
 *
 */
public class SignedMarkData {

    public static final String BASE_EXPR = "/" + SIGNED_MARK_DATA.getName() + ":signedMark";
    private static final String SMD_ID_EXPR = BASE_EXPR + "/" + SIGNED_MARK_DATA.getName() + ":id/text()";
    private static final String SMD_NOT_BEFORE_EXPR =
            BASE_EXPR + "/" + SIGNED_MARK_DATA.getName() + ":notBefore/text()";
    private static final String SMD_NOT_AFTER_EXPR = BASE_EXPR + "/" + SIGNED_MARK_DATA.getName() + ":notAfter/text()";
    private static final String ISSUER_INFO_EXPR = BASE_EXPR + "/" + SIGNED_MARK_DATA.getName() + ":issuerInfo";
    private static final String MARK_EXPR = BASE_EXPR + "/" + MARK.getName() + ":mark";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String id;

    private SmdIssuerInfo smdIssuerInfo;

    private Date notBefore;

    private Date notAfter;

    private MarksList marksList;

    public void fromXML(final XMLDocument xmlDocument) {
        try {
            id = xmlDocument.getNodeValue(SMD_ID_EXPR);
            Element issuerInfoElement = (Element) xmlDocument.getElement(ISSUER_INFO_EXPR);
            if (issuerInfoElement != null) {
                smdIssuerInfo = new SmdIssuerInfo();
                smdIssuerInfo.fromXML(new XMLDocument(issuerInfoElement));
            }
            notBefore = DatatypeConverter.parseDate(xmlDocument.getNodeValue(SMD_NOT_BEFORE_EXPR)).getTime();
            notAfter = DatatypeConverter.parseDate(xmlDocument.getNodeValue(SMD_NOT_AFTER_EXPR)).getTime();
            Element markElement = (Element) xmlDocument.getElement(MARK_EXPR);
            if (markElement != null) {
                marksList = new MarksList();
                marksList.fromXML(new XMLDocument(markElement));
            }
        } catch (Exception e) {
            maintLogger.warning(e.getMessage());
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SmdIssuerInfo getSmdIssuerInfo() {
        return smdIssuerInfo;
    }

    public void setSmdIssuerInfo(SmdIssuerInfo smdIssuerInfo) {
        this.smdIssuerInfo = smdIssuerInfo;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public MarksList getMarksList() {
        return marksList;
    }

    public void setMarksList(MarksList marksList) {
        this.marksList = marksList;
    }

    public boolean isValid(final Date currentDate) {
        return !currentDate.before(notBefore)
                && !currentDate.after(notAfter);
    }
}
