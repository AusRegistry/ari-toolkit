#include "se/TransferResponse.hpp"
#include "se/StandardCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

using namespace std;

const string TransferResponse::OBJ()
{
	static string expr = "OBJ";
	return expr;
}

const string TransferResponse::TR_STATUS_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:trnData/OBJ:trStatus/text()";
	return expr;
}

const string TransferResponse::REID_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:trnData/OBJ:reID/text()";
	return expr;
}

const string TransferResponse::REDATE_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:trnData/OBJ:reDate/text()";
	return expr;
}

const string TransferResponse::ACID_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:trnData/OBJ:acID/text()";
	return expr;
}

const string TransferResponse::ACDATE_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:trnData/OBJ:acDate/text()";
	return expr;
}

TransferResponse::TransferResponse (const ObjectType* objectType)
    : DataResponse(StandardCommandType::TRANSFER(), objectType)
{
}

void TransferResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        trStatus = xmlDoc->getNodeValue (trStatusExpr());
        
        reID = xmlDoc->getNodeValue (reIDExpr());
        string reDateStr = xmlDoc->getNodeValue(reDateExpr());
        if (reDateStr.length() > 0)
            reDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(reDateStr));
        
        acID = xmlDoc->getNodeValue(acIDExpr());
        string acDateStr = xmlDoc->getNodeValue (acDateExpr());
        if (acDateStr.length() > 0)
            acDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(acDateStr));
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}
