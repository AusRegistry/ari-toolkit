#ifndef __CONTACT_CHECK_RESPONSE_HPP
#define __CONTACT_CHECK_RESPONSE_HPP

#include "se/CheckResponse.hpp"

/**
 * Use this to access availability data for contacts as provided in an EPP
 * contact check response compliant with RFCs 3730 and 3733.  Such a service
 * element is sent by a compliant EPP server in response to a valid contact
 * check command, implemented by the ContactCheckCommand class.
 *
 * @see ContactCheckCommand
 */
class ContactCheckResponse : public CheckResponse
{
public:
    ContactCheckResponse();
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
protected:
    static const std::string &CON_CHKDATA_COUNT_EXPR(),
                             &CON_CHKDATA_IND_EXPR(),
                             &CON_CHKDATA_IDENT_EXPR(),
                             &CON_CHKDATA_AVAIL_EXPR(),
                             &CON_CHKDATA_REASON_EXPR();

    const std::string& chkDataCountExpr() const
	{
		return CON_CHKDATA_COUNT_EXPR();
	}
    const std::string& chkDataIndexExpr() const
	{
		return CON_CHKDATA_IND_EXPR();
	}
    const std::string& chkDataTextExpr() const
	{
		return CON_CHKDATA_IDENT_EXPR();
	}
    const std::string& chkDataAvailExpr() const
	{
		return CON_CHKDATA_AVAIL_EXPR();
	}
    const std::string& chkDataReasonExpr() const
	{
		return CON_CHKDATA_REASON_EXPR();
	}
    static std::string exprReplace (const std::string &expr);
};
#endif // __CONTACT_CHECK_RESPONSE_HPP
