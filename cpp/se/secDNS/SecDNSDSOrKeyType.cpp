#include "xml/XMLHelper.hpp"
#include "SecDNSDSOrKeyType.hpp"

void SecDNSDSOrKeyType::setMaxSigLife(SecDNSMaxSigLifeType* maxSigLife)
{
	this->maxSigLife.reset(maxSigLife);
}

void SecDNSDSOrKeyType::addToDSData(SecDNSDSData* dsData)
{
	std::tr1::shared_ptr<SecDNSDSData> dsDataPtr(dsData);
	dsDataList.push_back(dsDataPtr);
}

void SecDNSDSOrKeyType::addToKeyData(SecDNSKeyData* keyData)
{
	std::tr1::shared_ptr<SecDNSKeyData> keyDataPtr(keyData);
	keyDataList.push_back(keyDataPtr);
}

int SecDNSDSOrKeyType::getDSDataListSize()
{
	return dsDataList.size();
}

std::tr1::shared_ptr<const SecDNSDSData> SecDNSDSOrKeyType::getDSData(int index) const
{
	return dsDataList.at(index);
}

std::tr1::shared_ptr<SecDNSDSData> SecDNSDSOrKeyType::getDSData(int index)
{
	return dsDataList.at(index);
}

int SecDNSDSOrKeyType::getKeyDataListSize()
{
	return keyDataList.size();
}

std::tr1::shared_ptr<const SecDNSKeyData> SecDNSDSOrKeyType::getKeyData(int index) const
{
	return keyDataList.at(index);
}

std::tr1::shared_ptr<SecDNSKeyData> SecDNSDSOrKeyType::getKeyData(int index)
{
	return keyDataList.at(index);
}

void SecDNSDSOrKeyType::createXMLElement(XMLWriter* xmlWriter, DOMElement* addElement)
{

	if (maxSigLife.get() != NULL)
	{
		maxSigLife->createXMLElement(xmlWriter, addElement);
	}

	std::vector<std::tr1::shared_ptr<SecDNSDSData> >::const_iterator ds_list_iterator;
	for (ds_list_iterator = dsDataList.begin();
			ds_list_iterator != dsDataList.end();
			ds_list_iterator++)
	{
		ds_list_iterator->get()->appendDSDataElement(xmlWriter, addElement);
	}

	std::vector<std::tr1::shared_ptr<SecDNSKeyData> >::const_iterator key_list_iterator;
	for (key_list_iterator = keyDataList.begin();
			key_list_iterator != keyDataList.end();
			key_list_iterator++)
	{
		key_list_iterator->get()->appendKeyDataElement(xmlWriter, addElement);
	}
}

