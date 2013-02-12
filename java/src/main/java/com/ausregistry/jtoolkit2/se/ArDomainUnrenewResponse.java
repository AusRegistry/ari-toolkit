package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;

/**
 * Use this to access domain unrenewal response data as provided in an
 * AusRegistry EPP Extension domain unrenew response.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain unrenew
 * command, implemented by the ArDomainUnrenewCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.ArDomainUnrenewCommand
 */
public class ArDomainUnrenewResponse extends Response {
    private static final long serialVersionUID = -5724827272682186647L;

    private static final String ARDOM_NAME_EXPR =
        "/e:epp/epp:response/epp:resData/ardom:urenData/ardom:name/text()";
    private static final String ARDOM_EX_DATE_EXPR =
        "/e:epp/epp:response/epp:resData/ardom:urenData/ardom:exDate/text()";

    private String name;
    private GregorianCalendar exDate;

    public ArDomainUnrenewResponse() {
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
            name = xmlDoc.getNodeValue(ARDOM_NAME_EXPR);
            String exDateStr = xmlDoc.getNodeValue(ARDOM_EX_DATE_EXPR);
            if (exDateStr != null && exDateStr.length() > 0) {
                exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}

