#include "se/ContactTransferResponse.hpp"
#include "common/StringUtils.hpp"

const std::string ContactTransferResponse::CON_ID_EXPR
    (DataResponse::RES_DATA_EXPR() + "/contact:trnData/contact:id/text()");

const std::string ContactTransferResponse::CON_TR_STATUS_EXPR
    (ContactTransferResponse::exprReplace
        (TransferResponse::TR_STATUS_EXPR()));
        
const std::string ContactTransferResponse::CON_REID_EXPR
    (ContactTransferResponse::exprReplace
        (TransferResponse::REID_EXPR()));
        
const std::string ContactTransferResponse::CON_REDATE_EXPR
    (ContactTransferResponse::exprReplace
        (TransferResponse::REDATE_EXPR()));
        
const std::string ContactTransferResponse::CON_ACID_EXPR
    (ContactTransferResponse::exprReplace
        (TransferResponse::ACID_EXPR()));
        
const std::string ContactTransferResponse::CON_ACDATE_EXPR
    (ContactTransferResponse::exprReplace
        (TransferResponse::ACDATE_EXPR()));


void ContactTransferResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    TransferResponse::fromXML(xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        id = xmlDoc->getNodeValue(CON_ID_EXPR);
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}


std::string ContactTransferResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    TransferResponse::OBJ(),
                                    StandardObjectType::CONTACT()->getName());
}
