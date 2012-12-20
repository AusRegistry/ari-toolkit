#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"
#include "session/Timer.hpp"
#include "se/DomainKVCommandExtension.hpp"
#include "se/DomainInfoResponse.hpp"
#include "se/DomainInfoKVResponseExtension.hpp"
#include "se/CLTRID.hpp"

using namespace std;

void checkKeyValueList(const DomainInfoKVResponseExtension &kvExtension, const std::string &listName);

void testNonExistentListName()
{
   DomainInfoResponse response;
   DomainInfoKVResponseExtension kvExtension;
   response.registerExtension(&kvExtension);

   const std::string xml =
   "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"au\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

   auto_ptr<XMLParser> parser(new XMLParser);
   auto_ptr<XMLDocument> doc(parser->parse(xml));

   response.fromXML(doc.get());

   ASSERT_EQ(kvExtension.isInitialised(), true);
   ASSERT_EQ(kvExtension.getItem("nonExistentListName", "registrantIDValue"), "");
}

void testNonExistentItem()
{
   DomainInfoResponse response;
   DomainInfoKVResponseExtension kvExtension;
   response.registerExtension(&kvExtension);

   const std::string xml =
   "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"au\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

   auto_ptr<XMLParser> parser(new XMLParser);
   auto_ptr<XMLDocument> doc(parser->parse(xml));

   response.fromXML(doc.get());

   ASSERT_EQ(kvExtension.isInitialised(), true);
   ASSERT_EQ(kvExtension.getItem("au", "nonExistentItem"), "");
}

void testNotInitialisedWhenNoKVExtensionPresent()
{
   DomainInfoResponse response;
   DomainInfoKVResponseExtension kvExtension;
   response.registerExtension(&kvExtension);

   const std::string xml =
         "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>viewdomain.registrar.au</domain:name><domain:roid>D604E5509E82DF9F3105B7E182BB4DF28-AR</domain:roid><domain:status s=\"inactive\"/><domain:registrant>CON-1</domain:registrant><domain:contact type=\"tech\">CON-1</domain:contact><domain:clID>EPP</domain:clID><domain:crID>EPP</domain:crID><domain:crDate>2010-08-12T00:33:20.0Z</domain:crDate><domain:exDate>2012-08-12T00:33:21.0Z</domain:exDate><domain:authInfo><domain:pw>123paSSword</domain:pw></domain:authInfo></domain:infData></resData><extension> <auext:infData xmlns:auext=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><auext:auProperties><auext:registrantName>My Registrant</auext:registrantName><auext:registrantID type=\"ACN\">123456789</auext:registrantID><auext:eligibilityType>Company</auext:eligibilityType><auext:policyReason>1</auext:policyReason></auext:auProperties></auext:infData></extension><trID><clTRID>TESTER1.20100812.003321.1</clTRID><svTRID>9223372036854778924</svTRID></trID></response></epp>";

   auto_ptr<XMLParser> parser(new XMLParser);
   auto_ptr<XMLDocument> doc(parser->parse(xml));

   response.fromXML(doc.get());

   ASSERT_EQ(kvExtension.isInitialised(), false);
}

void testSingleKVList()
{
   DomainInfoResponse response;
   DomainInfoKVResponseExtension kvExtension;
   response.registerExtension(&kvExtension);

   const std::string xml =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"au\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

   auto_ptr<XMLParser> parser(new XMLParser);
   auto_ptr<XMLDocument> doc(parser->parse(xml));

   response.fromXML(doc.get());

   checkKeyValueList(kvExtension, "au");
}

void testMultipleKVList()
{
   DomainInfoResponse response;
   DomainInfoKVResponseExtension kvExtension;
   response.registerExtension(&kvExtension);

   const std::string xml =
         "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"ae\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist><kv:kvlist name=\"au\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

   auto_ptr<XMLParser> parser(new XMLParser);
   auto_ptr<XMLDocument> doc(parser->parse(xml));

   response.fromXML(doc.get());

   checkKeyValueList(kvExtension, "ae");
   checkKeyValueList(kvExtension, "au");
}

void checkKeyValueList(const DomainInfoKVResponseExtension &kvExtension, const std::string &listName)
{
   ASSERT_EQ(kvExtension.getItem(listName, "registrantIDValue"), "123456789");
   ASSERT_EQ(kvExtension.getItem(listName, "registrantName"), "RegistrantName Pty. Ltd.");
   ASSERT_EQ(kvExtension.getItem(listName, "registrantIDType"), "Trade License");
   ASSERT_EQ(kvExtension.getItem(listName, "eligibilityType"), "Trademark");
   ASSERT_EQ(kvExtension.getItem(listName, "eligibilityName"), "Registrant Eligi");
   ASSERT_EQ(kvExtension.getItem(listName, "eligibilityIDValue"), "987654321");
   ASSERT_EQ(kvExtension.getItem(listName, "eligibilityIDType"), "Trademark");
   ASSERT_EQ(kvExtension.getItem(listName, "policyReason"), "2");
}

int main(int argc, char* argv[])
{
   init("etc/toolkit2.conf");
   TEST_run(testNonExistentListName);
   TEST_run(testNonExistentItem);
   TEST_run(testNotInitialisedWhenNoKVExtensionPresent);
   TEST_run(testSingleKVList);
   TEST_run(testMultipleKVList);

   return TEST_errorCount();
}
