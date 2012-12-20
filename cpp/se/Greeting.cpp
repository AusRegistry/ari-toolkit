#include "se/Greeting.hpp"
#include "se/EPPDateFormatter.hpp"
#include "xml/ParsingException.hpp"

const std::string Greeting::GREETING_EXPR ("/e:epp/e:greeting");
const std::string Greeting::DCP_EXPR (GREETING_EXPR + "/e:dcp");
const std::string Greeting::SVID_EXPR(GREETING_EXPR + "/e:svID/text()");
const std::string Greeting::SVDATE_EXPR(GREETING_EXPR + "/e:svDate/text()");
const std::string Greeting::VERSIONS_EXPR (GREETING_EXPR + "/e:svcMenu/e:version");
const std::string Greeting::LANGS_EXPR (GREETING_EXPR + "/e:svcMenu/e:lang");
const std::string Greeting::OBJ_URIS_EXPR (GREETING_EXPR + "/e:svcMenu/e:objURI");
const std::string Greeting::EXT_URIS_EXPR (GREETING_EXPR + "/e:svcMenu/e:svcExtension/e:extURI");
const std::string Greeting::ACCESS_EXPR (DCP_EXPR + "/e:access/*[1]");
const std::string Greeting::STMT_COUNT_EXPR ("count(" + DCP_EXPR + "/e:statement)");
const std::string Greeting::STMT_IND_EXPR (DCP_EXPR + "/e:statement[IDX]");
const std::string Greeting::PURPOSE_EXPR ("/e:purpose");
const std::string Greeting::RECIPIENT_EXPR ("/e:recipient");
const std::string Greeting::RETENTION_EXPR ("/e:retention/*[1]");
const std::string Greeting::EXPIRY_EXPR ("/e:expiry/*[1]");


std::string Greeting::toString() const
{
    unsigned int i;
    std::string versionString = versions[0];
    
    for (i = 1; i < versions.size(); i++)
        versionString += "," + versions[i];
    
    std::string langString = langs[0];
    for (i = 1; i < langs.size(); i++)
        langString += "," + langs[i];
    
    std::string objURIString = objURIs[0];
    for (i = 1; i < objURIs.size(); i++)
        objURIString += "," + objURIs[i];
    
    std::string retval = 
        "(svID = " + getServerID() + ")" +
        (getServerDateTime() != NULL ? "(svDate = " + getServerDateTime()->toXMLFormat() + ")" : "") +
        "(versions = (" + versionString + "))" +
        "(languages = (" + langString + "))" +
        "(objURIs = (" + objURIString + "))";
    
    return retval;
}


void Greeting::fromXML(XMLDocument *xmlDoc) throw (ParsingException)
{
    debugLogger->LOG_FINEST("enter");
    
    try
    {
		// debugLogger->info (xmlDoc->toString());
        svID = xmlDoc->getNodeValue (SVID_EXPR);
        std::string svDateText = xmlDoc->getNodeValue (SVDATE_EXPR);
        svDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(svDateText));
        versions = xmlDoc->getNodeValues(VERSIONS_EXPR);
        langs = xmlDoc->getNodeValues(LANGS_EXPR);
        objURIs = xmlDoc->getNodeValues(OBJ_URIS_EXPR);
        extURIs = xmlDoc->getNodeValues(EXT_URIS_EXPR);
        dcpAccess = xmlDoc->getNodeValue(ACCESS_EXPR);
        
        int dcpStmtCount = xmlDoc->getNodeCount(STMT_COUNT_EXPR);
        for (int i = 0; i < dcpStmtCount; i++)
        {
            std::string qry = ReceiveSE::replaceIndex(STMT_IND_EXPR, i+1);

            dcpStatements.push_back(
				DCPStatement(
					xmlDoc->getChildNames(qry + PURPOSE_EXPR),
					xmlDoc->getChildNames(qry + RECIPIENT_EXPR),
					xmlDoc->getNodeName(qry + RETENTION_EXPR)));
        }
    }
    catch (XPathExpressionException& e)
	{
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
    
    debugLogger->info (toString());
    debugLogger->LOG_FINEST("exit");
}

