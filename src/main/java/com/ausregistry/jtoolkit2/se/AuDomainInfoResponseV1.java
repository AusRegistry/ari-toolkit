package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Legacy extension of the domain mapping of the EPP info response, as defined
 * in RFC5730 and RFC5731, to .au domain names, the specification of which is
 * in the XML schema definition urn:au:params:xml:ns:auext-1.0.
 * Instances of this class provide an interface to access all of the
 * information available through EPP for a .au domain name.  This relies on the
 * instance first being initialised by a suitable EPP domain info response
 * using the method fromXML.  For flexibility, this implementation extracts the
 * data from the response using XPath queries, the expressions for which are
 * defined statically.
 */
public final class AuDomainInfoResponseV1 extends DomainInfoResponse {
    private static final long serialVersionUID = 6585390748052827078L;
    private static final String AUEXT_EXPR =
        RESPONSE_EXPR + "/e:extension/auext1:extensionAU/auext1:info";
    private static final String AU_REGISTRANT_NAME_EXPR =
        AUEXT_EXPR + "/auext1:registrantName/text()";
    private static final String AU_REGISTRANT_ID_EXPR =
        AUEXT_EXPR + "/auext1:registrantID/text()";
    private static final String AU_REGISTRANT_ID_TYPE_EXPR =
        AUEXT_EXPR + "/auext1:registrantID/@type";
    private static final String AU_ELI_TYPE_EXPR =
        AUEXT_EXPR + "/auext1:eligibilityType/text()";
    private static final String AU_ELI_NAME_EXPR =
        AUEXT_EXPR + "/auext1:eligibilityName/text()";
    private static final String AU_ELI_ID_EXPR =
        AUEXT_EXPR + "/auext1:eligibilityID/text()";
    private static final String AU_ELI_ID_TYPE_EXPR =
        AUEXT_EXPR + "/auext1:eligibilityID/@type";
    private static final String AU_POLICY_REASON_EXPR =
        AUEXT_EXPR + "/auext1:policyReason/text()";

    private String registrantName;
    private String registrantID;
    private String registrantIDType;
    private String eligibilityType;
    private String eligibilityName;
    private String eligibilityID;
    private String eligibilityIDType;
    private int policyReason;

    public AuDomainInfoResponseV1() {
        super();
    }

    public String getRegistrantName() {
        return registrantName;
    }

    public String getAuRegistrantID() {
        return registrantID;
    }

    public String getRegistrantIDType() {
        return registrantIDType;
    }

    public String getEligibilityType() {
        return eligibilityType;
    }

    public String getEligibilityName() {
        return eligibilityName;
    }

    public String getEligibilityID() {
        return eligibilityID;
    }

    public String getEligibilityIDType() {
        return eligibilityIDType;
    }

    public int getPolicyReason() {
        return policyReason;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            registrantName = xmlDoc.getNodeValue(AU_REGISTRANT_NAME_EXPR);
            registrantID = xmlDoc.getNodeValue(AU_REGISTRANT_ID_EXPR);
            registrantIDType = xmlDoc.getNodeValue(AU_REGISTRANT_ID_TYPE_EXPR);
            eligibilityType = xmlDoc.getNodeValue(AU_ELI_TYPE_EXPR);
            eligibilityName = xmlDoc.getNodeValue(AU_ELI_NAME_EXPR);
            eligibilityID = xmlDoc.getNodeValue(AU_ELI_ID_EXPR);
            eligibilityIDType = xmlDoc.getNodeValue(AU_ELI_ID_TYPE_EXPR);
            String polReason = xmlDoc.getNodeValue(AU_POLICY_REASON_EXPR);
            policyReason = Integer.parseInt(polReason);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}

