#include "se/ContactCreateCommand.hpp"
#include "se/StandardObjectType.hpp"
#include "se/IntPostalInfo.hpp"
#include "xml/XMLHelper.hpp"
#include "se/PostalInfo.hpp"
#include "se/LocalPostalInfo.hpp"
#include "se/Disclose.hpp"
#include "se/IllegalArgException.hpp"
#include "common/ErrorPkg.hpp"


ContactCreateCommand::ContactCreateCommand (const std::string& id,
                                            const std::string& pw,
                                            const PostalInfo* postalInfo,
                                            const std::string& email,
                                            const LocalPostalInfo* localPostalInfo,
                                            const std::string* voice,
                                            const std::string* voiceExt,
                                            const std::string* fax,
                                            const std::string* faxExt,
                                            const Disclose* disclose)
    : CreateCommand(StandardObjectType::CONTACT(), id)
{
	if (postalInfo == NULL && localPostalInfo == NULL)
	{
		throw IllegalArgException(
				ErrorPkg::getMessage("se.contact.create.missing_arg"));
	}
    
    if (dynamic_cast<const IntPostalInfo *>(postalInfo))
	{
		postalInfo->appendToElement(xmlWriter, objElement);
		if (localPostalInfo)
		{
			localPostalInfo->appendToElement(xmlWriter, objElement);
		}
	}
	else if (dynamic_cast<const LocalPostalInfo *>(postalInfo))
	{
		postalInfo->appendToElement(xmlWriter, objElement);
	}

    if (voice)
    {
        if (voiceExt)
            xmlWriter->appendChild (objElement, "voice", *voice, "x", *voiceExt);
        else
			XMLHelper::setTextContent
				(xmlWriter->appendChild (objElement, "voice"), *voice);
    }
    
    if (fax)
    {
        if (faxExt)
            xmlWriter->appendChild (objElement, "fax", *fax, "x", *faxExt);
        else
			XMLHelper::setTextContent
				(xmlWriter->appendChild (objElement, "fax"), *fax);
    }
    
	XMLHelper::setTextContent 
		(xmlWriter->appendChild (objElement, "email"), email);
    
	XMLHelper::setTextContent
		(xmlWriter->appendChild
        	(xmlWriter->appendChild
            	(objElement,
             	"authInfo"),
         	 "pw"),
		 pw);
    
    if (disclose)
        disclose->appendToElement (xmlWriter, objElement);
}
