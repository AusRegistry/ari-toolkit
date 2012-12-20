#include <sstream>

#include "SecDNSMaxSigLifeType.hpp"
#include "SecDNSDSData.hpp"
#include "SecDNSKeyData.hpp"
#include "DomainSecDNSInfoResponseExtension.hpp"

using namespace std;

/*
 * Have to use static function instead of static variable
 * since there is not guarantee about the construct/destruct
 * order of a static instance of any types
 */
const string DomainSecDNSInfoResponseExtension::DS_DATA_LIST_EXPR()
{
	return EXTENSION_EXPR() + "/secDNS:infData/secDNS:dsData";
}

const string DomainSecDNSInfoResponseExtension::KEY_DATA_LIST_EXPR()
{
	return EXTENSION_EXPR() + "/secDNS:infData/secDNS:keyData";
}

const string DomainSecDNSInfoResponseExtension::MAXSIGLIFE_EXPR()
{
	return EXTENSION_EXPR() + "/secDNS:infData/secDNS:maxSigLife";
}

void DomainSecDNSInfoResponseExtension::fromXML(XMLDocument *xmlDoc)
{
	infData.reset(new SecDNSDSOrKeyType);

	if (xmlDoc->getNodeCount("count(" + MAXSIGLIFE_EXPR() + ")") > 0)
	{
		int maxSigLifeInt = getInt(xmlDoc->getNodeValue(MAXSIGLIFE_EXPR()));

		auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(maxSigLifeInt));
		infData->setMaxSigLife(maxSigLife.release());
	}

	int secDnsCount = getResponseDSData(xmlDoc);
	initialised = (secDnsCount > 0) && (infData->getDSDataListSize() == secDnsCount);

	if (!initialised)
	{
		secDnsCount = getResponseKeyData(xmlDoc);
		initialised = (secDnsCount > 0) && (infData->getKeyDataListSize() == secDnsCount);
	}
}

SecDNSDSOrKeyType* DomainSecDNSInfoResponseExtension::getInfData() const
{
	return infData.get();
}

int DomainSecDNSInfoResponseExtension::getResponseDSData(const XMLDocument* xmlDoc)
{
	int secDnsCount = xmlDoc->getNodeCount("count(" + DS_DATA_LIST_EXPR() + ")");

	if (secDnsCount > 0) {
		for (int i = 1; i <= secDnsCount; i++)
		{
			const std::string currentDSDataListXPath =
				ReceiveSE::replaceIndex(DS_DATA_LIST_EXPR() + "[IDX]", i);
			auto_ptr<SecDNSDSData> dsData = getDSData(xmlDoc, currentDSDataListXPath);
			infData->addToDSData(dsData.release());
		}
	}

	return secDnsCount;
}

int DomainSecDNSInfoResponseExtension::getResponseKeyData(const XMLDocument* xmlDoc)
{
	int secDnsCount = xmlDoc->getNodeCount("count(" + KEY_DATA_LIST_EXPR() + ")");
	if (secDnsCount > 0) {
		for (int i = 1; i <= secDnsCount; i++)
		{
			const std::string currentKeyDataListXPath =
				ReceiveSE::replaceIndex(KEY_DATA_LIST_EXPR() + "[IDX]", i);
			auto_ptr<SecDNSKeyData> keyData = getKeyData(xmlDoc, currentKeyDataListXPath);
			infData->addToKeyData(keyData.release());
		}
	}
	return secDnsCount;
}

auto_ptr<SecDNSDSData> DomainSecDNSInfoResponseExtension::getDSData(
		const XMLDocument* xmlDoc,
		const std::string& dsDataXPath)
{
	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData());
	dsData->setKeyTag(getInt(xmlDoc->getNodeValue(dsDataXPath + "/secDNS:keyTag/text()")));
	dsData->setAlg(getInt(xmlDoc->getNodeValue(dsDataXPath + "/secDNS:alg/text()")));
	dsData->setDigestType(getInt(xmlDoc->getNodeValue(dsDataXPath + "/secDNS:digestType/text()")));
	dsData->setDigest(xmlDoc->getNodeValue(dsDataXPath + "/secDNS:digest/text()"));

	int keyDataCount = xmlDoc->getNodeCount("count(" + dsDataXPath + "/secDNS:keyData)");
	if (keyDataCount > 0) {
		dsData->setKeyData(getKeyData(xmlDoc, dsDataXPath + "/secDNS:keyData").release());
	}
	return dsData;
}

auto_ptr<SecDNSKeyData> DomainSecDNSInfoResponseExtension::getKeyData(
		const XMLDocument* xmlDoc,
		const std::string& keyDataXPath)
{
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData());

	keyData->setFlags(getInt(xmlDoc->getNodeValue(keyDataXPath + "/secDNS:flags/text()")));
	keyData->setProtocol(getInt(xmlDoc->getNodeValue(keyDataXPath + "/secDNS:protocol/text()")));
	keyData->setAlg(getInt(xmlDoc->getNodeValue(keyDataXPath + "/secDNS:alg/text()")));
	keyData->setPubKey(xmlDoc->getNodeValue(keyDataXPath + "/secDNS:pubKey/text()"));
	return keyData;
}

int DomainSecDNSInfoResponseExtension::getInt(const std::string& value)
{
	int intValue;
	istringstream iss(value);
	iss >> intValue;
	return intValue;
}
