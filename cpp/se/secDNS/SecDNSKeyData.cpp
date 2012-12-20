#include "xml/XMLHelper.hpp"
#include "SecDNSKeyData.hpp"

void SecDNSKeyData::appendKeyDataElement(XMLWriter* xmlWriter, DOMElement* addElement)
{
	DOMElement* dsDataElement = xmlWriter->appendChild(addElement, "keyData");
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "flags"), flags);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "protocol"), protocol);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "alg"), alg);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "pubKey"), pubKey);
}
