#include "se/DomainCreateResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "se/EPPDateFormatter.hpp"
#include "common/StringUtils.hpp"

const std::string DomainCreateResponse::DOM_CR_DATE_EXPR
    (DomainCreateResponse::exprReplace
        (CreateResponse::CR_DATE_EXPR()));

const std::string DomainCreateResponse::DOM_NAME_EXPR
    (DomainCreateResponse::exprReplace
        (CreateResponse::CRE_DATA_EXPR()) + 
     "/domain:name/text()");

const std::string DomainCreateResponse::DOM_EX_DATE_EXPR
    (DomainCreateResponse::exprReplace
        (CreateResponse::CRE_DATA_EXPR()) +
     "/domain:exDate/text()");

DomainCreateResponse::DomainCreateResponse()
    : CreateResponse(StandardObjectType::DOMAIN())
{
}


void DomainCreateResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    CreateResponse::fromXML (xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        name = xmlDoc->getNodeValue (DOM_NAME_EXPR);
        std::string exDateStr = xmlDoc->getNodeValue (DOM_EX_DATE_EXPR);
        exDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(exDateStr));
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}

std::string DomainCreateResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    CreateResponse::OBJ(),
                                    StandardObjectType::DOMAIN()->getName());
}
