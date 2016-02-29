package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Represents an smdIssuerInfo from the Trademark Clearing House extension, represented by the "issuerInfo" element
 * of type "issuerInfoType" in the "urn:ietf:params:xml:ns:signedMark-1.0" namespace,
 * defined in the "signedMark-1.0.xsd" schema.
 *
 */
public class SmdIssuerInfo {

    private static final String ISSUER_INFO_ID_EXPR = "@issuerID";
    private static final String ISSUER_INFO_ORG_EXPR = "smd:org/text()";
    private static final String ISSUER_INFO_EMAIL_EXPR = "smd:email/text()";
    private static final String ISSUER_INFO_URL_EXPR = "smd:url/text()";
    private static final String ISSUER_INFO_VOICE_EXPR = "smd:voice/text()";
    private static final String ISSUER_INFO_VOICE_EXT_EXPR = "smd:voice/@x";

    private Logger maintLogger = Logger.getLogger(getClass().getPackage().getName() + ".maint");

    private String id;

    private String org;

    private String email;

    private String url;

    private String voice;

    private String voiceExt;

    public void fromXML(XMLDocument xmlDocument) {
        try {
            id = xmlDocument.getNodeValue(ISSUER_INFO_ID_EXPR);
            org = xmlDocument.getNodeValue(ISSUER_INFO_ORG_EXPR);
            email = xmlDocument.getNodeValue(ISSUER_INFO_EMAIL_EXPR);
            url = xmlDocument.getNodeValue(ISSUER_INFO_URL_EXPR);
            voice = xmlDocument.getNodeValue(ISSUER_INFO_VOICE_EXPR);
            voiceExt = xmlDocument.getNodeValue(ISSUER_INFO_VOICE_EXT_EXPR);
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

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
