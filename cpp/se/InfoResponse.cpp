#include "se/InfoResponse.hpp"

#include "se/StandardCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

#include <iostream>

const std::string InfoResponse::INF_DATA_EXPR()
{
	static std::string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:infData";
	return expr;
}
const std::string InfoResponse::ROID_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:roid/text()";
	return expr;
}

const std::string InfoResponse::CR_ID_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:crID/text()";
	return expr;
}
const std::string InfoResponse::UP_ID_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:upID/text()";
	return expr;
}
const std::string InfoResponse::CL_ID_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:clID/text()";
	return expr;
}
const std::string InfoResponse::CR_DATE_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:crDate/text()";
	return expr;
}
const std::string InfoResponse::UP_DATE_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:upDate/text()";
	return expr;
}
const std::string InfoResponse::TR_DATE_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:trDate/text()";
	return expr;
}
const std::string InfoResponse::STATUS_COUNT_EXPR()
{
	static std::string expr = "count(" + InfoResponse::INF_DATA_EXPR() + "/OBJ:status)";
	return expr;
}
const std::string InfoResponse::STATUS_EXPR()
{
	static std::string expr = InfoResponse::INF_DATA_EXPR() + "/OBJ:status[IDX]";
	return expr;
}
const std::string InfoResponse::STATUS_S_EXPR()
{
	static std::string expr = "/@s";
	return expr;
}
const std::string InfoResponse::STATUS_REASON_EXPR()
{
	static std::string expr = "/text()";
	return expr;
}
const std::string InfoResponse::STATUS_LANG_EXPR()
{
	static std::string expr = "/@lang";
	return expr;
}

InfoResponse::InfoResponse (const ObjectType* objectType)
    : DataResponse(StandardCommandType::INFO(), objectType)
{ }

void InfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML (xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

	try
	{
		roid = xmlDoc->getNodeValue (roidExpr());
		
		crID = xmlDoc->getNodeValue (crIDExpr());
		upID = xmlDoc->getNodeValue (upIDExpr());
		clID = xmlDoc->getNodeValue (clIDExpr());
		
		std::string crDateStr = xmlDoc->getNodeValue(crDateExpr());
		std::string upDateStr = xmlDoc->getNodeValue(upDateExpr());
		std::string trDateStr = xmlDoc->getNodeValue(trDateExpr());
		
		crDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(crDateStr));
		upDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(upDateStr));
		trDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(trDateStr));
		
		int statusCount = xmlDoc->getNodeCount (statusCountExpr());
		statuses.clear();
	
		for (int i = 0; i < statusCount; i++)
		{
			std::string qry = ReceiveSE::replaceIndex (statusExpr(), i + 1);
			std::string reason = xmlDoc->getNodeValue (qry + STATUS_REASON_EXPR());
			std::string s = xmlDoc->getNodeValue (qry + STATUS_S_EXPR());
			std::string lang = xmlDoc->getNodeValue (qry + STATUS_LANG_EXPR());
			statuses.push_back (Status(s, reason, lang));
		}
	}
	catch (MalformedDateException& e)
	{
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
	catch (XPathExpressionException& e)
	{
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}
// End InfoResponse::fromXML()

std::string InfoResponse::toString() const 
{
    std::string retval = DataResponse::toString();
    retval += "(roid = " + roid + ")";
    return retval;
}
