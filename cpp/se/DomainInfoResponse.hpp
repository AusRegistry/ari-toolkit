#ifndef __DOMAIN_INFO_RESPONSE_HPP
#define __DOMAIN_INFO_RESPONSE_HPP

#include "se/InfoResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include "xml/XMLDocument.hpp"

#include <string>
#include <vector>

/**
 * Use this to access domain object information as provided in an EPP domain
 * info response compliant with RFCs 3730 and 3731.  Such a service element is
 * sent by a compliant EPP server in response to a valid domain info command,
 * implemented by the DomainInfoCommand class.
 *
 * @see DomainInfoCommand
 */     
class DomainInfoResponse : public InfoResponse
{
public:
    DomainInfoResponse();
    
    const std::string & getName() const { return name; };
    const std::string & getPW()   const { return pw; };
    const XMLGregorianCalendar* getExpireDate()  const { return exDate.get(); };
    const std::string & getRegistrantID() const { return registrantID; };
    const std::vector<std::string> & getTechContacts() const { return techContacts; };
    const std::vector<std::string> & getAdminContacts() const { return adminContacts; };
    const std::vector<std::string> & getBillingContacts() const { return billingContacts; };
    const std::vector<std::string> & getNameservers() const { return delHosts; };
    const std::vector<std::string> & getSubordinateHosts() const { return subHosts; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    virtual std::string toString() const;
    
protected:
    static const std::string &DOM_ROID_EXPR(),
                             &DOM_CR_ID_EXPR(),
                             &DOM_UP_ID_EXPR(),
                             &DOM_CL_ID_EXPR(),
                             &DOM_CR_DATE_EXPR(),
                             &DOM_UP_DATE_EXPR(),
                             &DOM_TR_DATE_EXPR(),
                             &DOM_STATUS_COUNT_EXPR(),
                             &DOM_STATUS_EXPR(),
                             &DOM_INF_DATA_EXPR(),
                             &DOM_NAME_EXPR(),
                             &DOM_PW_EXPR(),
                             &DOM_REGISTRANT_EXPR(),
                             &DOM_EX_DATE_EXPR(),
                             &DOM_NS_EXPR(),
                             &DOM_HOST_EXPR(),
                             &DOM_CON_EXPR(),
                             &DOM_CON_TECH_EXPR(),
                             &DOM_CON_ADMIN_EXPR(),
                             &DOM_CON_BILLING_EXPR();

    const std::string& roidExpr() const { return DOM_ROID_EXPR(); };
    const std::string& crIDExpr() const { return DOM_CR_ID_EXPR(); };
    const std::string& upIDExpr() const { return DOM_UP_ID_EXPR(); };
    const std::string& clIDExpr() const { return DOM_CL_ID_EXPR(); };
    const std::string& crDateExpr() const { return DOM_CR_DATE_EXPR(); };
    const std::string& upDateExpr() const { return DOM_UP_DATE_EXPR(); };
    const std::string& trDateExpr() const { return DOM_TR_DATE_EXPR(); };
    const std::string& statusExpr() const { return DOM_STATUS_EXPR(); };
    const std::string& statusCountExpr() const { return DOM_STATUS_COUNT_EXPR(); };
    
    static std::string exprReplace (const std::string &expr);
    
private:
    std::string name, pw, registrantID;
    std::vector<std::string> techContacts, adminContacts, billingContacts,
                             delHosts, subHosts;
	std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __DOMAIN_INFO_RESPONSE_HPP
