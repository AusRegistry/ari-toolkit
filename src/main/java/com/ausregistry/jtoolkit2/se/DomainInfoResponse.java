package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access domain object information as provided in an EPP domain
 * info response compliant with RFC5730 and RFC5731.  Such a service element is
 * sent by a compliant EPP server in response to a valid domain info command,
 * implemented by the DomainInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 */
public class DomainInfoResponse extends InfoResponse {
    private static final long serialVersionUID = -5948394715740177139L;

    protected static final String DOM_ROID_EXPR =
        DomainInfoResponse.exprReplace(ROID_EXPR);
    protected static final String DOM_CR_ID_EXPR =
        DomainInfoResponse.exprReplace(CR_ID_EXPR);
    protected static final String DOM_UP_ID_EXPR =
        DomainInfoResponse.exprReplace(UP_ID_EXPR);
    protected static final String DOM_CL_ID_EXPR =
        DomainInfoResponse.exprReplace(CL_ID_EXPR);
    protected static final String DOM_CR_DATE_EXPR =
        DomainInfoResponse.exprReplace(CR_DATE_EXPR);
    protected static final String DOM_UP_DATE_EXPR =
        DomainInfoResponse.exprReplace(UP_DATE_EXPR);
    protected static final String DOM_TR_DATE_EXPR =
        DomainInfoResponse.exprReplace(TR_DATE_EXPR);
    protected static final String DOM_STATUS_COUNT_EXPR =
        DomainInfoResponse.exprReplace(STATUS_COUNT_EXPR);
    protected static final String DOM_STATUS_EXPR =
        DomainInfoResponse.exprReplace(STATUS_EXPR);

    protected static final String DOM_INF_DATA_EXPR =
        DomainInfoResponse.exprReplace(INF_DATA_EXPR);
    protected static final String DOM_NAME_EXPR = DOM_INF_DATA_EXPR +
        "/domain:name/text()";
    protected static final String DOM_PW_EXPR = DOM_INF_DATA_EXPR +
        "/domain:authInfo/domain:pw/text()";
    protected static final String DOM_REGISTRANT_EXPR = DOM_INF_DATA_EXPR +
        "/domain:registrant/text()";
    protected static final String DOM_EX_DATE_EXPR = DOM_INF_DATA_EXPR +
        "/domain:exDate/text()";
    protected static final String DOM_NS_EXPR = DOM_INF_DATA_EXPR +
        "/domain:ns/domain:hostObj/text()";
    protected static final String DOM_HOST_EXPR = DOM_INF_DATA_EXPR +
        "/domain:host/text()";
    protected static final String DOM_CON_EXPR = DOM_INF_DATA_EXPR +
        "/domain:contact[@type='TYPE']/text()";
    protected static final String DOM_CON_TECH_EXPR =
        DOM_CON_EXPR.replaceFirst("TYPE", "tech");
    protected static final String DOM_CON_ADMIN_EXPR =
        DOM_CON_EXPR.replaceFirst("TYPE", "admin");
    protected static final String DOM_CON_BILLING_EXPR =
        DOM_CON_EXPR.replaceFirst("TYPE", "billing");

    private String name, pw, registrantID;
    private String[] techContacts, adminContacts, billingContacts;
    private String[] delHosts, subHosts;
    private GregorianCalendar exDate;
    private String exDateStr;

    protected String roidExpr() {
        return DOM_ROID_EXPR;
    }

    protected String crIDExpr() {
        return DOM_CR_ID_EXPR;
    }

    protected String upIDExpr() {
        return DOM_UP_ID_EXPR;
    }

    protected String clIDExpr() {
        return DOM_CL_ID_EXPR;
    }

    protected String crDateExpr() {
        return DOM_CR_DATE_EXPR;
    }

    protected String upDateExpr() {
        return DOM_UP_DATE_EXPR;
    }

    protected String trDateExpr() {
        return DOM_TR_DATE_EXPR;
    }

    protected String statusExpr() {
        return DOM_STATUS_EXPR;
    }

    protected String statusCountExpr() {
        return DOM_STATUS_COUNT_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, "domain");
    }

    public DomainInfoResponse() {
        super(StandardObjectType.DOMAIN);
    }

    public String getName() {
        return name;
    }

    public String getPW() {
        return pw;
    }

    public GregorianCalendar getExpireDate() {
        return exDate;
    }

    public String getRegistrantID() {
        return registrantID;
    }

    public String[] getTechContacts() {
        return techContacts;
    }

    public String[] getAdminContacts() {
        return adminContacts;
    }

    public String[] getBillingContacts() {
        return billingContacts;
    }

    public String[] getNameservers() {
        return delHosts;
    }

    public String[] getSubordinateHosts() {
        return subHosts;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            name = xmlDoc.getNodeValue(DOM_NAME_EXPR);
            pw = xmlDoc.getNodeValue(DOM_PW_EXPR);
            registrantID = xmlDoc.getNodeValue(DOM_REGISTRANT_EXPR);
            exDateStr = xmlDoc.getNodeValue(DOM_EX_DATE_EXPR);
            if (exDateStr != null) {
                exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
            }
            delHosts = xmlDoc.getNodeValues(DOM_NS_EXPR);
            subHosts = xmlDoc.getNodeValues(DOM_HOST_EXPR);

            techContacts = xmlDoc.getNodeValues(DOM_CON_TECH_EXPR);
            adminContacts = xmlDoc.getNodeValues(DOM_CON_ADMIN_EXPR);
            billingContacts = xmlDoc.getNodeValues(DOM_CON_BILLING_EXPR);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

    @Override
    public String toString() {
        String retval = super.toString();
        retval += "(name = " + name
            + ")(registrant-id = " + registrantID + ")";

        if (exDateStr != null) {
            retval += "(expiry-date = " + exDateStr + ")";
        }

        if (subHosts != null) {
            retval += "(subordinate-hosts = " + arrayToString(subHosts, ",") + ")";
        }

        if (delHosts != null) {
            retval += "(nameserver-hosts = " + arrayToString(delHosts, ",") + ")";
        }

        if (techContacts != null) {
            retval += "(tech-contacts = " + arrayToString(techContacts, ",") + ")";
        }

        if (adminContacts != null) {
            retval += "(admin-contacts = " + arrayToString(adminContacts, ",") + ")";
        }

        if (billingContacts != null) {
            retval += "(billing-contacts = " + arrayToString(billingContacts, ",") + ")";
        }

        return retval;
    }
}

