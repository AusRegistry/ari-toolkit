#include "se/ContactInfoResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "se/IntPostalInfo.hpp"
#include "se/LocalPostalInfo.hpp"
#include "se/DiscloseItem.hpp"

const std::string ContactInfoResponse::CON_ROID_EXPR
    (exprReplace(InfoResponse::ROID_EXPR()));

const std::string ContactInfoResponse::CON_CR_ID_EXPR
    (exprReplace(InfoResponse::CR_ID_EXPR()));

const std::string ContactInfoResponse::CON_UP_ID_EXPR
    (exprReplace(InfoResponse::UP_ID_EXPR()));

const std::string ContactInfoResponse::CON_CL_ID_EXPR
    (exprReplace(InfoResponse::CL_ID_EXPR()));

const std::string ContactInfoResponse::CON_CR_DATE_EXPR
    (exprReplace(InfoResponse::CR_DATE_EXPR()));

const std::string ContactInfoResponse::CON_UP_DATE_EXPR
    (exprReplace(InfoResponse::UP_DATE_EXPR()));

const std::string ContactInfoResponse::CON_TR_DATE_EXPR
    (exprReplace(InfoResponse::TR_DATE_EXPR()));

const std::string ContactInfoResponse::CON_STATUS_COUNT_EXPR
    (exprReplace(InfoResponse::STATUS_COUNT_EXPR()));

const std::string ContactInfoResponse::CON_STATUS_EXPR
    (exprReplace(InfoResponse::STATUS_EXPR()));

const std::string ContactInfoResponse::CON_INF_DATA_EXPR
    (exprReplace(InfoResponse::INF_DATA_EXPR()));

const std::string ContactInfoResponse::CON_ID_EXPR
    (CON_INF_DATA_EXPR + "/contact:id/text()");

const std::string ContactInfoResponse::CON_PW_EXPR
    (CON_INF_DATA_EXPR + "/contact:authInfo/contact:pw/text()");

const std::string ContactInfoResponse::CON_PINFO_INT_EXPR
    (CON_INF_DATA_EXPR + "/contact:postalInfo[@type='int']");

const std::string ContactInfoResponse::CON_PINFO_INT_NAME_EXPR
    (CON_PINFO_INT_EXPR + "/contact:name/text()");
    
const std::string ContactInfoResponse::CON_PINFO_INT_ORG_EXPR
    (CON_PINFO_INT_EXPR + "/contact:org/text()");
    
const std::string ContactInfoResponse::CON_PINFO_INT_STREET_EXPR
    (CON_PINFO_INT_EXPR + "/contact:addr/contact:street");
    
const std::string ContactInfoResponse::CON_PINFO_INT_CITY_EXPR
    (CON_PINFO_INT_EXPR + "/contact:addr/contact:city/text()");
    
const std::string ContactInfoResponse::CON_PINFO_INT_SP_EXPR
    (CON_PINFO_INT_EXPR + "/contact:addr/contact:sp/text()");
    
const std::string ContactInfoResponse::CON_PINFO_INT_PC_EXPR
    (CON_PINFO_INT_EXPR + "/contact:addr/contact:pc/text()");
    
const std::string ContactInfoResponse::CON_PINFO_INT_CC_EXPR
    (CON_PINFO_INT_EXPR + "/contact:addr/contact:cc/text()");
    
const std::string ContactInfoResponse::CON_PINFO_LOC_EXPR
    (CON_INF_DATA_EXPR + "/contact:postalInfo[@type='loc']");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_NAME_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:name/text()");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_ORG_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:org/text()");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_STREET_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:addr/contact:street");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_CITY_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:addr/contact:city/text()");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_SP_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:addr/contact:sp/text()");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_PC_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:addr/contact:pc/text()");
        
const std::string ContactInfoResponse::CON_PINFO_LOC_CC_EXPR
    (CON_PINFO_LOC_EXPR + "/contact:addr/contact:cc/text()");
        
const std::string ContactInfoResponse::CON_VOICE_EXPR
    (CON_INF_DATA_EXPR + "/contact:voice/text()");

const std::string ContactInfoResponse::CON_VOICEX_EXPR
    (CON_INF_DATA_EXPR + "/contact:voice/@x");

const std::string ContactInfoResponse::CON_FAX_EXPR
    (CON_INF_DATA_EXPR + "/contact:fax/text()");

const std::string ContactInfoResponse::CON_FAXX_EXPR
    (CON_INF_DATA_EXPR + "/contact:fax/@x");

const std::string ContactInfoResponse::CON_EMAIL_EXPR
    (CON_INF_DATA_EXPR + "/contact:email/text()");

const std::string ContactInfoResponse::CON_DISCLOSE_EXPR
    (CON_INF_DATA_EXPR + "/contact:disclose");

const std::string ContactInfoResponse::CON_DISCLOSE_COUNT_EXPR
    ("count(" + CON_DISCLOSE_EXPR + "/*)");

const std::string ContactInfoResponse::CON_DISCLOSE_FLAG_EXPR
    (CON_DISCLOSE_EXPR + "/@flag");
    
const std::string ContactInfoResponse::CON_DISCLOSE_CHILD_EXPR
    (CON_DISCLOSE_EXPR + "/*[IDX]");
    
const std::string ContactInfoResponse::CON_DISCLOSE_NAME_EXPR ("/local-name()");
const std::string ContactInfoResponse::CON_DISCLOSE_TYPE_EXPR ("/@type");

ContactInfoResponse::ContactInfoResponse()
    : InfoResponse (StandardObjectType::CONTACT()), 
	  intPostalInfo(NULL),
	  locPostalInfo(NULL),
      voiceX(-1), 
      faxX(-1)
{ }

ContactInfoResponse::~ContactInfoResponse()
{
	if (intPostalInfo)
		delete intPostalInfo;
	if (locPostalInfo)
		delete locPostalInfo;
}

const LocalPostalInfo & ContactInfoResponse::getLocPostalInfo() const
	throw (EmptyPostalInfoException)
{
	if (locPostalInfo)
		return *locPostalInfo;
	else
		throw EmptyPostalInfoException();
}

const IntPostalInfo & ContactInfoResponse::getIntPostalInfo() const
	throw (EmptyPostalInfoException)
{
	if (intPostalInfo)
		return *intPostalInfo;
	else
		throw EmptyPostalInfoException();
}

bool ContactInfoResponse::isDisclosed() const 
	throw (NoDiscloseItemsException)
{
    if (items.size() > 0)
        return discloseFlag;
    else
        throw NoDiscloseItemsException();
}
    
void ContactInfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    debugLogger->LOG_FINEST("enter");
    
    InfoResponse::fromXML (xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        id = xmlDoc->getNodeValue (CON_ID_EXPR);
        
        std::string iName = xmlDoc->getNodeValue (CON_PINFO_INT_NAME_EXPR);
        std::string iOrg  = xmlDoc->getNodeValue (CON_PINFO_INT_ORG_EXPR);
        std::vector<std::string> iStreet = 
            xmlDoc->getNodeValues (CON_PINFO_INT_STREET_EXPR);
        std::string iCity = xmlDoc->getNodeValue (CON_PINFO_INT_CITY_EXPR);
        std::string iSP   = xmlDoc->getNodeValue (CON_PINFO_INT_SP_EXPR);
        std::string iPC   = xmlDoc->getNodeValue (CON_PINFO_INT_PC_EXPR);
        std::string iCC   = xmlDoc->getNodeValue (CON_PINFO_INT_CC_EXPR);
        
        intPostalInfo = 
			new IntPostalInfo(iName, iOrg, iStreet, iCity, iSP, iPC, iCC);
        
        std::string lName = xmlDoc->getNodeValue (CON_PINFO_LOC_NAME_EXPR);
        
        if (lName.length() > 0)
        {
            std::string lOrg = xmlDoc->getNodeValue (CON_PINFO_LOC_ORG_EXPR);
            std::vector<std::string> lStreet = 
                xmlDoc->getNodeValues(CON_PINFO_LOC_STREET_EXPR);
            std::string lCity = xmlDoc->getNodeValue(CON_PINFO_LOC_CITY_EXPR);
            std::string lSP = xmlDoc->getNodeValue(CON_PINFO_LOC_SP_EXPR);
            std::string lPC = xmlDoc->getNodeValue(CON_PINFO_LOC_PC_EXPR);
            std::string lCC = xmlDoc->getNodeValue(CON_PINFO_LOC_CC_EXPR);
            
            locPostalInfo = 
                new LocalPostalInfo(lName, lOrg, lStreet, lCity, lSP, lPC, lCC);
        }
        
        voice = xmlDoc->getNodeValue(CON_VOICE_EXPR);
        std::string voiceXStr = xmlDoc->getNodeValue(CON_VOICEX_EXPR);
        if (voiceXStr.length() > 0)
            voiceX = atoi(voiceXStr.c_str());
        
        fax = xmlDoc->getNodeValue (CON_FAX_EXPR);
        std::string faxXStr = xmlDoc->getNodeValue (CON_FAXX_EXPR);
        if (faxXStr.length() > 0)
            faxX = atoi(faxXStr.c_str());
        
        email = xmlDoc->getNodeValue (CON_EMAIL_EXPR);
        pw = xmlDoc->getNodeValue (CON_PW_EXPR);
        std::string flagStr = xmlDoc->getNodeValue (CON_DISCLOSE_FLAG_EXPR);
        discloseFlag = (flagStr == "1") ? true : false;
        
        int count = xmlDoc->getNodeCount (CON_DISCLOSE_COUNT_EXPR);
        for (int i = 0; i < count; i++)
        {
            std::string qry = ReceiveSE::replaceIndex (CON_DISCLOSE_CHILD_EXPR, i+1);
            std::string childName =
                xmlDoc->getNodeValue (qry + CON_DISCLOSE_NAME_EXPR);
            std::string childType =
                xmlDoc->getNodeValue (qry + CON_DISCLOSE_TYPE_EXPR);
            
            if (childType.length() == 0)
                items.push_back (DiscloseItem(childName));
            else
                items.push_back (DiscloseItem(childName, childType));
        }
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException p;
		p.causedBy(e);
		throw p;
	}
    debugLogger->LOG_FINEST("exit");
}


std::string ContactInfoResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    DataResponse::OBJ(),
                                    StandardObjectType::CONTACT()->getName());
}    
    
