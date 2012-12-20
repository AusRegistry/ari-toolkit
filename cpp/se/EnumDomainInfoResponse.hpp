#ifndef __ENUM_DOMAIN_INFO_RESPONSE_HPP
#define __ENUM_DOMAIN_INFO_RESPONSE_HPP

#include "se/DomainInfoResponse.hpp"
#include "se/NAPTR.hpp"

class EnumDomainInfoResponse : public DomainInfoResponse
{
public:
    EnumDomainInfoResponse();
    
    const std::vector<NAPTR> & getNAPTRs() const { return naptrs; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    static std::string exprReplace (const std::string &expr);
    
private:
    static const std::string E164_INF_DATA_EXPR,
                             NAPTR_COUNT_EXPR,
                             NAPTR_IND_EXPR,
                             NAPTR_ORDER_EXPR,
                             NAPTR_PREF_EXPR,
                             NAPTR_FLAGS_EXPR,
                             NAPTR_SVC_EXPR,
                             NAPTR_REGEX_EXPR,
                             NAPTR_REPL_EXPR;
    std::vector<NAPTR> naptrs;
};

#endif // __ENUM_DOMAIN_INFO_RESPONSE_HPP
