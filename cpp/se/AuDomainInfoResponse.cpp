#include "se/AuDomainInfoResponse.hpp"
#include "se/StandardObjectType.hpp"

#include <stdlib.h> // atoi()

using namespace std;

const std::string AuDomainInfoResponse::AUEXT_EXPR(Response::RESPONSE_EXPR() + "/e:extension/auext:infData");
const std::string AuDomainInfoResponse::AU_PROPERTIES_EXPR(AuDomainInfoResponse::AUEXT_EXPR + "/auext:auProperties");
const std::string AuDomainInfoResponse::AU_REGISTRANT_NAME_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:registrantName/text()");
const std::string AuDomainInfoResponse::AU_REGISTRANT_ID_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:registrantID/text()");
const std::string AuDomainInfoResponse::AU_REGISTRANT_ID_TYPE_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:registrantID/@type");
const std::string AuDomainInfoResponse::AU_ELI_TYPE_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:eligibilityType/text()");
const std::string AuDomainInfoResponse::AU_ELI_NAME_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:eligibilityName/text()");
const std::string AuDomainInfoResponse::AU_ELI_ID_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:eligibilityID/text()");
const std::string AuDomainInfoResponse::AU_ELI_ID_TYPE_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:eligibilityID/@type");
const std::string AuDomainInfoResponse::AU_POLICY_REASON_EXPR(AuDomainInfoResponse::AU_PROPERTIES_EXPR + "/auext:policyReason/text()");



AuDomainInfoResponse::AuDomainInfoResponse() : DomainInfoResponse()
{
    policyReason = 0;
}

void AuDomainInfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DomainInfoResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        registrantName = xmlDoc->getNodeValue (AU_REGISTRANT_NAME_EXPR);
        registrantID = xmlDoc->getNodeValue (AU_REGISTRANT_ID_EXPR);
        registrantIDType = xmlDoc->getNodeValue (AU_REGISTRANT_ID_TYPE_EXPR);
        eligibilityType = xmlDoc->getNodeValue (AU_ELI_TYPE_EXPR);
        eligibilityName = xmlDoc->getNodeValue (AU_ELI_NAME_EXPR);
        eligibilityID = xmlDoc->getNodeValue (AU_ELI_ID_EXPR);
        eligibilityIDType = xmlDoc->getNodeValue (AU_ELI_ID_TYPE_EXPR);

        string polReasonStr = xmlDoc->getNodeValue (AU_POLICY_REASON_EXPR);
        if (polReasonStr.length() > 0) {
            policyReason = atoi (polReasonStr.c_str());
        }
    }
    catch (XPathExpressionException& e)
    {
        maintLogger->warning(e.getMessage());
        ParsingException pe("Could not parse AuDomainInfoResponse object.");
        pe.causedBy(e);
        throw pe;
    }
}

