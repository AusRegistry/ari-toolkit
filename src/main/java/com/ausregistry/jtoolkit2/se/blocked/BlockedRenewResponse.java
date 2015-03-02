package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.DataResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;

/**
 * Use this to access blocked domain object information as provided in an EPP blocked domain
 * info response. Such a service element is sent by a ari registry server
 * in response to a valid blocked domain info command, implemented by the
 * BlockedInfoCommand class.
 *
 * @see BlockedInfoCommand
 */
public class BlockedRenewResponse extends DataResponse {
    private static final long serialVersionUID = -4403413192868009866L;

    private static final String BLOCKED_DOMAIN_ID_EXPR = RES_DATA_EXPR +
            "/blocked:renData/blocked:id/text()";
    private static final String BLOCKED_DOMAIN_EXPIRY_DATE_EXPR = RES_DATA_EXPR +
            "/blocked:renData/blocked:exDate/text()";

    private String id;
    private GregorianCalendar exDate;

    public BlockedRenewResponse() {
        super(new BlockedDomainRenewCommandType(), ExtendedObjectType.BLOCKED);
    }

    public String getId() {
        return id;
    }

    public GregorianCalendar getExDate() {
        return exDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            id = xmlDoc.getNodeValue(BLOCKED_DOMAIN_ID_EXPR);
            String exDateStr = xmlDoc.getNodeValue(BLOCKED_DOMAIN_EXPIRY_DATE_EXPR);
            exDate = EPPDateFormatter.fromXSDateTime(exDateStr);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}
