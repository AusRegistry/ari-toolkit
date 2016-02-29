package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access create data for a domain as provided in an EPP domain
 * create response compliant with RFC5730 and RFC5731.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain create
 * command, implemented by the DomainCreateCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 */
public class DomainCreateResponse extends CreateResponse {
    private static final long serialVersionUID = -5724827272682186647L;

    private static final String DOM_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
    private static final String DOM_NAME_EXPR = exprReplace(CRE_DATA_EXPR) + "/domain:name/text()";
    private static final String DOM_EX_DATE_EXPR = exprReplace(CRE_DATA_EXPR) + "/domain:exDate/text()";

    private String name;
    private GregorianCalendar exDate;

    public DomainCreateResponse() {
        super(StandardObjectType.DOMAIN);
    }

    protected String crDateExpr() {
        return DOM_CR_DATE_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, StandardObjectType.DOMAIN.getName());
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getExpiryDate() {
        return exDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            name = xmlDoc.getNodeValue(DOM_NAME_EXPR);
            String exDateStr = xmlDoc.getNodeValue(DOM_EX_DATE_EXPR);
            exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}

