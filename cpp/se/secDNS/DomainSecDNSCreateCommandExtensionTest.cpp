#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"
#include "session/Timer.hpp"
#include "SecDNSDSData.hpp"
#include "SecDNSKeyData.hpp"
#include "SecDNSDSOrKeyType.hpp"
#include "DomainSecDNSCreateCommandExtension.hpp"
#include "se/DomainCreateCommand.hpp"
#include "se/CLTRID.hpp"

using namespace std;

void testSecDNSAllFields()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	DomainCreateCommand createCommand("jtkutest.com.au", "jtkUT3st", NULL);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(12345, 3, 1, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(256, 3, 1, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSDSOrKeyType> createData(new SecDNSDSOrKeyType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(604800));
	createData->setMaxSigLife(maxSigLife.release());
	createData->addToDSData(dsData.release());

	DomainSecDNSCreateCommandExtension extension;
	extension.setCreateData(createData.release());

	createCommand.appendExtension(extension);

	const string xml = createCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSMultipleDSDataWithoutMaxSigLife()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	DomainCreateCommand createCommand("jtkutest.com.au", "jtkUT3st", NULL);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(12345, 3, 1, "38FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(256, 3, 1, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSDSOrKeyType> createData(new SecDNSDSOrKeyType());
	createData->addToDSData(dsData.release());

	auto_ptr<SecDNSDSData> dsData2(new SecDNSDSData(6789, 2, 2, "49FD46E6C4B45C55D4AC"));
	createData->addToDSData(dsData2.release());

	DomainSecDNSCreateCommandExtension extension;
	extension.setCreateData(createData.release());

	createCommand.appendExtension(extension);

	const string xml = createCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>38FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData><dsData><keyTag>6789</keyTag><alg>2</alg><digestType>2</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSAllFieldsMin()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	DomainCreateCommand createCommand("jtkutest.com.au", "jtkUT3st", NULL);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(0, 0, 0, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(0, 0, 0, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSDSOrKeyType> createData(new SecDNSDSOrKeyType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(1));
	createData->setMaxSigLife(maxSigLife.release());
	createData->addToDSData(dsData.release());

	DomainSecDNSCreateCommandExtension extension;
	extension.setCreateData(createData.release());

	createCommand.appendExtension(extension);

	const string xml = createCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>1</maxSigLife><dsData><keyTag>0</keyTag><alg>0</alg><digestType>0</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>0</flags><protocol>0</protocol><alg>0</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSAllFieldsMax()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	DomainCreateCommand createCommand("jtkutest.com.au", "jtkUT3st", NULL);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(65535, 255, 255, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(65535, 255, 255, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSDSOrKeyType> createData(new SecDNSDSOrKeyType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(2147483647));
	createData->setMaxSigLife(maxSigLife.release());
	createData->addToDSData(dsData.release());

	DomainSecDNSCreateCommandExtension extension;
	extension.setCreateData(createData.release());

	createCommand.appendExtension(extension);

	const string xml = createCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>2147483647</maxSigLife><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSJustKeyData()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	DomainCreateCommand createCommand("jtkutest.com.au", "jtkUT3st", NULL);

	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(65535, 255, 255, "AQPJ////4Q=="));

	auto_ptr<SecDNSDSOrKeyType> createData(new SecDNSDSOrKeyType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(65535));
	createData->setMaxSigLife(maxSigLife.release());
	createData->addToKeyData(keyData.release());

	DomainSecDNSCreateCommandExtension extension;
	extension.setCreateData(createData.release());

	createCommand.appendExtension(extension);

	const string xml = createCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>65535</maxSigLife><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

int main(int argc, char* argv[])
{
   init("etc/toolkit2.conf");
   TEST_run(testSecDNSAllFields);
   TEST_run(testSecDNSMultipleDSDataWithoutMaxSigLife);
   TEST_run(testSecDNSAllFieldsMin);
   TEST_run(testSecDNSAllFieldsMax);
   TEST_run(testSecDNSJustKeyData);

   return TEST_errorCount();
}
