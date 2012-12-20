#ifndef __TRANSFER_RESPONSE_HPP
#define __TRANSFER_RESPONSE_HPP

#include "se/DataResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/ObjectType.hpp"

#include "xml/XMLDocument.hpp"

/**
 * Representation of the EPP transfer response, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped.
 * Instances of this class provide an interface to access transfer response
 * data for the object identified in a @link TransferCommand .
 * This relies on the instance first being initialised by a suitable EPP
 * transfer response using the method fromXML.  For flexibility, this
 * implementation extracts the data from the response using XPath queries, the
 * expressions for which are defined statically.
 *
 * @see TransferCommand
 */
class TransferResponse : public DataResponse
{
public:
    TransferResponse (const ObjectType* objectType);
    
    const std::string& getTransferStatus() const { return trStatus; };
    const std::string& getRequestingClID() const { return reID; };
    const XMLGregorianCalendar* getRequestDate()    const { return reDate.get(); };
    const std::string& getActioningClID()  const { return acID; };
    const XMLGregorianCalendar* getActionDate()     const { return acDate.get(); };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

protected:
    static const std::string OBJ();
    static const std::string TR_STATUS_EXPR();
    static const std::string REID_EXPR();
    static const std::string REDATE_EXPR();
    static const std::string ACID_EXPR();
    static const std::string ACDATE_EXPR();
    
    virtual const std::string& trStatusExpr() const = 0;
    virtual const std::string& reIDExpr() const = 0;
    virtual const std::string& reDateExpr() const = 0;
    virtual const std::string& acIDExpr() const = 0;
    virtual const std::string& acDateExpr() const = 0;
    
private:
    std::string trStatus, reID, acID;
	std::auto_ptr<XMLGregorianCalendar> reDate, acDate;
};

#endif // __TRANSFER_RESPONSE_HPP
