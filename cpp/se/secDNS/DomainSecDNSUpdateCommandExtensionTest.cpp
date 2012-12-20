#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"
#include "session/Timer.hpp"
#include "SecDNSDSData.hpp"
#include "SecDNSKeyData.hpp"
#include "SecDNSDSOrKeyType.hpp"
#include "DomainSecDNSUpdateCommandExtension.hpp"
#include "se/DomainUpdateCommand.hpp"
#include "se/CLTRID.hpp"

using namespace std;

void testSecDNSAddFieldsUrgent()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(12345, 3, 1, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(256, 3, 1, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSDSOrKeyType> addData(new SecDNSDSOrKeyType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLife(new SecDNSMaxSigLifeType(604800));
	addData->setMaxSigLife(maxSigLife.release());
	addData->addToDSData(dsData.release());

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(true);
	extension.setAddData(addData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><add><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSRemoveFieldsNotUrgent()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(65535, 255, 255, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(65535, 255, 255, "AQPJ////4Q=="));
	dsData->setKeyData(keyData.release());

	auto_ptr<SecDNSRemType> remData(new SecDNSRemType());
	remData->addToDSData(dsData.release());

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(false);
	extension.setRemData(remData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><rem><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSRemoveAll()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);


	auto_ptr<SecDNSRemType> remData(new SecDNSRemType());
	remData->setRemoveAll(true);

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(true);
	extension.setRemData(remData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSRemoveAllAndAddDsData()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);


	auto_ptr<SecDNSRemType> remData(new SecDNSRemType());
	remData->setRemoveAll(true);

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(true);
	extension.setRemData(remData.release());

	auto_ptr<SecDNSDSData> dsData(new SecDNSDSData(65535, 255, 255, "49FD46E6C4B45C55D4AC"));
	auto_ptr<SecDNSDSOrKeyType> addData(new SecDNSDSOrKeyType());
	addData->addToDSData(dsData.release());
	extension.setAddData(addData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
        	"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem><add><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSRemoveAllAndAddKeyData()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);


	auto_ptr<SecDNSRemType> remData(new SecDNSRemType());
	remData->setRemoveAll(true);

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(true);
	extension.setRemData(remData.release());

	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(65535, 255, 255, "AQPJ////4Q=="));
	auto_ptr<SecDNSDSOrKeyType> addData(new SecDNSDSOrKeyType());
	addData->addToKeyData(keyData.release());
	extension.setAddData(addData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
        	"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem><add><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

void testSecDNSRemoveAllAndAddKeyDataAndChangeMaxSigLife()
{
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	string passwd = "jtkUT3st";
	string registrantID = "JTKCON";
	DomainUpdateCommand updateCommand("jtkutest.com.au", &passwd, NULL, NULL, &registrantID);


	auto_ptr<SecDNSRemType> remData(new SecDNSRemType());
	remData->setRemoveAll(true);

	DomainSecDNSUpdateCommandExtension extension;
	extension.setUrgent(true);
	extension.setRemData(remData.release());

	auto_ptr<SecDNSKeyData> keyData(new SecDNSKeyData(65535, 255, 255, "AQPJ////4Q=="));
	auto_ptr<SecDNSDSOrKeyType> addData(new SecDNSDSOrKeyType());
	addData->addToKeyData(keyData.release());
	extension.setAddData(addData.release());

	auto_ptr<SecDNSChgType> chgData(new SecDNSChgType());
	auto_ptr<SecDNSMaxSigLifeType> maxSigLifeType(new SecDNSMaxSigLifeType(604800));
	chgData->setMaxSigLife(maxSigLifeType.release());
	extension.setChgData(chgData.release());

	updateCommand.appendExtension(extension);

	const string xml = updateCommand.toXML();
	ASSERT_EQ(xml,
        	"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem><add><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></add><chg><maxSigLife>604800</maxSigLife></chg></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
			);
}

int main(int argc, char* argv[])
{
   init("etc/toolkit2.conf");
   TEST_run(testSecDNSAddFieldsUrgent);
   TEST_run(testSecDNSRemoveFieldsNotUrgent);
   TEST_run(testSecDNSRemoveAll);
   TEST_run(testSecDNSRemoveAllAndAddDsData);
   TEST_run(testSecDNSRemoveAllAndAddKeyData);
   TEST_run(testSecDNSRemoveAllAndAddKeyDataAndChangeMaxSigLife);

   return TEST_errorCount();
}
