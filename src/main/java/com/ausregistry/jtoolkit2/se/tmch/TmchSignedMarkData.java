package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;

/**
 * Represents a signedMarkData from the Trademark Clearing House extension, represented by the "signedMark" element
 * of type "signedMarkType" in the "urn:ietf:params:xml:ns:signedMark-1.0" namespace,
 * defined in the "signedMark-1.0.xsd" schema.
 *
 */
public class TmchSignedMarkData {
    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    public static final String BASE_EXPR = "/smd:signedMark";
    private static final String SMD_ID_EXPR = BASE_EXPR + "/smd:id/text()";
    private static final String SMD_NOT_BEFORE_EXPR = BASE_EXPR + "/smd:notBefore/text()";
    private static final String SMD_NOT_AFTER_EXPR = BASE_EXPR + "/smd:notAfter/text()";
    private static final String ISSUER_INFO_EXPR = BASE_EXPR + "/smd:issuerInfo";
    private static final String MARK_EXPR = BASE_EXPR + "/mark:mark";

    private String id;

    private TmchSmdIssuerInfo tmchSmdIssuerInfo;

    private Date notBefore;

    private Date notAfter;

    private TmchMark tmchMark;

    public void fromXML(final XMLDocument xmlDocument) {
        try {
            id = xmlDocument.getNodeValue(SMD_ID_EXPR);
            Element issuerInfoElement = (Element) xmlDocument.getElement(ISSUER_INFO_EXPR);
            if (issuerInfoElement != null) {
                tmchSmdIssuerInfo = new TmchSmdIssuerInfo();
                tmchSmdIssuerInfo.fromXML(new XMLDocument(issuerInfoElement));
            }
            notBefore = DatatypeConverter.parseDate(xmlDocument.getNodeValue(SMD_NOT_BEFORE_EXPR)).getTime();
            notAfter = DatatypeConverter.parseDate(xmlDocument.getNodeValue(SMD_NOT_AFTER_EXPR)).getTime();
            Element markElement = (Element) xmlDocument.getElement(MARK_EXPR);
            if (markElement != null) {
                tmchMark = new TmchMark();
                tmchMark.fromXML(new XMLDocument(markElement));
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

    public TmchSmdIssuerInfo getTmchSmdIssuerInfo() {
        return tmchSmdIssuerInfo;
    }

    public void setTmchSmdIssuerInfo(TmchSmdIssuerInfo tmchSmdIssuerInfo) {
        this.tmchSmdIssuerInfo = tmchSmdIssuerInfo;
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

    public TmchMark getTmchMark() {
        return tmchMark;
    }

    public void setTmchMark(TmchMark tmchMark) {
        this.tmchMark = tmchMark;
    }

    public boolean isValid(final Date currentDate) {
        return !currentDate.before(notBefore)
                && !currentDate.after(notAfter);
    }
}
