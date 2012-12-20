#include <string>

#include "se/NotificationResponse.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/StandardCommandType.hpp"
#include "common/StringUtils.hpp"

using namespace std;

NotificationResponse::NotificationResponse (const ObjectType* objectType)
    : DataResponse(StandardCommandType::POLL(), objectType)
{
}

// BEGIN Class constants

const string NotificationResponse::IDENT()
{
    static string expr = "IDENT";
    return expr;
}

const string NotificationResponse::PAN_DATA_EXPR()
{
    static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:panData";

    return expr;
}

const string NotificationResponse::IDENT_EXPR()
{
    static string expr = NotificationResponse::PAN_DATA_EXPR()
        + "/OBJ:IDENT/text()";

    return expr;
}

const string NotificationResponse::RESULT_EXPR()
{
    static string expr = NotificationResponse::PAN_DATA_EXPR()
        + "/OBJ:IDENT/@paResult";

    return expr;
}

const string NotificationResponse::CLTRID_EXPR()
{
    static string expr = NotificationResponse::PAN_DATA_EXPR()
        + "/OBJ:paTRID/e:clTRID/text()";

    return expr;
}

const string NotificationResponse::SVTRID_EXPR()
{
    static string expr = NotificationResponse::PAN_DATA_EXPR()
        + "/OBJ:paTRID/e:svTRID/text()";

    return expr;
}

const string NotificationResponse::PADATE_EXPR()
{
    static string expr = NotificationResponse::PAN_DATA_EXPR()
        + "/OBJ:paDate/text()";

    return expr;
}

// END Class Constants


void NotificationResponse::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded()))
        return;

    try
    {
        identifier = xmlDoc->getNodeValue(identifierExpr());
        string resultStr = xmlDoc->getNodeValue(resultExpr());
        cltrid = xmlDoc->getNodeValue(cltridExpr());
        svtrid = xmlDoc->getNodeValue(svtridExpr());
        padateStr = xmlDoc->getNodeValue(padateExpr());

        result = (resultStr == "1");
        paDate = std::auto_ptr<XMLGregorianCalendar>(
                EPPDateFormatter::fromXSDateTime(padateStr));
    }
    catch (XPathExpressionException& e)
    {
        maintLogger->warning(e.getMessage());
        ParsingException pe;
        pe.causedBy(e);
        throw pe;
    }
}

string NotificationResponse::toString(void)
{
    string retval = DataResponse::toString();
    string identType = getObjType().getIdentType();

    retval += "(panData = ("
        + identType + " = " + identifier
        + ")(result = " + StringUtils::makeString(result)
        + ")(clTRID = " + cltrid
        + ")(svTRID = " + svtrid
        + ")(paDate = " + padateStr + "))";

    return retval;
}

