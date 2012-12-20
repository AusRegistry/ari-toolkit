#include "se/ContactUpdateCommand.hpp"
#include "se/StandardObjectType.hpp"
#include "xml/XMLHelper.hpp"
#include "se/Status.hpp"
#include "se/IntPostalInfo.hpp"
#include "se/LocalPostalInfo.hpp"
#include "se/Disclose.hpp"

/*
 * The complete set of attributes of a contact which may be updated as per
 * RFC3733.
 */
ContactUpdateCommand::ContactUpdateCommand (const std::string &id,
                                            const std::string &pw,
                                            const std::vector<Status> *addStatuses,
                                            const std::vector<std::string> *remStatuses,
                                            const IntPostalInfo *newIntPostalInfo,
                                            const LocalPostalInfo *newLocPostalInfo,
                                            const std::string *newVoice,
                                            const std::string *newVoiceExt,
                                            const std::string *newFax,
                                            const std::string *newFaxExt,
                                            const std::string *newEmail,
                                            const Disclose *disclose)
    : UpdateCommand (StandardObjectType::CONTACT(), id)
{
    if (addStatuses)
    {
        const std::vector<Status> &stats = *addStatuses;
        
        DOMElement *add = xmlWriter->appendChild (objElement, "add");
        
        for (unsigned int i = 0; i < stats.size(); i++)
            xmlWriter->appendChild (add, "status", stats[i].getRationale(),
                                    "s", stats[i].toString());
    }
    
    if (remStatuses)
    {
        const std::vector<std::string> &stats = *remStatuses;
        
        DOMElement *rem = xmlWriter->appendChild (objElement, "rem");
        
        for (unsigned int i = 0; i < stats.size(); i++)
            xmlWriter->appendChild (rem, "status", "s", stats[i]);
    }
    
    if (pw == "" && 
        newIntPostalInfo == NULL && 
        newLocPostalInfo == NULL && 
        newVoice == NULL && 
        newFax == NULL &&
        newEmail == NULL && 
        disclose == NULL) return;

    DOMElement *chg = xmlWriter->appendChild (objElement, "chg");
    
    if (newIntPostalInfo)
        newIntPostalInfo->appendToElement (xmlWriter, chg);
    
    if (newLocPostalInfo)
        newLocPostalInfo->appendToElement (xmlWriter, chg);
    
    if (newVoice)
    {
        DOMElement *voice = xmlWriter->appendChild (chg, "voice");
        if (newVoiceExt)
			XMLHelper::setAttribute (voice, "x", *newVoiceExt);
        
		XMLHelper::setTextContent (voice, *newVoice);
    }
    
    if (newFax)
    {
        DOMElement *fax = xmlWriter->appendChild (chg, "fax");
        if (newFaxExt)
			XMLHelper::setAttribute (fax, "x", *newFaxExt);
    
		XMLHelper::setTextContent (fax, *newFax);
    }
    
    if (newEmail)
		XMLHelper::setTextContent
			(xmlWriter->appendChild (chg, "email"), *newEmail);
    
    if (pw != "")
		XMLHelper::setTextContent
			(xmlWriter->appendChild
            	(xmlWriter->appendChild
                	(chg,
                 	"authInfo"),
             	 "pw"),
			 pw);
    
    if (disclose)
        disclose->appendToElement (xmlWriter, chg);
}


                                            
