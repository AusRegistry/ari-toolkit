#ifndef __DOMAIN_CHECK_RESPONSE_HPP
#define __DOMAIN_CHECK_RESPONSE_HPP

#include "se/CheckResponse.hpp"

/**
 * Use this to access availability data for domains as provided in an EPP
 * domain check response compliant with RFCs 3730 and 3731.  Such a service
 * element is sent by a compliant EPP server in response to a valid domain
 * check command, implemented by the DomainCheckCommand class.
 *
 * @see DomainCheckCommand
 */
class DomainCheckResponse : public CheckResponse
{
public:
    DomainCheckResponse ();
    
protected:
    const std::string& chkDataCountExpr()  const { return DOM_CHKDATA_COUNT_EXPR(); };
    const std::string& chkDataIndexExpr()    const { return DOM_CHKDATA_IND_EXPR(); };
    const std::string& chkDataTextExpr()  const { return DOM_CHKDATA_IDENT_EXPR(); };
    const std::string& chkDataAvailExpr()  const { return DOM_CHKDATA_AVAIL_EXPR(); };
    const std::string& chkDataReasonExpr() const { return DOM_CHKDATA_REASON_EXPR(); };
                             
    static std::string exprReplace (const std::string &expr);

private:
    static const std::string& DOM_CHKDATA_COUNT_EXPR();
    static const std::string& DOM_CHKDATA_IND_EXPR();
    static const std::string& DOM_CHKDATA_IDENT_EXPR();
    static const std::string& DOM_CHKDATA_AVAIL_EXPR();
    static const std::string& DOM_CHKDATA_REASON_EXPR();
    
};
#endif // __DOMAIN_CHECK_RESPONSE_HPP
