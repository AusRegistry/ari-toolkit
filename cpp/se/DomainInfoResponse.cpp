#include "se/DomainInfoResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "se/EPPDateFormatter.hpp"

const std::string& DomainInfoResponse::DOM_ROID_EXPR()
{
    static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::ROID_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_CR_ID_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::CR_ID_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_UP_ID_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::UP_ID_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_CL_ID_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::CL_ID_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_CR_DATE_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::CR_DATE_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_UP_DATE_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::UP_DATE_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_TR_DATE_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::TR_DATE_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_STATUS_COUNT_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::STATUS_COUNT_EXPR());
	return expr;
}
const std::string& DomainInfoResponse::DOM_STATUS_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::STATUS_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_INF_DATA_EXPR()
{
	static const std::string expr =
		DomainInfoResponse::exprReplace (InfoResponse::INF_DATA_EXPR());
	return expr;
}

const std::string& DomainInfoResponse::DOM_NAME_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:name/text()";
	return expr;
}

const std::string& DomainInfoResponse::DOM_PW_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:authInfo/domain:pw/text()";
	return expr;
}

const std::string& DomainInfoResponse::DOM_REGISTRANT_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:registrant/text()";
	return expr;
}

const std::string& DomainInfoResponse::DOM_EX_DATE_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:exDate/text()";
	return expr;
}

const std::string& DomainInfoResponse::DOM_NS_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:ns/domain:hostObj";
	return expr;
}

const std::string& DomainInfoResponse::DOM_HOST_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:host";
	return expr;
}

const std::string& DomainInfoResponse::DOM_CON_EXPR()
{
	static const std::string expr =
    	DomainInfoResponse::DOM_INF_DATA_EXPR() + "/domain:contact[@type='TYPE']";
	return expr;
}

const std::string& DomainInfoResponse::DOM_CON_TECH_EXPR()
{
	static const std::string expr =
    	StringUtils::replaceFirst (DomainInfoResponse::DOM_CON_EXPR(), "TYPE", "tech");
	return expr;
}
        
const std::string& DomainInfoResponse::DOM_CON_ADMIN_EXPR()
{
	static const std::string expr =
    	StringUtils::replaceFirst (DomainInfoResponse::DOM_CON_EXPR(), "TYPE", "admin");
	return expr;
}

const std::string& DomainInfoResponse::DOM_CON_BILLING_EXPR()
{
	static const std::string expr =
    	StringUtils::replaceFirst (DomainInfoResponse::DOM_CON_EXPR(), "TYPE", "billing");
	return expr;
}


DomainInfoResponse::DomainInfoResponse()
    : InfoResponse(StandardObjectType::DOMAIN())
{ }

void DomainInfoResponse::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
	InfoResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {

        name = xmlDoc->getNodeValue(DOM_NAME_EXPR());
        pw = xmlDoc->getNodeValue(DOM_PW_EXPR());
        registrantID = xmlDoc->getNodeValue (DOM_REGISTRANT_EXPR());
        exDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime
                    (xmlDoc->getNodeValue (DOM_EX_DATE_EXPR())));
        delHosts = xmlDoc->getNodeValues(DOM_NS_EXPR());
        subHosts = xmlDoc->getNodeValues(DOM_HOST_EXPR());
        
        techContacts = xmlDoc->getNodeValues(DOM_CON_TECH_EXPR());
        adminContacts = xmlDoc->getNodeValues(DOM_CON_ADMIN_EXPR());
        billingContacts = xmlDoc->getNodeValues(DOM_CON_BILLING_EXPR());
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}

std::string DomainInfoResponse::toString() const 
{
    std::string retval = InfoResponse::toString();
    retval += "(name = " + name + ")";
    return retval;
}
        

std::string DomainInfoResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
								    DataResponse::OBJ(),
                                    "domain");
}
