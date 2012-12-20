#ifndef __DOMAIN_CREATE_RESPONSE_HPP
#define __DOMAIN_CREATE_RESPONSE_HPP

#include "se/CreateResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

/**
 * Use this to access create data for a domain as provided in an EPP domain
 * create response compliant with RFCs 3730 and 3731.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain create
 * command, implemented by the DomainCreateCommand.
 *
 * @see DomainCreateCommand
 */
class DomainCreateResponse : public CreateResponse
{
public:
    DomainCreateResponse();

    const std::string & getName() const { return name; };
    const XMLGregorianCalendar* getExpiryDate() const { return exDate.get(); };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    const std::string & crDateExpr() const { return DOM_CR_DATE_EXPR; };
    
    static std::string exprReplace (const std::string &expr);
    
private:
    static const std::string DOM_CR_DATE_EXPR,
                             DOM_NAME_EXPR,
                             DOM_EX_DATE_EXPR;
    std::string name;
	std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __DOMAIN_CREATE_RESPONSE_HPP
