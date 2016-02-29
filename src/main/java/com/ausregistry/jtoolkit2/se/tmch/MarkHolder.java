package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;

import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents a holder from the Trademark Clearing House extension,
 * represented by the "holder" element of type "holderType"
 * in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public class MarkHolder {

    private static final String ENTITLEMENT_EXPR = "@entitlement";
    private static final String ORG_EXPR = "mark:org/text()";
    private static final String NAME_EXPR = "mark:name/text()";
    private static final String VOICE_EXPR = "mark:voice/text()";
    private static final String VOICE_EXT_EXPR = "mark:voice/@x";
    private static final String FAX_EXPR = "mark:fax/text()";
    private static final String FAX_EXT_EXPR = "mark:fax/@x";
    private static final String ADDRESS_EXPR = "mark:addr";
    private static final String EMAIL_EXPR = "mark:email";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private MarkHolderEntitlement entitlement;

    private String name;

    private String org;

    private MarkAddress address;

    private String voice;

    private String voiceExt;

    private String fax;

    private String faxExt;

    private String email;

    public MarkHolderEntitlement getEntitlement() {
        return entitlement;
    }

    public void fromXML(XMLDocument xmlDocument) {
        try {
            entitlement = MarkHolderEntitlement.valueOf(xmlDocument.getNodeValue(ENTITLEMENT_EXPR));
            name = xmlDocument.getNodeValue(NAME_EXPR);
            org = xmlDocument.getNodeValue(ORG_EXPR);
            voice = xmlDocument.getNodeValue(VOICE_EXPR);
            voiceExt = xmlDocument.getNodeValue(VOICE_EXT_EXPR);
            fax = xmlDocument.getNodeValue(FAX_EXPR);
            faxExt = xmlDocument.getNodeValue(FAX_EXT_EXPR);
            email = xmlDocument.getNodeValue(EMAIL_EXPR);
            Node addressElement = xmlDocument.getElement(ADDRESS_EXPR);
            if (addressElement != null) {
                address = new MarkAddress();
                address.fromXML(new XMLDocument((Element) addressElement));
            }
        } catch (XPathExpressionException e) {
            maintLogger.warning(e.getMessage());
        }
    }

    public void setEntitlement(MarkHolderEntitlement entitlement) {
        this.entitlement = entitlement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public MarkAddress getAddress() {
        return address;
    }

    public void setAddress(MarkAddress address) {
        this.address = address;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVoiceExt() {
        return voiceExt;
    }

    public void setVoiceExt(String voiceExt) {
        this.voiceExt = voiceExt;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFaxExt() {
        return faxExt;
    }

    public void setFaxExt(String faxExt) {
        this.faxExt = faxExt;
    }

    public String getEmail() {
        return email;
    }
}
