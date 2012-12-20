#include "se/LoginCommand.hpp"
#include "se/StandardCommandType.hpp"
#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"

#include <xercesc/dom/DOMElement.hpp>

using namespace std;

const string LoginCommand::DEFAULT_VERSION("1.0");
const string LoginCommand::DEFAULT_LANG("en");

LoginCommand::LoginCommand(const string &clID,
                           const string &password)
    : Command (StandardCommandType::LOGIN())
{
    const vector<string>& stdURIs = StandardObjectType::getStandardURIs();
    
    init(clID, password, DEFAULT_VERSION, DEFAULT_LANG, NULL, stdURIs, vector<string>());
}


LoginCommand::LoginCommand(const string& clID,
                           const string& password,
                           const string* newPW)
    : Command(StandardCommandType::LOGIN())
{
    const vector<string>& stdURIs = StandardObjectType::getStandardURIs();
    
    init(clID, password, DEFAULT_VERSION, DEFAULT_LANG, newPW, stdURIs, vector<string>());
}


LoginCommand::LoginCommand(const string& clID,
                           const string& password,
                           const vector<string> objURIs,
                           const vector<string> extURIs)
    : Command(StandardCommandType::LOGIN())
{
    init(clID, password, DEFAULT_VERSION, DEFAULT_LANG, NULL, objURIs, extURIs);
}                            

LoginCommand::LoginCommand (const string& clID,
                            const string& password,
                            const string& version,
                            const string& lang,
                            const vector<string> objURIs,
                            const vector<string> extURIs)
    : Command(StandardCommandType::LOGIN())
{
    init(clID, password, version, lang, NULL, objURIs, extURIs);
}

LoginCommand::LoginCommand (const string& clID,
                            const string& password,
                            const string* newPassword,
                            const string& version,
                            const string& lang,
                            const vector<string> objURIs,
                            const vector<string> extURIs)
    : Command(StandardCommandType::LOGIN())
{
    init(clID, password, version, lang, newPassword, objURIs, extURIs);
}

void LoginCommand::init(const string& clID, 
						const string& password,
						const string& version, 
						const string& lang,
						const string* newPassword,
						const vector<string> objURIs,
						const vector<string> extURIs)
{
	XMLHelper::setTextContent
		(xmlWriter->appendChild (cmdElement, "clID"), clID);
	XMLHelper::setTextContent
		(xmlWriter->appendChild (cmdElement, "pw"), password);
    
    if (newPassword)
		XMLHelper::setTextContent
			(xmlWriter->appendChild(cmdElement, "newPW"), *newPassword);

    DOMElement *options = xmlWriter->appendChild(cmdElement, "options");
            
	XMLHelper::setTextContent 
		(xmlWriter->appendChild(options, "version"), version);
	XMLHelper::setTextContent
		(xmlWriter->appendChild(options, "lang"), lang);
    
    if (objURIs.size() > 0)
    {
        DOMElement *svcs = xmlWriter->appendChild(cmdElement, "svcs");
        for (unsigned int i = 0; i < objURIs.size(); i++)
		{
			XMLHelper::setTextContent(xmlWriter->appendChild(svcs, "objURI"), objURIs[i]);
		}

        if (extURIs.size() > 0)
        {
            DOMElement *svcExtension = 
                xmlWriter->appendChild (svcs, "svcExtension");
            
            for (unsigned int i = 0; i < extURIs.size(); i++)
				XMLHelper::setTextContent
					(xmlWriter->appendChild (svcExtension, "extURI"),
					 extURIs[i]);
        }
    }
}
