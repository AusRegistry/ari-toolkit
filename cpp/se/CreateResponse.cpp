#include "se/CreateResponse.hpp"
#include "se/StandardCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

const std::string CreateResponse::OBJ()
{
	static std::string expr = "OBJ";
	return expr;
}

const std::string CreateResponse::CRE_DATA_EXPR()
{
	static std::string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:creData";
	return expr;
}

const std::string CreateResponse::CR_DATE_EXPR()
{
	static std::string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:crDate/text()";
	return expr;
}

CreateResponse::CreateResponse(const ObjectType* objectType)
    : DataResponse(StandardCommandType::CREATE(), objectType)
{ }

void CreateResponse::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
		std::string crDateStr = xmlDoc->getNodeValue(crDateExpr());
        crDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(crDateStr));
    }
    catch (IllegalArgException& iae)
    {
		maintLogger->LOG_WARNING(iae.getMessage());
		throw ParsingException(iae.getMessage());
	}
}
