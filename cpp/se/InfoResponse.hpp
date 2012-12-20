#ifndef __INFORESPONSE_HPP
#define __INFORESPONSE_HPP

#include "se/DataResponse.hpp"
#include "se/Status.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include <string>
#include <vector>
#include <memory>

/**
 *	Use this to retrieve the values of attributes common to all EPP info response
 *	service elements.
 */
class InfoResponse : public DataResponse
{
public:
    InfoResponse (const ObjectType* objectType);
        
    const std::string & getROID() const { return roid; };
    const XMLGregorianCalendar* getCreateDate() const { return crDate.get(); };
    const XMLGregorianCalendar* getUpdateDate() const { return upDate.get(); };
    const XMLGregorianCalendar* getTransferDate() const { return trDate.get(); };
    const std::string & getCreateClient() const { return crID; };
    const std::string & getUpdateClient() const { return upID; };
    const std::string & getSponsorClient() const { return clID; };
    const std::vector<Status> &getStatuses() const { return statuses; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    
    virtual std::string toString() const;
        
protected:
    static const std::string INF_DATA_EXPR();
    static const std::string ROID_EXPR();
    static const std::string CR_ID_EXPR();
    static const std::string UP_ID_EXPR();
    static const std::string CL_ID_EXPR();
    static const std::string CR_DATE_EXPR();
    static const std::string UP_DATE_EXPR();
    static const std::string TR_DATE_EXPR();
    static const std::string STATUS_COUNT_EXPR();
    static const std::string STATUS_EXPR();
    static const std::string STATUS_S_EXPR();
    static const std::string STATUS_REASON_EXPR();
    static const std::string STATUS_LANG_EXPR();

    virtual const std::string & roidExpr() const = 0;
    virtual const std::string & crIDExpr() const = 0;
    virtual const std::string & upIDExpr() const = 0;
	virtual const std::string & clIDExpr() const = 0;
    virtual const std::string & crDateExpr() const = 0;
    virtual const std::string & upDateExpr() const = 0;
    virtual const std::string & trDateExpr() const = 0;
    virtual const std::string & statusExpr() const = 0;
    virtual const std::string & statusCountExpr() const = 0;

private:
    std::string roid;
    std::string clID, crID, upID;
	std::auto_ptr<XMLGregorianCalendar> crDate, upDate, trDate;
    std::vector<Status> statuses;
};


#endif // __INFORESPONSE_HPP
