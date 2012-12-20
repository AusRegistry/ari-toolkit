#include "xml/XMLHelper.hpp"
#include "SecDNSChgType.hpp"

void SecDNSChgType::createXMLElement(XMLWriter* xmlWriter, DOMElement* chgElement)
{
	if (maxSigLife.get() != NULL)
	{
		maxSigLife->createXMLElement(xmlWriter, chgElement);
	}
}

