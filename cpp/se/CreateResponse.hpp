#ifndef __CREATE_RESPONSE_HPP
#define __CREATE_RESPONSE_HPP

#include "se/DataResponse.hpp"
#include "se/ObjectType.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include "xml/XMLDocument.hpp"

#include <string>

/**
 * Representation of the EPP create response, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped.
 * Instances of this class provide an interface to access create data for the
 * object identified in a {@link CreateCommand}.
 * This relies on the instance first being initialised by a suitable EPP create
 * response using the method fromXML.  For flexibility, this implementation
 * extracts the data from the response using XPath queries, the expressions for
 * which are defined statically.
 *
 * @see CreateCommand
 */
class CreateResponse : public DataResponse
{
public:
    CreateResponse (const ObjectType* objectType);
    
    const XMLGregorianCalendar* getCreateDate() const { return crDate.get(); };

    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);

protected:
    static const std::string OBJ();
	static const std::string CRE_DATA_EXPR();
	static const std::string CR_DATE_EXPR();
    
    virtual const std::string & crDateExpr() const = 0;
    
private:
	std::auto_ptr<XMLGregorianCalendar> crDate;
};
#endif // __CREATE_RESPONSE_HPP
