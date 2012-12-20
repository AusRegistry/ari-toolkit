#ifndef __HOST_CREATE_RESPONSE_HPP
#define __HOST_CREATE_RESPONSE_HPP

#include "se/CreateResponse.hpp"

/**
 * Use this to access create data for a host as provided in an EPP host create
 * response compliant with RFCs 3730 and 3732.  Such a service element is sent
 * by a compliant EPP server in response to a valid host create command,
 * implemented by the HostCreateCommand.
 *
 * @see HostCreateCommand
 */
class HostCreateResponse : public CreateResponse
{
public:
    HostCreateResponse();
    
    const std::string & getName() const { return name; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    const std::string & crDateExpr() const { return HOS_CR_DATE_EXPR; };
    
    static std::string exprReplace (const std::string &expr);
private:
    static const std::string HOS_CR_DATE_EXPR,
                             HOS_NAME_EXPR;
    std::string name;
};
#endif // __HOST_CREATE_RESPONSE_HPP
