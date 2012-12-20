#include "se/ContactCheckResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "common/StringUtils.hpp"

using namespace std;

const string& ContactCheckResponse::CON_CHKDATA_COUNT_EXPR()
{
	static const string expr =
		ContactCheckResponse::exprReplace(CheckResponse::CHKDATA_COUNT_EXPR());
	return expr;
}

const string&  ContactCheckResponse::CON_CHKDATA_IND_EXPR()
{
	static const string expr =
		ContactCheckResponse::exprReplace(CheckResponse::CHKDATA_IND_EXPR());
	return expr;
}

const string&  ContactCheckResponse::CON_CHKDATA_IDENT_EXPR()
{
	static const string expr =
		ContactCheckResponse::exprReplace(CheckResponse::CHKDATA_IDENT_EXPR());
	return expr;
}

const string&  ContactCheckResponse::CON_CHKDATA_AVAIL_EXPR()
{
	static const string expr =
		ContactCheckResponse::exprReplace(CheckResponse::CHKDATA_AVAIL_EXPR());
	return expr;
}

const string&  ContactCheckResponse::CON_CHKDATA_REASON_EXPR()
{
	static const string expr =
		ContactCheckResponse::exprReplace(CheckResponse::CHKDATA_REASON_EXPR());
	return expr;
}


ContactCheckResponse::ContactCheckResponse()
    : CheckResponse(StandardObjectType::CONTACT())
{ }


string ContactCheckResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll(
		StringUtils::replaceAll(
			expr, DataResponse::OBJ(),
			StandardObjectType::CONTACT()->getName()),
		"IDENT",
		"id");
}

void ContactCheckResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    CheckResponse::fromXML(xmlDoc);
}
