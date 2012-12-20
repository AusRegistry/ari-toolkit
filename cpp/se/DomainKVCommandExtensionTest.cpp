#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"
#include "session/Timer.hpp"
#include "se/DomainKVCommandExtension.hpp"
#include "se/DomainCreateCommand.hpp"
#include "se/DomainUpdateCommand.hpp"
#include "se/StandardCommandType.hpp"
#include "se/CLTRID.hpp"

using namespace std;

void addSampleItemsToExtension(DomainKVCommandExtension &extension, const string &listName);

const static string registrantName = "AusRegistry";
const static string eligibilityType = "Trademark";
const static string policyReason = "1";

void testUpdateSingleKVList()
{
   Timer::setTime("20070101.010101");
   CLTRID::setClID("JTKUTEST");

   DomainKVCommandExtension extension(StandardCommandType::UPDATE());
   addSampleItemsToExtension(extension, "ae");

   DomainUpdateCommand updateCommand("jtkutest.com.ae");

   updateCommand.appendExtension(extension);

   const string xml = updateCommand.toXML();
   ASSERT_EQ(xml,
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
         );
}

void testCreateSingleKVList()
{
   Timer::setTime("20070101.010101");
   CLTRID::setClID("JTKUTEST");

   DomainKVCommandExtension extension(StandardCommandType::CREATE());
   addSampleItemsToExtension(extension, "ae");

   string registrant("JTKCON");
   DomainCreateCommand createCommand("jtkutest.com.ae", "jtkUT3st", &registrant);

   createCommand.appendExtension(extension);

   const string xml = createCommand.toXML();
   ASSERT_EQ(xml,
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
         );
}

void testMultipleKVList()
{
   Timer::setTime("20070101.010101");
   CLTRID::setClID("JTKUTEST");

   DomainKVCommandExtension extension(StandardCommandType::CREATE());
   addSampleItemsToExtension(extension, "ae");
   addSampleItemsToExtension(extension, "au");

   string registrant("JTKCON");
   DomainCreateCommand createCommand("jtkutest.com.ae", "jtkUT3st", &registrant);

   createCommand.appendExtension(extension);

   const string xml = createCommand.toXML();
   ASSERT_EQ(xml,
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist><kvlist name=\"au\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>"
         );
}

void addSampleItemsToExtension(DomainKVCommandExtension &extension, const string &listName)
{
   extension.addItem(listName, "eligibilityType", eligibilityType);
   extension.addItem(listName, "policyReason", policyReason);
   extension.addItem(listName, "registrantName", registrantName);
}

int main(int argc, char* argv[])
{
   init("etc/toolkit2.conf");
   TEST_run(testUpdateSingleKVList);
   TEST_run(testCreateSingleKVList);
   TEST_run(testMultipleKVList);

   return TEST_errorCount();
}
