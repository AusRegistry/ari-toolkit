#ifndef __DOMAIN_TRANSFER_RESPONSE_HPP
#define __DOMAIN_TRANSFER_RESPONSE_HPP

#include "se/TransferResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include <memory>

/**
 * Use this to access domain object transfer information as provided in an EPP
 * domain transfer response compliant with RFCs 3730 and 3731.  Such a service
 * element is sent by a compliant EPP server in response to a valid domain
 * transfer command, implemented by a subclass of the DomainTransferCommand
 * class.
 *
 * @see DomainTransferCommand
 * @see DomainTransferRequestCommand
 * @see DomainTransferApproveCommand
 * @see DomainTransferCancelCommand
 * @see DomainTransferRejectCommand
 * @see DomainTransferQueryCommand
 */
class DomainTransferResponse : public TransferResponse
{
public:
	DomainTransferResponse();

    const std::string & getName() const { return name; };
    const XMLGregorianCalendar* getExpiryDate()  const { return exDate.get(); };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    static std::string exprReplace (const std::string &expr);
    
    const std::string & trStatusExpr() const { return DOM_TR_STATUS_EXPR; };
    const std::string & reIDExpr()     const { return DOM_REID_EXPR; };
    const std::string & reDateExpr()   const { return DOM_REDATE_EXPR; };
    const std::string & acIDExpr()     const { return DOM_ACID_EXPR; };
    const std::string & acDateExpr()   const { return DOM_ACDATE_EXPR; };

private:
    static const std::string DOM_NAME_EXPR,
                             DOM_EXDATE_EXPR,
                             DOM_TR_STATUS_EXPR,
                             DOM_REID_EXPR,
                             DOM_REDATE_EXPR,
                             DOM_ACID_EXPR,
                             DOM_ACDATE_EXPR;
	std::auto_ptr<XMLGregorianCalendar> exDate;
    std::string name;
};

#endif // __DOMAIN_TRANSFER_RESPONSE_HPP
