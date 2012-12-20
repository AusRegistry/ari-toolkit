#include "se/HostCreateResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "common/StringUtils.hpp"

const std::string HostCreateResponse::HOS_CR_DATE_EXPR
    (HostCreateResponse::exprReplace
        (CreateResponse::CR_DATE_EXPR()));

const std::string HostCreateResponse::HOS_NAME_EXPR
    (HostCreateResponse::exprReplace
        (CreateResponse::CRE_DATA_EXPR()) + "/host:name/text()");

HostCreateResponse::HostCreateResponse()
    : CreateResponse(StandardObjectType::HOST())
{
}

void HostCreateResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    CreateResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        name = xmlDoc->getNodeValue(HOS_NAME_EXPR);
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}

std::string HostCreateResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    CreateResponse::OBJ(),
                                    StandardObjectType::HOST()->getName());
}
