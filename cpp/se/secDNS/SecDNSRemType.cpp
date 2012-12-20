#include "xml/XMLHelper.hpp"
#include "SecDNSRemType.hpp"

void SecDNSRemType::createXMLElement(XMLWriter* xmlWriter, DOMElement* remElement)
{
	if (removeAll)
	{
        XMLHelper::setTextContent(xmlWriter->appendChild(remElement, "all"), "true");
	}

	std::list<std::tr1::shared_ptr<SecDNSDSData> >::const_iterator ds_list_iterator;
	for (ds_list_iterator = dsDataList.begin();
			ds_list_iterator != dsDataList.end();
			ds_list_iterator++)
	{
		ds_list_iterator->get()->appendDSDataElement(xmlWriter, remElement);
	}

	std::list<std::tr1::shared_ptr<SecDNSKeyData> >::const_iterator key_list_iterator;
	for (key_list_iterator = keyDataList.begin();
			key_list_iterator != keyDataList.end();
			key_list_iterator++)
	{
		key_list_iterator->get()->appendKeyDataElement(xmlWriter, remElement);
	}
}

