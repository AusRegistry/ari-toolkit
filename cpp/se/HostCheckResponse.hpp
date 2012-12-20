#ifndef __HOST_CHECK_RESPONSE_HPP
#define __HOST_CHECK_RESPONSE_HPP

#include "se/CheckResponse.hpp"

/**
 * Use this to access availability data for hosts as provided in an EPP host
 * check response compliant with RFCs 3730 and 3732.  Such a service element is
 * sent by a compliant EPP server in response to a valid host check command,
 * implemented by the HostCheckCommand class.
 *
 * @see HostCheckCommand
 */
class HostCheckResponse : public CheckResponse
{
public:
    HostCheckResponse();
    
protected:
    const std::string& chkDataCountExpr() const;
    const std::string& chkDataIndexExpr() const;
    const std::string& chkDataTextExpr() const;
    const std::string& chkDataAvailExpr() const;
    const std::string& chkDataReasonExpr() const;
    
private:

    static std::string exprReplace (const std::string &expr);
};


#endif // __HOST_CHECK_RESPONSE_HPP
