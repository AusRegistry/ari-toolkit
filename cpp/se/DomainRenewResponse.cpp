#include "se/DomainRenewResponse.hpp"
#include "se/StandardCommandType.hpp"
#include "se/StandardObjectType.hpp"
#include "se/EPPDateFormatter.hpp"

const std::string DomainRenewResponse::DOM_NAME_EXPR
    (DataResponse::RES_DATA_EXPR() + "/domain:renData/domain:name/text()");

const std::string DomainRenewResponse::DOM_EX_DATE_EXPR
    (DataResponse::RES_DATA_EXPR() + "/domain:renData/domain:exDate/text()");

DomainRenewResponse::DomainRenewResponse ()
    : DataResponse(StandardCommandType::RENEW(), StandardObjectType::DOMAIN())
{
}

void DomainRenewResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML (xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        name = xmlDoc->getNodeValue (DOM_NAME_EXPR);
        std::string exDateStr = xmlDoc->getNodeValue (DOM_EX_DATE_EXPR);
        exDate = std::auto_ptr<XMLGregorianCalendar>(
				EPPDateFormatter::fromXSDateTime(exDateStr));
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}

