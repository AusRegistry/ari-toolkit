#ifndef __CHECKRESPONSE_HPP
#define __CHECKRESPONSE_HPP

#include "se/DataResponse.hpp"
#include <map>

/**
 * Representation of the EPP check response, as defined in RFC3730.
 * Subclasses of this must specify the object to which the command is mapped.
 * Instances of this class provide an interface to access availability
 * information for each object identified in a {@link
 * com.ausregistry.jtoolkit2.se.CheckCommand}.
 * This relies on the instance first being initialised by a suitable EPP check
 * response using the method fromXML.  For flexibility, this implementation
 * extracts the data from the response using XPath queries, the expressions for
 * which are defined statically.
 *
 * @see CheckCommand
 */
class CheckResponse : public DataResponse
{
public:
    CheckResponse (const ObjectType* objectType);
    
    bool isAvailable (const std::string &nameID) const;
    const std::string & getReason (const std::string &nameID) const;
    const std::string & getReason (int index) const;
    const std::vector<bool> & getAvailableList() const { return availArray; };
    const std::vector<std::string> & getReasonList() const { return reasonArray; };
    
    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    virtual std::string toString() const;

protected:
    static const std::string CHKDATA_COUNT_EXPR();
    static const std::string CHKDATA_IND_EXPR();
    static const std::string CHKDATA_IDENT_EXPR();
    static const std::string CHKDATA_AVAIL_EXPR();
    static const std::string CHKDATA_REASON_EXPR();
    
	class Availability
	{
	public:
		Availability (bool avail, const std::string &reason)
			: avail(avail), reason(reason) {};
		
		bool avail;
		std::string reason;
	};
    
    std::map<std::string, Availability> availMap;
    std::vector<bool> availArray;
    std::vector<std::string> reasonArray;
    
    virtual const std::string& chkDataCountExpr()  const = 0;
    virtual const std::string& chkDataIndexExpr()  const = 0;
    virtual const std::string& chkDataTextExpr()   const = 0;
	virtual const std::string& chkDataAvailExpr()  const = 0;
    virtual const std::string& chkDataReasonExpr() const = 0;
};
    
#endif // __CHECKRESPONSE_HPP
