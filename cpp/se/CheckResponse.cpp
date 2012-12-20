#include "se/CheckResponse.hpp"
#include "se/ItemNotFoundException.hpp"
#include "se/StandardCommandType.hpp"
#include "common/StringUtils.hpp"

using namespace std;

const string CheckResponse::CHKDATA_COUNT_EXPR()
{
	static string expr = "count(" + DataResponse::RES_DATA_EXPR() + "/OBJ:chkData/*)";
	return expr;
}
const string CheckResponse::CHKDATA_IND_EXPR()
{
	static string expr = DataResponse::RES_DATA_EXPR() + "/OBJ:chkData/OBJ:cd[IDX]";
	return expr;
}
const string CheckResponse::CHKDATA_IDENT_EXPR()
{
	static string expr = "/OBJ:IDENT/text()";
	return expr;
}
const string CheckResponse::CHKDATA_AVAIL_EXPR()
{
	static string expr = "/OBJ:IDENT/@avail";
	return expr;
}
const string CheckResponse::CHKDATA_REASON_EXPR()
{
	static string expr = "/OBJ:reason/text()";
	return expr;
}
    

CheckResponse::CheckResponse (const ObjectType* objectType)
    : DataResponse(StandardCommandType::CHECK(), objectType)
{
}

bool CheckResponse::isAvailable (const string &nameID) const
{
    map<string, Availability>::const_iterator p = availMap.find(nameID);
    if (p != availMap.end())
        return p->second.avail;
    else
        return false;
}

const string& CheckResponse::getReason (const string &nameID) const
{
    map<string, Availability>::const_iterator p = availMap.find(nameID);
    if (p != availMap.end())
        return p->second.reason;
    else
        throw ItemNotFoundException();
}

const string & CheckResponse::getReason (int index) const
{
    return reasonArray[index];
}

void CheckResponse::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        availArray.clear();
        reasonArray.clear();
        
        int cdCount = xmlDoc->getNodeCount(chkDataCountExpr());
        availArray.reserve(cdCount);
        reasonArray.reserve(cdCount);
        
        for (int i = 0; i < cdCount; i++)
        {
            string qry = replaceIndex(chkDataIndexExpr(), i + 1);
            string type = getObjType().getIdentType();
            string nameID = xmlDoc->getNodeValue(qry + chkDataTextExpr());
            string availStr = xmlDoc->getNodeValue(qry + chkDataAvailExpr());
            bool avail = (availStr == "1");
            availArray.push_back(avail);
            string reason = xmlDoc->getNodeValue(qry + chkDataReasonExpr());
            reasonArray.push_back(reason);

            availMap.insert(make_pair(nameID, Availability(avail, reason)));
        }
        // End for
        debugLogger->fine(toString());
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}


string CheckResponse::toString () const
{
    string retval = DataResponse::toString();
    
    map<string, Availability>::const_iterator p;
    
    for (p = availMap.begin(); p != availMap.end(); p++)
        retval += "(name = " + p->first + ")(available = " +
                  StringUtils::makeString(p->second.avail) + 
				  ")(reason = " + p->second.reason + ")";

    return retval;
}
