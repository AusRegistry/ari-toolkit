package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.app.DomainInfoApplicationResponseExtension;
import com.ausregistry.jtoolkit2.se.idn.ietf.DomainInfoIetfIdnResponseExtension;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ausregistry.jtoolkit2.se.generic.DomainInfoKVResponseExtension;
import com.ausregistry.jtoolkit2.se.idn.DomainInfoIdnResponseExtension;
import com.ausregistry.jtoolkit2.se.rgp.DomainInfoRgpResponseExtension;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainInfoResponseExtension;
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
    private DomainInfoIdnResponseExtension domainInfoIdnResponseExtension;
    private DomainInfoIetfIdnResponseExtension domainInfoIetfIdnResponseExtension;
    private SecDnsDomainInfoResponseExtension secDnsDomainInfoResponseExtension;
    private DomainVariantResponseExtensionV1_1 variantResponseExtension1_1;
    private DomainInfoRgpResponseExtension rgpDomainInfoResponseExtension;
    private DomainInfoKVResponseExtension kvDomainInfoResponseExtension;
    private DomainInfoApplicationResponseExtension domainInfoApplicationResponseExtension;

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

            // the first child might be a whitespace node.  Skip over siblings until an ELEMENT node is encountered.
            while (childNode != null && childNode.getNodeType() != Node.ELEMENT_NODE) {
                childNode = childNode.getNextSibling();
            }

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
                    initialiseExtensions();
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

    private void initialiseExtensions() {
        domainInfoIdnResponseExtension = new DomainInfoIdnResponseExtension();
        domainInfoIetfIdnResponseExtension = new DomainInfoIetfIdnResponseExtension();
        secDnsDomainInfoResponseExtension = new SecDnsDomainInfoResponseExtension();
        variantResponseExtension1_1 = new DomainVariantResponseExtensionV1_1(ResponseExtension.INFO);
        rgpDomainInfoResponseExtension = new DomainInfoRgpResponseExtension(ResponseExtension.INFO);
        kvDomainInfoResponseExtension = new DomainInfoKVResponseExtension();
        domainInfoApplicationResponseExtension = new DomainInfoApplicationResponseExtension(ResponseExtension.INFO);
        domInfoResponse.registerExtension(domainInfoIdnResponseExtension);
        domInfoResponse.registerExtension(domainInfoIetfIdnResponseExtension);
        domInfoResponse.registerExtension(secDnsDomainInfoResponseExtension);
        domInfoResponse.registerExtension(variantResponseExtension1_1);
        domInfoResponse.registerExtension(rgpDomainInfoResponseExtension);
        domInfoResponse.registerExtension(kvDomainInfoResponseExtension);
        domInfoResponse.registerExtension(domainInfoApplicationResponseExtension);
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

    public DomainInfoIdnResponseExtension getIdnDomainInfoResponseExtension() {
        return domainInfoIdnResponseExtension;
    }

    public SecDnsDomainInfoResponseExtension getSecDnsDomainInfoResponseExtension() {
        return secDnsDomainInfoResponseExtension;
    }

    public DomainVariantResponseExtensionV1_1 getVariantDomainInfoResponseExtensionV1_1() {
        return variantResponseExtension1_1;
    }

    public DomainInfoRgpResponseExtension getRgpDomainInfoResponseExtension() {
        return rgpDomainInfoResponseExtension;
    }

    public DomainInfoKVResponseExtension getKvDomainInfoResponseExtension() {
        return kvDomainInfoResponseExtension;
    }

    public DomainInfoApplicationResponseExtension getDomainInfoApplicationResponseExtension() {
        return domainInfoApplicationResponseExtension;
    }
}

