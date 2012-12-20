#include <sstream>

#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"
#include "SecDNSDSData.hpp"
#include "SecDNSKeyData.hpp"
#include "SecDNSDSOrKeyType.hpp"
#include "DomainSecDNSInfoResponseExtension.hpp"
#include "se/DomainInfoResponse.hpp"

using namespace std;

void assertFirstDSData(const SecDNSDSData* dsData);
void assertKeyData(const SecDNSKeyData* keyData);
void assertSecondDSData(const SecDNSDSData* dsData);

string getInfoResponseExpectedXml(const string& domainName, bool isMulitpleDs,
		bool isKeyData, bool isDsData);
void buildXmlResponseAfterExtension(ostringstream& result);
void buildSecDNSXmlExtension(bool isMulitpleDs, bool isKeyData, bool isDsData,
		ostringstream& result);
void buildXmlResponseBeforeExtension(const string& domainName,
		ostringstream& result);

void testSecDNSInfoExtensionAllFields()
{
	string domainName = "test.com.au";
	DomainInfoResponse response;
	DomainSecDNSInfoResponseExtension extension;
	response.registerExtension(&extension);

	const string xml = getInfoResponseExpectedXml(domainName, false, false, true);

	auto_ptr<XMLParser> parser(new XMLParser);
	auto_ptr<XMLDocument> doc(parser->parse(xml));

	response.fromXML(doc.get());

	ASSERT_EQ(extension.isInitialised(), true);
	ASSERT_EQ(response.getName(), domainName);
	ASSERT_EQ(extension.getInfData()->getDSDataListSize(), 1);
	assertFirstDSData(extension.getInfData()->getDSData(0).get());
}

void testSecDNSInfoExtensionOnlyKeyData()
{
	string domainName = "test.com.au";
	DomainInfoResponse response;
	DomainSecDNSInfoResponseExtension extension;
	response.registerExtension(&extension);

	const string xml = getInfoResponseExpectedXml(domainName, false, true, false);

	auto_ptr<XMLParser> parser(new XMLParser);
	auto_ptr<XMLDocument> doc(parser->parse(xml));

	response.fromXML(doc.get());

	ASSERT_EQ(extension.isInitialised(), true);
	ASSERT_EQ(response.getName(), domainName);
	ASSERT_EQ(extension.getInfData()->getDSDataListSize(), 0);
	ASSERT_EQ(extension.getInfData()->getKeyDataListSize(), 1);
	assertKeyData(extension.getInfData()->getKeyData(0).get());
}

void testSecDNSInfoExtensionMultipleDsRecords()
{
	string domainName = "test.com.au";
	DomainInfoResponse response;
	DomainSecDNSInfoResponseExtension extension;
	response.registerExtension(&extension);

	const string xml = getInfoResponseExpectedXml(domainName, true, false, true);

	auto_ptr<XMLParser> parser(new XMLParser);
	auto_ptr<XMLDocument> doc(parser->parse(xml));

	response.fromXML(doc.get());

	ASSERT_EQ(extension.isInitialised(), true);
	ASSERT_EQ(response.getName(), domainName);
	ASSERT_EQ(extension.getInfData()->getDSDataListSize(), 2);
	assertFirstDSData(extension.getInfData()->getDSData(0).get());
	assertSecondDSData(extension.getInfData()->getDSData(1).get());
}

void testSecDNSInfoNoExtensionInitialised()
{
	string domainName = "test.com.au";
	DomainInfoResponse response;
	DomainSecDNSInfoResponseExtension extension;
	response.registerExtension(&extension);

	const string xml = getInfoResponseExpectedXml(domainName, false, false, false);

	auto_ptr<XMLParser> parser(new XMLParser);
	auto_ptr<XMLDocument> doc(parser->parse(xml));

	response.fromXML(doc.get());

	ASSERT_EQ(extension.isInitialised(), false);
	ASSERT_EQ(response.getName(), domainName);
	ASSERT_EQ(extension.getInfData()->getDSDataListSize(), 0);
	ASSERT_EQ(extension.getInfData()->getKeyDataListSize(), 0);
}

void assertFirstDSData(const SecDNSDSData* dsData)
{
	if (dsData != NULL)
	{
		ASSERT_EQ(dsData->getKeyTag(), 12345);
		ASSERT_EQ(dsData->getAlg(), 3);
		ASSERT_EQ(dsData->getDigestType(), 1);
		ASSERT_EQ(dsData->getDigest(), "49FD46E6C4B45C55D4AC");

		assertKeyData(dsData->getKeyData());
	}
}

void assertKeyData(const SecDNSKeyData* keyData)
{
	if (keyData != NULL)
	{
		ASSERT_EQ(keyData->getFlags(), 256);
		ASSERT_EQ(keyData->getProtocol(), 3);
		ASSERT_EQ(keyData->getAlg(), 1);
		ASSERT_EQ(keyData->getPubKey(), "AQPJ////4Q==");
	}
}

void assertSecondDSData(const SecDNSDSData* dsData)
{
	if (dsData != NULL)
	{
		ASSERT_EQ(dsData->getKeyTag(), 14321);
		ASSERT_EQ(dsData->getAlg(), 2);
		ASSERT_EQ(dsData->getDigestType(), 5);
		ASSERT_EQ(dsData->getDigest(), "39FD46E6C4B45C55D4AC");

		ASSERT_NULL(dsData->getKeyData());
	}
}

string getInfoResponseExpectedXml(const string& domainName, bool isMulitpleDs,
		bool isKeyData, bool isDsData)
{
	ostringstream result;
	buildXmlResponseBeforeExtension(domainName, result);
	if (isKeyData || isDsData)
	{
		buildSecDNSXmlExtension(isMulitpleDs, isKeyData, isDsData, result);
	}
	buildXmlResponseAfterExtension(result);

	return result.str();
}

void buildXmlResponseAfterExtension(ostringstream& result)
{
	result << "<trID>" << "<clTRID>ABC-12345</clTRID>"
			<< "<svTRID>54321-XYZ</svTRID>" << "</trID>" << "</response>"
			<< "</epp>";
}

void buildSecDNSXmlExtension(bool isMulitpleDs, bool isKeyData, bool isDsData,
		ostringstream& result)
{
	result << "<extension>"
			<< "<infData xmlns=\"urn:X-au:params:xml:ns:auext-1.1\">"
			<< "<auProperties>"
			<< "<registrantName>RegistrantName Pty. Ltd.</registrantName>"
			<< "<registrantID type=\"ACN\">123456789</registrantID>"
			<< "<eligibilityType>Other</eligibilityType>"
			<< "<eligibilityName>Registrant Eligi</eligibilityName>"
			<< "<eligibilityID type=\"ABN\">987654321</eligibilityID>"
			<< "<policyReason>2</policyReason>"
			<< "</auProperties>" << "</infData>"
			<< "<infData xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\">"
			<< "<maxSigLife>604800</maxSigLife>";

	if (isDsData)
	{
		result << "<dsData>" << "<keyTag>12345</keyTag>"
				<< "<alg>3</alg>"
				<< "<digestType>1</digestType>"
				<< "<digest>49FD46E6C4B45C55D4AC</digest>"
				<< "<keyData>" << "<flags>256</flags>"
				<< "<protocol>3</protocol>"
				<< "<alg>1</alg>"
				<< "<pubKey>AQPJ////4Q==</pubKey>"
				<< "</keyData>" << "</dsData>";
		if (isMulitpleDs)
		{
			result << "<dsData>"
					<< "<keyTag>14321</keyTag>"
					<< "<alg>2</alg>"
					<< "<digestType>5</digestType>"
					<< "<digest>39FD46E6C4B45C55D4AC</digest>"
					<< "</dsData>";
		}
	}

	if (isKeyData)
	{
		result << "<keyData>" << "<flags>256</flags>"
				<< "<protocol>3</protocol>"
				<< "<alg>1</alg>"
				<< "<pubKey>AQPJ////4Q==</pubKey>"
				<< "</keyData>";
	}

	result << "</infData>" << "</extension>";
}

void buildXmlResponseBeforeExtension(const string& domainName,
		ostringstream& result)
{
	result << "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			<< "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
			<< "<response>" << "<result code=\"1000\">"
			<< "<msg>Command completed successfully</msg>" << "</result>"
			<< "<resData>"
			<< "<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\">"
			<< "<name>" + domainName + "</name>" << "<roid>D0000003-AR</roid>"
			<< "<status s=\"ok\" lang=\"en\"/>"
			<< "<registrant>EXAMPLE</registrant>"
			<< "<contact type=\"tech\">EXAMPLE</contact>" << "<ns>"
			<< "<hostObj>ns1.example.com.au</hostObj>"
			<< "<hostObj>ns2.example.com.au</hostObj>" << "</ns>"
			<< "<host>ns1.example.com.au</host>"
			<< "<host>ns2.exmaple.com.au</host>" << "<clID>Registrar</clID>"
			<< "<crID>Registrar</crID>"
			<< "<crDate>2006-02-09T15:44:58.0Z</crDate>"
			<< "<exDate>2008-02-10T00:00:00.0Z</exDate>" << "<authInfo>"
			<< "<pw>0192pqow</pw>" << "</authInfo>" << "</infData>"
			<< "</resData>";
}

int main(int argc, char* argv[])
{
	init("etc/toolkit2.conf");
	TEST_run(testSecDNSInfoExtensionAllFields);
	TEST_run(testSecDNSInfoExtensionOnlyKeyData);
	TEST_run(testSecDNSInfoExtensionMultipleDsRecords);
	TEST_run(testSecDNSInfoNoExtensionInitialised);

	return TEST_errorCount();
}
