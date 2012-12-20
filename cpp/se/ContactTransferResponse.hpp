#ifndef __CONTACT_TRANSFER_RESPONSE_HPP
#define __CONTACT_TRANSFER_RESPONSE_HPP

#include "se/StandardObjectType.hpp"
#include "se/TransferResponse.hpp"

/**
 * Use this to access contact object transfer information as provided in an EPP
 * contact transfer response compliant with RFCs 3730 and 3733.  Such a service
 * element is sent by a compliant EPP server in response to a valid contact
 * transfer command, implemented by a subclass of the ContactTransferCommand
 * class.
 *
 * @see ContactTransferCommand
 * @see ContactTransferRequestCommand
 * @see ContactTransferApproveCommand
 * @see ContactTransferCancelCommand
 * @see ContactTransferRejectCommand
 * @see ContactTransferQueryCommand
 */
class ContactTransferResponse : public TransferResponse
{
public:
    ContactTransferResponse() 
		: TransferResponse(StandardObjectType::CONTACT())
	{ }
    
    const std::string & getID() const { return id; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

protected:
    static std::string exprReplace (const std::string &expr);
    
    const std::string& trStatusExpr() const { return CON_TR_STATUS_EXPR; };
    const std::string& reIDExpr()     const { return CON_REID_EXPR; };
    const std::string& reDateExpr()   const { return CON_REDATE_EXPR; };
    const std::string& acIDExpr()     const { return CON_ACID_EXPR; };
    const std::string& acDateExpr()   const { return CON_ACDATE_EXPR; };

private:
    static const std::string CON_ID_EXPR,
							 CON_TR_STATUS_EXPR,
							 CON_REID_EXPR,
							 CON_REDATE_EXPR,
							 CON_ACID_EXPR,
							 CON_ACDATE_EXPR;
    std::string id;
};

#endif // __CONTACT_TRANSFER_RESPONSE_HPP
