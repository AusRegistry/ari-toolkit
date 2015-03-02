package com.ausregistry.jtoolkit2.se.blocked;

import java.util.GregorianCalendar;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.DataResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access blocked domain object information as provided in an EPP blocked domain
 * info response. Such a service element is sent by a ari registry server
 * in response to a valid blocked domain info command, implemented by the
 * BlockedInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.blocked.BlockedInfoCommand
 */
public class BlockedInfoResponse extends DataResponse {
    private static final long serialVersionUID = -4403413192868009866L;

    private BlockedDomain blockedDomain;

    public BlockedInfoResponse() {
        super(new BlockedDomainInfoCommandType(), ExtendedObjectType.BLOCKED);
    }

    @Override
    public void fromXML(XMLDocument xmlDocArg) {
        debugLogger.finest("enter");
        super.fromXML(xmlDocArg);
        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            Node blockedInfDataNode = xmlDocArg.getElement(RESPONSE_EXPR + "/e:resData/blocked:infData");

            blockedDomain = parseBlockedDomainNode(blockedInfDataNode);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }

    private BlockedDomain parseBlockedDomainNode(Node blockedInfDataNode) {
        NodeList blockedDomainElements = blockedInfDataNode.getChildNodes();
        String id = null;
        String domainName = null;
        String registrantContactId = null;
        String clID = null;
        GregorianCalendar crDate = null;
        GregorianCalendar exDate = null;
        for (int j = 0; j < blockedDomainElements.getLength(); j++) {
            Node currentNode = blockedDomainElements.item(j);
            String elementName = currentNode.getNodeName();

            if ("blocked:id".equals(elementName)) {
                id = currentNode.getFirstChild().getNodeValue();
            } else if ("blocked:name".equals(elementName)) {
                domainName = currentNode.getFirstChild().getNodeValue();
            } else if ("blocked:registrant".equals(elementName)) {
                registrantContactId = currentNode.getFirstChild().getNodeValue();
            } else if ("blocked:clID".equals(elementName)) {
                clID = currentNode.getFirstChild().getNodeValue();
            } else if ("blocked:crDate".equals(elementName)) {
                crDate = EPPDateFormatter.fromXSDateTime(currentNode.getFirstChild().getNodeValue());
            } else if ("blocked:exDate".equals(elementName)) {
                exDate = EPPDateFormatter.fromXSDateTime(currentNode.getFirstChild().getNodeValue());
            }
        }
        return new BlockedDomain(id, domainName, registrantContactId, clID, crDate, exDate);
    }

    public BlockedDomain getBlockedDomain() {
        return blockedDomain;
    }
}
