#ifndef __CONTACT_INFO_RESPONSE_HPP
#define __CONTACT_INFO_RESPONSE_HPP

#include "se/InfoResponse.hpp"
class IntPostalInfo;
class LocalPostalInfo;
class DiscloseItem;

#include "common/EPPException.hpp"

class NoDiscloseItemsException : public EPPException
{
public:
	NoDiscloseItemsException()
		: EPPException("NoDiscloseItemsException") { }
	EPP_EXCEPTION(NoDiscloseItemsException);
};

class EmptyPostalInfoException : public EPPException
{
public:
	EmptyPostalInfoException()
		: EPPException("EmptyPostalInfoException") { }
	EPP_EXCEPTION(EmptyPostalInfoException);
};

/**
 * Use this to access contact object information as provided in an EPP contact
 * info response compliant with RFCs 3730 and 3733.  Such a service element is
 * sent by a compliant EPP server in response to a valid contact info command,
 * implemented by the ContactInfoCommand class.
 *
 * @see ContactInfoCommand
 */
class ContactInfoResponse : public InfoResponse
{
public:
    ContactInfoResponse ();
	virtual ~ContactInfoResponse();
    
    const std::string & getID() const { return id; };
    const IntPostalInfo & getIntPostalInfo() const 
			throw (EmptyPostalInfoException);
    const LocalPostalInfo & getLocPostalInfo() const
			throw (EmptyPostalInfoException);
    const std::string & getVoice() const { return voice; };
    int getVoiceExtension() const { return voiceX; };
    const std::string & getFax() const { return fax; };
    int getFaxExtension() const { return faxX; };
    const std::string & getEmail() const { return email; };
    const std::string & getPassword() const { return pw; };
    const std::vector<DiscloseItem> & getDiscloseItems() const { return items; };
    
    bool isDisclosed() const throw (NoDiscloseItemsException);
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);


protected:
    const std::string & roidExpr()   const { return CON_ROID_EXPR; };
    const std::string & crIDExpr()   const { return CON_CR_ID_EXPR; };
    const std::string & upIDExpr()   const { return CON_UP_ID_EXPR; };
    const std::string & clIDExpr()   const { return CON_CL_ID_EXPR; };
    const std::string & crDateExpr() const { return CON_CR_DATE_EXPR; };
    const std::string & upDateExpr() const { return CON_UP_DATE_EXPR; };
    const std::string & trDateExpr() const { return CON_TR_DATE_EXPR; };
    const std::string & statusExpr() const { return CON_STATUS_EXPR; };
    const std::string & statusCountExpr() const { return CON_STATUS_COUNT_EXPR; };
    
    static std::string exprReplace (const std::string &expr);


private:
    static const std::string CON_ROID_EXPR,
                             CON_CR_ID_EXPR,
                             CON_UP_ID_EXPR,
                             CON_CL_ID_EXPR,
                             CON_CR_DATE_EXPR,
                             CON_UP_DATE_EXPR,
                             CON_TR_DATE_EXPR,
                             CON_STATUS_COUNT_EXPR,
                             CON_STATUS_EXPR,
                             CON_INF_DATA_EXPR,
                             CON_ID_EXPR,
                             CON_PW_EXPR,
                             CON_PINFO_INT_EXPR,
                             CON_PINFO_INT_NAME_EXPR,
                             CON_PINFO_INT_ORG_EXPR,
                             CON_PINFO_INT_STREET_EXPR,
                             CON_PINFO_INT_CITY_EXPR,
                             CON_PINFO_INT_SP_EXPR,
                             CON_PINFO_INT_PC_EXPR,
                             CON_PINFO_INT_CC_EXPR,
                             CON_PINFO_LOC_EXPR,
                             CON_PINFO_LOC_NAME_EXPR,
                             CON_PINFO_LOC_ORG_EXPR,
                             CON_PINFO_LOC_STREET_EXPR,
                             CON_PINFO_LOC_CITY_EXPR,
                             CON_PINFO_LOC_SP_EXPR,
                             CON_PINFO_LOC_PC_EXPR,
                             CON_PINFO_LOC_CC_EXPR,
                             CON_VOICE_EXPR,
                             CON_VOICEX_EXPR,
                             CON_FAX_EXPR,
                             CON_FAXX_EXPR,
                             CON_EMAIL_EXPR,
                             CON_DISCLOSE_EXPR,
                             CON_DISCLOSE_COUNT_EXPR,
                             CON_DISCLOSE_FLAG_EXPR,
                             CON_DISCLOSE_CHILD_EXPR,
                             CON_DISCLOSE_NAME_EXPR,
                             CON_DISCLOSE_TYPE_EXPR;

    std::string id, voice, fax, email, pw;
    IntPostalInfo *intPostalInfo;
    LocalPostalInfo *locPostalInfo;
    int voiceX, faxX;
    bool discloseFlag;
    std::vector<DiscloseItem> items;
};

#endif // __CONTACT_INFO_RESPONSE_HPP
