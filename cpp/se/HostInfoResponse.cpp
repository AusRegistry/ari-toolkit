#include "se/HostInfoResponse.hpp"
#include "se/StandardObjectType.hpp"

const std::string HostInfoResponse::HOS_ROID_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::ROID_EXPR()));
    
const std::string HostInfoResponse::HOS_CR_ID_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::CR_ID_EXPR()));
    
const std::string HostInfoResponse::HOS_UP_ID_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::UP_ID_EXPR()));
    
const std::string HostInfoResponse::HOS_CL_ID_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::CL_ID_EXPR()));
    
const std::string HostInfoResponse::HOS_CR_DATE_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::CR_DATE_EXPR()));
    
const std::string HostInfoResponse::HOS_UP_DATE_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::UP_DATE_EXPR()));
    
const std::string HostInfoResponse::HOS_TR_DATE_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::TR_DATE_EXPR()));
    
const std::string HostInfoResponse::HOS_STATUS_COUNT_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::STATUS_COUNT_EXPR()));
    
const std::string HostInfoResponse::HOS_STATUS_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::STATUS_EXPR()));
    
const std::string HostInfoResponse::HOS_INF_DATA_EXPR
    (HostInfoResponse::exprReplace (InfoResponse::INF_DATA_EXPR()));
    
const std::string HostInfoResponse::HOS_NAME_EXPR
    (HostInfoResponse::HOS_INF_DATA_EXPR + "/host:name/text()");

const std::string HostInfoResponse::HOS_ADDR_EXPR
    (HostInfoResponse::HOS_INF_DATA_EXPR + "/host:addr[IDX]");

const std::string HostInfoResponse::HOS_ADDR_COUNT_EXPR
    ("count(" + HostInfoResponse::HOS_INF_DATA_EXPR + "/host:addr)");

const std::string HostInfoResponse::HOS_ADDR_TXT_EXPR ("/text()");

const std::string HostInfoResponse::HOS_ADDR_IP_EXPR ("/@ip");


HostInfoResponse::HostInfoResponse()
    : InfoResponse(StandardObjectType::HOST())
{ }


void HostInfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    debugLogger->LOG_FINEST("enter");
    InfoResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        name = xmlDoc->getNodeValue(HOS_NAME_EXPR);
        
        int addrCount = xmlDoc->getNodeCount (HOS_ADDR_COUNT_EXPR);
        addresses.clear();
        
        for (int i = 0; i < addrCount; i++)
        {
            std::string qry = ReceiveSE::replaceIndex(HOS_ADDR_EXPR, i+1);
            std::string addr = xmlDoc->getNodeValue(qry + HOS_ADDR_TXT_EXPR);
            std::string version = xmlDoc->getNodeValue(qry + HOS_ADDR_IP_EXPR);
            addresses.push_back(InetAddress (addr, IPVersion::value(version)));
        }
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
    debugLogger->LOG_FINEST("exit");
}

std::string HostInfoResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    DataResponse::OBJ(),
                                    StandardObjectType::HOST()->getName());
}
