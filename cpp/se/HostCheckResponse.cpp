#include "se/HostCheckResponse.hpp"
#include "se/CheckResponse.hpp"
#include "se/StandardObjectType.hpp"

#include "common/StringUtils.hpp"

const std::string& HostCheckResponse::chkDataCountExpr() const
{
	static const std::string expr
		= HostCheckResponse::exprReplace(CheckResponse::CHKDATA_COUNT_EXPR());
	return expr;
}

const std::string& HostCheckResponse::chkDataIndexExpr() const
{
	static const std::string expr
		= HostCheckResponse::exprReplace (CheckResponse::CHKDATA_IND_EXPR());
	return expr;
}

const std::string& HostCheckResponse::chkDataTextExpr() const
{
	static const std::string expr
		= HostCheckResponse::exprReplace (CheckResponse::CHKDATA_IDENT_EXPR());
	return expr;
}

const std::string& HostCheckResponse::chkDataAvailExpr() const
{
	static const std::string expr
		= HostCheckResponse::exprReplace (CheckResponse::CHKDATA_AVAIL_EXPR());
	return expr;
}

const std::string& HostCheckResponse::chkDataReasonExpr() const
{
	static const std::string expr
		= HostCheckResponse::exprReplace (CheckResponse::CHKDATA_REASON_EXPR());
	return expr;
}


HostCheckResponse::HostCheckResponse()
    : CheckResponse (StandardObjectType::HOST())
{ }

std::string HostCheckResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll 
        (StringUtils::replaceAll
            (expr,
             DataResponse::OBJ(),
             StandardObjectType::HOST()->getName()),
         "IDENT",
         "name");
}
