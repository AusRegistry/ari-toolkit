#include "se/DomainTransferResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "se/EPPDateFormatter.hpp"
#include "common/StringUtils.hpp"

const std::string DomainTransferResponse::DOM_NAME_EXPR
    (DataResponse::RES_DATA_EXPR() + "/domain:trnData/domain:name/text()");

const std::string DomainTransferResponse::DOM_EXDATE_EXPR
    (DataResponse::RES_DATA_EXPR() + "/domain:trnData/domain:exDate/text()");

const std::string DomainTransferResponse::DOM_TR_STATUS_EXPR
    (DomainTransferResponse::exprReplace
        (TransferResponse::TR_STATUS_EXPR()));
        
const std::string DomainTransferResponse::DOM_REID_EXPR
    (DomainTransferResponse::exprReplace
        (TransferResponse::REID_EXPR()));
        
const std::string DomainTransferResponse::DOM_REDATE_EXPR
    (DomainTransferResponse::exprReplace
        (TransferResponse::REDATE_EXPR()));
        
const std::string DomainTransferResponse::DOM_ACID_EXPR
    (DomainTransferResponse::exprReplace
        (TransferResponse::ACID_EXPR()));
        
const std::string DomainTransferResponse::DOM_ACDATE_EXPR
    (DomainTransferResponse::exprReplace
        (TransferResponse::ACDATE_EXPR()));


DomainTransferResponse::DomainTransferResponse()
	: TransferResponse(StandardObjectType::DOMAIN())
{
}

void DomainTransferResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    TransferResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        name = xmlDoc->getNodeValue(DOM_NAME_EXPR);
        std::string exDateStr = xmlDoc->getNodeValue(DOM_EXDATE_EXPR);
        if (exDateStr.length() > 0)
            exDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(exDateStr));
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}


std::string DomainTransferResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    TransferResponse::OBJ(),
                                    StandardObjectType::DOMAIN()->getName());
}
