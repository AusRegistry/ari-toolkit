#include "se/PostalInfo.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XMLWriter.hpp"

using namespace std;

PostalInfo::PostalInfo(const PostalInfoType* type,
                       const string    &name,
                       const string    &city,
                       const string    &countryCode)
	: type(type->toString()), name(name), city(city), cc(countryCode)
{ }

PostalInfo::PostalInfo (const PostalInfoType           *type,
                        const string              &name,
                        const string              &org,
                        const vector<string> &street,
                        const string              &city,
                        const string              &stateProv,
                        const string              &postcode,
                        const string              &countryCode)
	: type(type->toString()), name(name), org(org), street(street),
	  city(city), sp(stateProv), pc(postcode), cc(countryCode)
{ }

DOMElement* PostalInfo::appendToElement(XMLWriter *xmlWriter,
                                        DOMElement *parent) const
{
    DOMElement *postalInfo = 
        xmlWriter->appendChild (parent,
                                "postalInfo",
                                "type",
                                type);
    
	XMLHelper::setTextContent
		(xmlWriter->appendChild(postalInfo, "name"), name);
    
    if (org != "")
		XMLHelper::setTextContent
			(xmlWriter->appendChild (postalInfo, "org"), org);
    
    DOMElement *addr = xmlWriter->appendChild (postalInfo, "addr");
    if (street.size() > 0)
    {
        vector<string>::const_iterator p;
        
        for (p = street.begin(); p != street.end(); p++)
			XMLHelper::setTextContent
				(xmlWriter->appendChild (addr, "street"), *p);
    }
    // End if (street valid)
    
	XMLHelper::setTextContent
		(xmlWriter->appendChild (addr, "city"), city);
    
    if (sp != "")
		XMLHelper::setTextContent
			(xmlWriter->appendChild (addr, "sp"), sp);
    
    if (pc != "")
		XMLHelper::setTextContent
			(xmlWriter->appendChild (addr, "pc"), pc);
    
	XMLHelper::setTextContent
		(xmlWriter->appendChild (addr, "cc"), cc);
    
    return postalInfo;
}
// End PostalInfo::appendToElement()
