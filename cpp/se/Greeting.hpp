#ifndef __GREETING_HPP
#define __GREETING_HPP

#include "se/ReceiveSE.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "xml/ParsingException.hpp"

/**
 * Provides access to the data collection policy statement parameter values
 * reported by an EPP server in a Greeting compliant with the greeting
 * specification in RFC3730.
 */
class DCPStatement
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    DCPStatement () {}

    DCPStatement (const std::vector<std::string>& purposes,
                  const std::vector<std::string>& recipients,
                  const std::string &retentionPolicy)
        : purposes(purposes),
          recipients(recipients),
          retention(retentionPolicy)
	{ }
    
    const std::vector<std::string> & getPurpose() const 
		{ return purposes; };
    const std::vector<std::string> & getRecipients() const 
		{ return recipients; };
			
    const std::string & getRetentionPolicy() const { return retention; };
    std::vector<std::string> purposes, recipients;
    std::string retention;
};


/**
 * Provides access to EPP server parameters as published in an EPP greeting
 * service element.
 * 
 */
class Greeting : public ReceiveSE
{
public:
    Greeting() : ReceiveSE(){};
    
    const std::string & getServerID() const { return svID; };
    const XMLGregorianCalendar* getServerDateTime() const { return svDate.get(); };
    const std::vector<std::string>& getProtocolVersions() const { return versions; };
    const std::vector<std::string>& getLanguages() const { return langs; };
    const std::vector<std::string>& getObjURIs() const { return objURIs; };
    const std::vector<std::string>& getExtURIs() const { return extURIs; };
    const std::string& getDcpAccess() const { return dcpAccess; };
    const std::vector<DCPStatement>& getDataCollectionPolicyStatements()
        const { return dcpStatements; };
    
    std::string toString() const;
    
    virtual void fromXML(XMLDocument *xmlDoc) throw (ParsingException);
    
private:
    std::string svID;
	std::auto_ptr<XMLGregorianCalendar> svDate;
    std::vector<std::string> versions, langs, objURIs, extURIs;
    std::string dcpAccess;
    std::vector<DCPStatement> dcpStatements;

    static const std::string GREETING_EXPR;
    static const std::string DCP_EXPR;
    static const std::string SVID_EXPR;
	static const std::string SVDATE_EXPR;
    static const std::string VERSIONS_EXPR;
    static const std::string LANGS_EXPR;
    static const std::string OBJ_URIS_EXPR;
    static const std::string EXT_URIS_EXPR;
    static const std::string ACCESS_EXPR;
    static const std::string STMT_COUNT_EXPR;
    static const std::string STMT_IND_EXPR;
    static const std::string PURPOSE_EXPR;
    static const std::string RECIPIENT_EXPR;
    static const std::string RETENTION_EXPR;
    static const std::string EXPIRY_EXPR;
};

#endif // __GREETING_HPP
