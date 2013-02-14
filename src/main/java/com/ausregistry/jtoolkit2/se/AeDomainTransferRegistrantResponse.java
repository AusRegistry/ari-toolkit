package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;

/**
 * Use this to access registrant transfer data for a .ae domain as provided in
 * an EPP registrantTransfer response. Such a service element is sent by a
 * compliant EPP server in response to a valid domain registrant transfer
 * command, implemented by the AeDomainTransferRegistrantCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.AeDomainTransferRegistrantCommand
 *
 */
public class AeDomainTransferRegistrantResponse extends Response {
    private static final long serialVersionUID = -5724827272682186647L;

    private static final String AEDOM_NAME_EXPR =
        "/e:epp/epp:response/epp:resData/aedom:rtrnData/aedom:name/text()";
    private static final String AEDOM_EX_DATE_EXPR =
        "/e:epp/epp:response/epp:resData/aedom:rtrnData/aedom:exDate/text()";

    private String name;
    private GregorianCalendar exDate;

    public AeDomainTransferRegistrantResponse() {
        super();
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
            name = xmlDoc.getNodeValue(AEDOM_NAME_EXPR);
            String exDateStr = xmlDoc.getNodeValue(AEDOM_EX_DATE_EXPR);
            exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}
