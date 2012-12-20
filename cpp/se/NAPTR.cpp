#include "se/NAPTR.hpp"
#include "common/StringUtils.hpp"
#include "xml/XMLHelper.hpp"
#include "xml/XMLWriter.hpp"
#include <xercesc/util/XMLString.hpp>

using namespace xercesc;

NAPTR::NAPTR (int order,
              int preference,
              const char *flags,
              const std::string &service,
              const std::string &regex,
              const std::string &replacement)
    : order(order), 
      preference(preference), 
      flags(flags), 
      svc(service),
      regex(regex),
      replacement(replacement)
{
}


DOMElement * NAPTR::appendToElement (XMLWriter *xmlWriter,
                                     DOMElement *parent) const
{
    DOMElement *e164Naptr = xmlWriter->appendChild (parent, "naptr");
    
    if (e164Naptr)
    {
		XMLHelper::setTextContent
			(xmlWriter->appendChild (e164Naptr, "order"), order);

		XMLHelper::setTextContent
			(xmlWriter->appendChild (e164Naptr, "pref"), preference);
		
		XMLHelper::setTextContent
				(xmlWriter->appendChild (e164Naptr, "flags"), flags);
		
		XMLHelper::setTextContent
				(xmlWriter->appendChild (e164Naptr, "svc"), svc);

		XMLHelper::setTextContent
				(xmlWriter->appendChild (e164Naptr, "regex"), regex);

		XMLHelper::setTextContent
				(xmlWriter->appendChild (e164Naptr, "repl"), replacement);
    }
    
    return e164Naptr;    
}
