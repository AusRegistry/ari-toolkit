#include "se/AeDomainInfoResponse.hpp"
#include "se/StandardObjectType.hpp"

#include <stdlib.h> // atoi()

using namespace std;

const std::string AeDomainInfoResponse::AEEXT_EXPR(Response::RESPONSE_EXPR() + "/e:extension/aeext:infData");
const std::string AeDomainInfoResponse::AE_PROPERTIES_EXPR(AeDomainInfoResponse::AEEXT_EXPR + "/aeext:aeProperties");
const std::string AeDomainInfoResponse::AE_REGISTRANT_NAME_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:registrantName/text()");
const std::string AeDomainInfoResponse::AE_REGISTRANT_ID_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:registrantID/text()");
const std::string AeDomainInfoResponse::AE_REGISTRANT_ID_TYPE_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:registrantID/@type");
const std::string AeDomainInfoResponse::AE_ELI_TYPE_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:eligibilityType/text()");
const std::string AeDomainInfoResponse::AE_ELI_NAME_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:eligibilityName/text()");
const std::string AeDomainInfoResponse::AE_ELI_ID_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:eligibilityID/text()");
const std::string AeDomainInfoResponse::AE_ELI_ID_TYPE_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:eligibilityID/@type");
const std::string AeDomainInfoResponse::AE_POLICY_REASON_EXPR(AeDomainInfoResponse::AE_PROPERTIES_EXPR + "/aeext:policyReason/text()");



AeDomainInfoResponse::AeDomainInfoResponse() : DomainInfoResponse()
{
    policyReason = 0;
}

void AeDomainInfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DomainInfoResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        registrantName = xmlDoc->getNodeValue (AE_REGISTRANT_NAME_EXPR);
        registrantID = xmlDoc->getNodeValue (AE_REGISTRANT_ID_EXPR);
        registrantIDType = xmlDoc->getNodeValue (AE_REGISTRANT_ID_TYPE_EXPR);
        eligibilityType = xmlDoc->getNodeValue (AE_ELI_TYPE_EXPR);
        eligibilityName = xmlDoc->getNodeValue (AE_ELI_NAME_EXPR);
        eligibilityID = xmlDoc->getNodeValue (AE_ELI_ID_EXPR);
        eligibilityIDType = xmlDoc->getNodeValue (AE_ELI_ID_TYPE_EXPR);

        string polReasonStr = xmlDoc->getNodeValue (AE_POLICY_REASON_EXPR);
        if (polReasonStr.length() > 0) {
            policyReason = atoi (polReasonStr.c_str());
        }
    }
    catch (XPathExpressionException& e)
    {
        maintLogger->warning(e.getMessage());
        ParsingException pe("Could not parse AeDomainInfoResponse object.");
        pe.causedBy(e);
        throw pe;
    }
}

