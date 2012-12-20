#include "se/Period.hpp"
#include "xml/XMLWriter.hpp"

#include "xml/XMLHelper.hpp"

using namespace xercesc;

DOMElement* Period::appendPeriod(XMLWriter *xmlWriter, 
								 DOMElement *parent) const
{
    DOMElement *retval = 
        xmlWriter->appendChild(
			parent,
            "period",
            "unit",
             unit->toString());
	XMLHelper::setTextContent (retval, period);
    return retval;
}
