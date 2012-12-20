#ifndef __DOMAIN_RENEW_RESPONSE_HPP
#define __DOMAIN_RENEW_RESPONSE_HPP

#include "se/DataResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include <memory>

/**
 * Use this to access domain object renewal response data as provided in an EPP
 * domain renew response compliant with RFCs 3730 and 3731.  Such a service
 * element is sent by a compliant EPP server in response to a valid domain renew
 * command, implemented by the DomainRenewCommand class.
 *
 * @see DomainRenewCommand
 */
class DomainRenewResponse : public DataResponse
{
public:
    DomainRenewResponse ();
    
    const std::string & getName() const { return name; };
    const XMLGregorianCalendar* getExpiryDate() const { return exDate.get(); };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

private:
    static const std::string DOM_NAME_EXPR,
                             DOM_EX_DATE_EXPR;
    std::string name;
	std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __DOMAIN_RENEW_RESPONSE_HPP
