package com.ausregistry.jtoolkit2.se.generic;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;

/**
 * Use this to access registrant transfer data for a domain as provided in an
 * EPP registrantTransfer response using the
 * {@code urn:X-ar:params:xml:ns:registrant-1.0} namespace. Such a service
 * element is sent by a compliant EPP server in response to a valid domain
 * registrant transfer command, implemented by the
 * DomainRegistrantTransferCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.generic.DomainRegistrantTransferCommand
 */
public class DomainRegistrantTransferResponse extends Response {
    private static final long serialVersionUID = -5724827272682186647L;

    private static final String DOM_NAME_EXPR =
        "/e:epp/epp:response/epp:resData/registrant:rtrnData/registrant:name/text()";
    private static final String DOM_EX_DATE_EXPR =
        "/e:epp/epp:response/epp:resData/registrant:rtrnData/registrant:exDate/text()";

    private String name;
    private GregorianCalendar exDate;

    public DomainRegistrantTransferResponse() {
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
            name = xmlDoc.getNodeValue(DOM_NAME_EXPR);
            String exDateStr = xmlDoc.getNodeValue(DOM_EX_DATE_EXPR);
            exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}
