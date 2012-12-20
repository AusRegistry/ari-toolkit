#ifndef __HOST_INFO_RESPONSE_HPP
#define __HOST_INFO_RESPONSE_HPP

#include "se/InfoResponse.hpp"
#include "se/InetAddress.hpp"

/**
 * Use this to access host object information as provided in an EPP host
 * info response compliant with RFCs 3730 and 3732.  Such a service element is
 * sent by a compliant EPP server in response to a valid host info command,
 * implemented by the HostInfoCommand class.
 *
 * @see HostInfoCommand
 */
class HostInfoResponse : public InfoResponse
{
public:
    HostInfoResponse ();

    const std::string& getName() const { return name; };
    const std::vector<InetAddress>& getAddresses() const { return addresses; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    const std::string & roidExpr() const { return HOS_ROID_EXPR; };
    const std::string & clIDExpr() const { return HOS_CL_ID_EXPR; };
    const std::string & crIDExpr() const { return HOS_CR_ID_EXPR; };
    const std::string & upIDExpr() const { return HOS_UP_ID_EXPR; };
    const std::string & crDateExpr() const { return HOS_CR_DATE_EXPR; };
    const std::string & upDateExpr() const { return HOS_UP_DATE_EXPR; };
    const std::string & trDateExpr() const { return HOS_TR_DATE_EXPR; };
    const std::string & statusExpr() const { return HOS_STATUS_EXPR; };
    const std::string & statusCountExpr() const { return HOS_STATUS_COUNT_EXPR; };
    
    static std::string exprReplace (const std::string &expr);
    
private:
    static const std::string HOS_ROID_EXPR,
                             HOS_CR_ID_EXPR,
                             HOS_UP_ID_EXPR,
                             HOS_CL_ID_EXPR,
                             HOS_CR_DATE_EXPR,
                             HOS_UP_DATE_EXPR,
                             HOS_TR_DATE_EXPR,
                             HOS_STATUS_COUNT_EXPR,
                             HOS_STATUS_EXPR,
                             HOS_INF_DATA_EXPR,
                             HOS_NAME_EXPR,
                             HOS_ADDR_EXPR,
                             HOS_ADDR_COUNT_EXPR,
                             HOS_ADDR_TXT_EXPR,
                             HOS_ADDR_IP_EXPR;
    std::string name;
    std::vector<InetAddress> addresses;
    
};
#endif // __HOST_INFO_RESPONSE_HPP
