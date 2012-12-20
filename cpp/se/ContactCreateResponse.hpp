#ifndef __CONTACT_CREATE_RESPONSE_HPP
#define __CONTACT_CREATE_RESPONSE_HPP

#include "se/CreateResponse.hpp"

/**
 * Use this to access create data for a contact as provided in an EPP contact
 * create response compliant with RFCs 3730 and 3733.  Such a service element
 * is sent by a compliant EPP server in response to a valid contact create
 * command, implemented by the ContactCreateCommand.
 *
 * @see ContactCreateCommand
 */
class ContactCreateResponse : public CreateResponse
{
public:
    ContactCreateResponse();
    
    const std::string & getID() const { return id; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

protected:
    const std::string & crDateExpr() const { return CON_CR_DATE_EXPR; };
    
    static std::string exprReplace (const std::string &expr);
    
private:
    static const std::string CON_CR_DATE_EXPR,
                             CON_ID_EXPR;
    
    std::string id;
};

#endif // __CONTACT_CREATE_RESPONSE_HPP
