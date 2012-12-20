#ifndef __AR_DOMAIN_UNRENEW_RESPONSE
#define __AR_DOMAIN_UNRENEW_RESPONSE

#include "se/DataResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

/**
 * Use this to access unrenew data for a domain as provided in an EPP domain
 * unrenew response compliant with AusRegistry Extensions to EPP and the domain
 * name mapping for such extensions.  Such a service element is sent by a
 * compliant EPP server in response to a valid domain unrenew command,
 * implemented by the ArDomainUnrenewCommand class.
 *
 * @see ArDomainUnrenewCommand
 */
class ArDomainUnrenewResponse : public DataResponse
{
public:
    ArDomainUnrenewResponse();

    const std::string& getName() { return name; }
    const XMLGregorianCalendar* getExpiryDate() { return exDate.get(); }
    void fromXML(XMLDocument* xmlDoc) throw (ParsingException);

private:
    static const std::string ARDOM_NAME_EXPR;
    static const std::string ARDOM_EX_DATE_EXPR;

    std::string name;
    std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __AU_DOMAIN_UNRENEW_RESPONSE
