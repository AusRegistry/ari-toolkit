#include "se/PollResponse.hpp"

#include <xalanc/XalanDOM/XalanNode.hpp>
#include <xalanc/XalanDOM/XalanDOMString.hpp>

const std::string PollResponse::RES_DATA_EXPR(Response::RESPONSE_EXPR() + "/e:resData");

const xalanc::XalanDOMString PollResponse::TRN_DATA()
{
    static xalanc::XalanDOMString domStr("trnData");

    return domStr;
}

const xalanc::XalanDOMString PollResponse::INF_DATA()
{
    static xalanc::XalanDOMString domStr("infData");

    return domStr;
}

const xalanc::XalanDOMString PollResponse::PAN_DATA()
{
    static xalanc::XalanDOMString domStr("panData");

    return domStr;
}

PollResponse::PollResponse()
    : resData(NULL)
{
    conTrnResponse = NULL;
    domTrnResponse = NULL;
    conNtfnResponse = NULL;
    domNtfnResponse = NULL;
    conInfoResponse = NULL;
    domInfoResponse = NULL;
}

PollResponse::~PollResponse()
{
    if (conTrnResponse != NULL) {
        delete conTrnResponse;
    } else if (domTrnResponse != NULL) {
        delete domTrnResponse;
    } else if (conNtfnResponse != NULL) {
        delete conNtfnResponse;
    } else if (domNtfnResponse != NULL) {
        delete domNtfnResponse;
    } else if (conInfoResponse != NULL) {
        delete conInfoResponse;
    } else if (domInfoResponse != NULL) {
        delete domInfoResponse;
    }
}

void PollResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    using namespace xalanc;

    debugLogger->LOG_FINEST("enter");
    Response::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        const XalanNode* resDataNode = xmlDoc->getElement(RES_DATA_EXPR);

        if (resDataNode && dynamic_cast<const XalanElement *>(resDataNode) != NULL)
            resData = dynamic_cast<const XalanElement *>(resDataNode);

        if (resData == NULL)
        {
            // Nothing further to extract.
            return;
        }

        XalanNode* childNode = resData->getFirstChild();
        if (!childNode) {
            debugLogger->LOG_FINEST("exit");
            return;
        }

        xalanc::XalanDOMString childName(childNode->getLocalName());
        xalanc::XalanDOMString dom_uri(StandardObjectType::DOMAIN()->getURI().c_str());
        xalanc::XalanDOMString con_uri(StandardObjectType::CONTACT()->getURI().c_str());

        if (xalanc::XalanDOMString::equals(childNode->getNamespaceURI(), dom_uri))
        {
            if (xalanc::XalanDOMString::equals(childName, TRN_DATA())) {
                domTrnResponse = new DomainTransferResponse();
                domTrnResponse->fromXML(xmlDoc);
            }
            else if (xalanc::XalanDOMString::equals(childName, PAN_DATA()))
            {
                domNtfnResponse = new DomainNotificationResponse();
                domNtfnResponse->fromXML(xmlDoc);
            }
            else if (xalanc::XalanDOMString::equals(childName, INF_DATA()))
            {
                domInfoResponse = new DomainInfoResponse();
                domInfoResponse->fromXML(xmlDoc);
            }
        }
        else if (xalanc::XalanDOMString::equals(childNode->getNamespaceURI(), con_uri))
        {
            if (xalanc::XalanDOMString::equals(childName, TRN_DATA())) {
                conTrnResponse = new ContactTransferResponse();
                conTrnResponse->fromXML(xmlDoc);
            }
            else if (xalanc::XalanDOMString::equals(childName, PAN_DATA()))
            {
                conNtfnResponse = new ContactNotificationResponse();
                conNtfnResponse->fromXML(xmlDoc);
            }
            else if (xalanc::XalanDOMString::equals(childName, INF_DATA()))
            {
                conInfoResponse = new ContactInfoResponse();
                conInfoResponse->fromXML(xmlDoc);
            }
        }
    }
    catch (XPathExpressionException& e)
    {
        maintLogger->warning(e.getMessage());
        ParsingException pe;
        pe.causedBy(e);
        throw pe;
    }

    debugLogger->LOG_FINEST("exit");
}

