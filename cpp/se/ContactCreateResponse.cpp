#include "se/ContactCreateResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "common/StringUtils.hpp"

const std::string ContactCreateResponse::CON_CR_DATE_EXPR
    (ContactCreateResponse::exprReplace (CreateResponse::CR_DATE_EXPR()));

const std::string ContactCreateResponse::CON_ID_EXPR
    (ContactCreateResponse::exprReplace
        (CreateResponse::CRE_DATA_EXPR()) +
     "/contact:id/text()");
    
ContactCreateResponse::ContactCreateResponse()
    : CreateResponse(StandardObjectType::CONTACT())
{ }


void ContactCreateResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    CreateResponse::fromXML(xmlDoc);
    
    try
    {
        id = xmlDoc->getNodeValue (CON_ID_EXPR);
    }
    catch (XPathExpressionException& e)
    {
		maintLogger->warning(e.getMessage());
		ParsingException p;
		p.causedBy(e);
		throw p;
	}
}


std::string ContactCreateResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    CreateResponse::OBJ(),
                                    StandardObjectType::CONTACT()->getName());
}
