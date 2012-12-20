#include "se/DomainCheckResponse.hpp"
#include "common/StringUtils.hpp"
#include "se/StandardObjectType.hpp"

const std::string& DomainCheckResponse::DOM_CHKDATA_COUNT_EXPR()
{
    static const std::string expr = DomainCheckResponse::exprReplace (CheckResponse::CHKDATA_COUNT_EXPR());
	return expr;
}

const std::string& DomainCheckResponse::DOM_CHKDATA_IND_EXPR()
{
    static const std::string expr = DomainCheckResponse::exprReplace (CheckResponse::CHKDATA_IND_EXPR());
	return expr;
}

const std::string& DomainCheckResponse::DOM_CHKDATA_IDENT_EXPR()
{
    static const std::string expr = DomainCheckResponse::exprReplace (CheckResponse::CHKDATA_IDENT_EXPR());
	return expr;
}
 
const std::string& DomainCheckResponse::DOM_CHKDATA_AVAIL_EXPR()
{
    static const std::string expr = DomainCheckResponse::exprReplace (CheckResponse::CHKDATA_AVAIL_EXPR());
	return expr;
}

const std::string& DomainCheckResponse::DOM_CHKDATA_REASON_EXPR()
{
    static const std::string expr = DomainCheckResponse::exprReplace (CheckResponse::CHKDATA_REASON_EXPR());
	return expr;
}

DomainCheckResponse::DomainCheckResponse()
    : CheckResponse (StandardObjectType::DOMAIN())
{ }

std::string DomainCheckResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll(
		StringUtils::replaceAll(
			expr,
			DataResponse::OBJ(),
			StandardObjectType::DOMAIN()->getName()),
         "IDENT",
         "name");
}

