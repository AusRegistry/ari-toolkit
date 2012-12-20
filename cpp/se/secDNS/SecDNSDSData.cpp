#include "xml/XMLHelper.hpp"
#include "SecDNSDSData.hpp"

void SecDNSDSData::appendDSDataElement(XMLWriter* xmlWriter, DOMElement* addElement)
{
	DOMElement* dsDataElement = xmlWriter->appendChild(addElement, "dsData");
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "keyTag"), keyTag);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "alg"), alg);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "digestType"), digestType);
	XMLHelper::setTextContent(xmlWriter->appendChild(dsDataElement, "digest"), digest);

	if (keyData.get() != NULL)
	{
		keyData.get()->appendKeyDataElement(xmlWriter, dsDataElement);
	}
}
