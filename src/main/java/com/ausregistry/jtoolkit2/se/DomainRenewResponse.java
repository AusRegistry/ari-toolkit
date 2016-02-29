package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access domain object renewal response data as provided in an EPP
 * domain renew response compliant with RFC5730 and RFC5731.  Such a service
 * element is sent by a compliant EPP server in response to a valid domain renew
 * command, implemented by the DomainRenewCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 */
public class DomainRenewResponse extends DataResponse {
    private static final long serialVersionUID = 3782976141801966267L;

    private static final String DOM_NAME_EXPR = RES_DATA_EXPR + "/domain:renData/domain:name/text()";
    private static final String DOM_EX_DATE_EXPR = RES_DATA_EXPR + "/domain:renData/domain:exDate/text()";

    private String name;
    private GregorianCalendar exDate;

    public DomainRenewResponse() {
        super(StandardCommandType.RENEW, StandardObjectType.DOMAIN);
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

