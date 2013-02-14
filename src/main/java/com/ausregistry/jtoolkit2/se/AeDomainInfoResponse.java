package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Extension of the domain mapping of the EPP info response, as defined in
 * RFC5730 and RFC5731, to .ae domain names, the specification of which is in
 * the XML schema definition urn:X-ae:params:xml:ns:aeext-1.0. Instances of this
 * class provide an interface to access all of the information available through
 * EPP for a .ae domain name. This relies on the instance first being
 * initialised by a suitable EPP domain info response using the method fromXML.
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries, the expressions for which are defined statically.
 *
 */
public final class AeDomainInfoResponse extends DomainInfoResponse {
    private static final long serialVersionUID = 6585390748052827078L;
    private static final String AEEXT_EXPR = RESPONSE_EXPR
            + "/e:extension/aeext:infData/aeext:aeProperties";
    private static final String AE_REGISTRANT_NAME_EXPR = AEEXT_EXPR
            + "/aeext:registrantName/text()";
    private static final String AE_REGISTRANT_ID_EXPR = AEEXT_EXPR + "/aeext:registrantID/text()";
    private static final String AE_REGISTRANT_ID_TYPE_EXPR = AEEXT_EXPR
            + "/aeext:registrantID/@type";
    private static final String AE_ELI_TYPE_EXPR = AEEXT_EXPR + "/aeext:eligibilityType/text()";
    private static final String AE_ELI_NAME_EXPR = AEEXT_EXPR + "/aeext:eligibilityName/text()";
    private static final String AE_ELI_ID_EXPR = AEEXT_EXPR + "/aeext:eligibilityID/text()";
    private static final String AE_ELI_ID_TYPE_EXPR = AEEXT_EXPR + "/aeext:eligibilityID/@type";
    private static final String AE_POLICY_REASON_EXPR = AEEXT_EXPR + "/aeext:policyReason/text()";

    private String registrantName;
    private String registrantID;
    private String registrantIDType;
    private String eligibilityType;
    private String eligibilityName;
    private String eligibilityID;
    private String eligibilityIDType;
    private String polReason;
    private int policyReason;

    public AeDomainInfoResponse() {
        super();
    }

    public String getRegistrantName() {
        return registrantName;
    }

    public String getAeRegistrantID() {
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
            registrantName = xmlDoc.getNodeValue(AE_REGISTRANT_NAME_EXPR);
            registrantID = xmlDoc.getNodeValue(AE_REGISTRANT_ID_EXPR);
            registrantIDType = xmlDoc.getNodeValue(AE_REGISTRANT_ID_TYPE_EXPR);
            eligibilityType = xmlDoc.getNodeValue(AE_ELI_TYPE_EXPR);
            eligibilityName = xmlDoc.getNodeValue(AE_ELI_NAME_EXPR);
            eligibilityID = xmlDoc.getNodeValue(AE_ELI_ID_EXPR);
            eligibilityIDType = xmlDoc.getNodeValue(AE_ELI_ID_TYPE_EXPR);
            polReason = xmlDoc.getNodeValue(AE_POLICY_REASON_EXPR);
            if (polReason != null) {
                policyReason = Integer.parseInt(polReason);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

    @Override
    public String toString() {
        String retval = super.toString();

        retval += "(registrant-name = " + registrantName + ")(eligibility-type = "
                + eligibilityType + ")(policy-reason = " + polReason + ")";

        if (registrantID != null) {
            retval += "(registrant-id = " + registrantID + " [" + registrantIDType + "])";
        }

        if (eligibilityName != null) {
            retval += "(eligibility-name = " + eligibilityName + ")";
        }

        if (eligibilityID != null) {
            retval += "(eligibility-id = " + eligibilityID + " [" + eligibilityIDType + "])";
        }

        return retval;
    }
}
