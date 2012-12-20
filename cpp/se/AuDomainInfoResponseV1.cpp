#include "se/AuDomainInfoResponseV1.hpp"
#include "se/StandardObjectType.hpp"

#include <stdlib.h> // atoi()

using namespace std;

const string AuDomainInfoResponseV1::AUEXT_EXPR(
		Response::RESPONSE_EXPR() + "/e:extension/auextv1:extensionAU/auextv1:info");
const string AuDomainInfoResponseV1::AU_REGISTRANT_NAME_EXPR(
		AUEXT_EXPR + "/auextv1:registrantName/text()");
const string AuDomainInfoResponseV1::AU_REGISTRANT_ID_EXPR(
		AUEXT_EXPR + "/auextv1:registrantID/text()");
const string AuDomainInfoResponseV1::AU_REGISTRANT_ID_TYPE_EXPR(
		AUEXT_EXPR + "/auextv1:registrantID/@type");
const string AuDomainInfoResponseV1::AU_ELI_TYPE_EXPR(
		AUEXT_EXPR + "/auextv1:eligibilityType/text()");
const string AuDomainInfoResponseV1::AU_ELI_NAME_EXPR(
		AUEXT_EXPR + "/auextv1:eligibilityName/text()");
const string AuDomainInfoResponseV1::AU_ELI_ID_EXPR(
		AUEXT_EXPR + "/auextv1:eligibilityID/text()");
const string AuDomainInfoResponseV1::AU_ELI_ID_TYPE_EXPR(
		AUEXT_EXPR + "/auextv1:eligibilityID/@type");
const string AuDomainInfoResponseV1::AU_POLICY_REASON_EXPR(
		AUEXT_EXPR + "/auextv1:policyReason/text()");


    
AuDomainInfoResponseV1::AuDomainInfoResponseV1()
    : DomainInfoResponse()
{ }

void AuDomainInfoResponseV1::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
    DomainInfoResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        registrantName = xmlDoc->getNodeValue(AU_REGISTRANT_NAME_EXPR);
        registrantID = xmlDoc->getNodeValue(AU_REGISTRANT_ID_EXPR);
        registrantIDType = xmlDoc->getNodeValue(AU_REGISTRANT_ID_TYPE_EXPR);
        eligibilityType = xmlDoc->getNodeValue(AU_ELI_TYPE_EXPR);
        eligibilityName = xmlDoc->getNodeValue(AU_ELI_NAME_EXPR);
        eligibilityID = xmlDoc->getNodeValue(AU_ELI_ID_EXPR);
        eligibilityIDType = xmlDoc->getNodeValue(AU_ELI_ID_TYPE_EXPR);
        
        policyReason = atoi (xmlDoc->getNodeValue(AU_POLICY_REASON_EXPR).c_str());
    }
    catch (XPathExpressionException& e)
    {
		maintLogger->warning(e.getMessage());
		ParsingException pe("Could not parse AuDomainInfoResponse object.");
		pe.causedBy(e);
		throw pe;
	}
}
