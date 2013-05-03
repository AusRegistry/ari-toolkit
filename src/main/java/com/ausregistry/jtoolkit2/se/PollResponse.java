package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access poll response information, as provided in an EPP poll
 * response compliant with RFC5730.  Such a service element is sent by an EPP
 * server in response to a poll service element.  If in response to a poll
 * request, the getResData, getContactTransferResponse or
 * getDomainTransferResponse methods may return object-specific information,
 * which is in addition to any message queue data potentially available via the
 * {@code getMessage} and related methods in the {@link
 * com.ausregistry.jtoolkit2.se.Response} class.
 *
 * @see com.ausregistry.jtoolkit2.se.PollCommand
 * @see com.ausregistry.jtoolkit2.se.PollRequestCommand
 * @see com.ausregistry.jtoolkit2.se.PollAckCommand
 */
public class PollResponse extends Response {
    private static final long serialVersionUID = 8823883186209520812L;

    private static final String RES_DATA_EXPR = RESPONSE_EXPR + "/e:resData";
    private static final String PAN_DATA = "panData";
    private static final String TRN_DATA = "trnData";
    private static final String INF_DATA = "infData";

    /// We can't serialize the resData element.
    private transient Element resData;
    private DomainTransferResponse domTrnResponse;
    private ContactTransferResponse conTrnResponse;
    private DomainNotificationResponse domNtfnResponse;
    private ContactNotificationResponse conNtfnResponse;
    private DomainInfoResponse domInfoResponse;
    private ContactInfoResponse conInfoResponse;
    private HostInfoResponse hostInfoResponse;
    private Response resDataResponse = null;

    public PollResponse() {
    }

    public Element getResData() {
        return resData;
    }

    public ContactTransferResponse getContactTransferResponse() {
        return conTrnResponse;
    }

    public DomainTransferResponse getDomainTransferResponse() {
        return domTrnResponse;
    }

    public ContactNotificationResponse getContactNotificationResponse() {
        return conNtfnResponse;
    }

    public DomainNotificationResponse getDomainNotificationResponse() {
        return domNtfnResponse;
    }

    public ContactInfoResponse getContactInfoResponse() {
        return conInfoResponse;
    }

    public DomainInfoResponse getDomainInfoResponse() {
        return domInfoResponse;
    }

    public HostInfoResponse getHostInfoResponse() {
        return hostInfoResponse;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            Node resDataNode = xmlDoc.getElement(RES_DATA_EXPR);

            if (resDataNode != null && resDataNode instanceof Element) {
                resData = (Element) resDataNode;
            }

            if (resData == null) {
                debugLogger.finest("exit");
                return;
            }

            Node childNode = resData.getFirstChild();
            if (childNode == null) {
                debugLogger.finest("exit");
                return;
            }

            String childName = childNode.getLocalName();

            if (childNode.getNamespaceURI().equals(
                        StandardObjectType.DOMAIN.getURI())) {
                if (childName.equals(PAN_DATA)) {
                    domNtfnResponse = new DomainNotificationResponse();
                    domNtfnResponse.fromXML(xmlDoc);
                    resDataResponse = domNtfnResponse;
                } else if (childName.equals(TRN_DATA)) {
                    domTrnResponse = new DomainTransferResponse();
                    domTrnResponse.fromXML(xmlDoc);
                    resDataResponse = domTrnResponse;
                } else if (childName.equals(INF_DATA)) {
                    domInfoResponse = new DomainInfoResponse();
                    domInfoResponse.fromXML(xmlDoc);
                    resDataResponse = domInfoResponse;
                }
            } else if (childNode.getNamespaceURI().equals(
                        StandardObjectType.CONTACT.getURI())) {
                if (childName.equals(PAN_DATA)) {
                    conNtfnResponse = new ContactNotificationResponse();
                    conNtfnResponse.fromXML(xmlDoc);
                    resDataResponse = conNtfnResponse;
                } else if (childName.equals(TRN_DATA)) {
                    conTrnResponse = new ContactTransferResponse();
                    conTrnResponse.fromXML(xmlDoc);
                    resDataResponse = conTrnResponse;
                } else if (childName.equals(INF_DATA)) {
                    conInfoResponse = new ContactInfoResponse();
                    conInfoResponse.fromXML(xmlDoc);
                    resDataResponse = conInfoResponse;
                }
            } else if (childNode.getNamespaceURI().equals(StandardObjectType.HOST.getURI())) {
                if (childName.equals(INF_DATA)) {
                    hostInfoResponse = new HostInfoResponse();
                    hostInfoResponse.fromXML(xmlDoc);
                    resDataResponse = hostInfoResponse;
                }
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }

    private boolean isResDataAvailable() {
        return resDataResponse != null;
    }

    @Override
    public String toString() {
        if (isResDataAvailable()) {
            return resDataResponse.toString();
        } else {
            return super.toString();
        }
    }
}

