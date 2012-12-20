#include "se/DomainRegistrantTransferCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/Period.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

using namespace std;

const static std::string registrantName = "AusRegistry";
const static std::string registrantIDValue = "01241326211";
const static std::string registrantIDType = "Trade License";
const static std::string eligibilityType = "Trademark";
const static std::string policyReason = "1";
const static std::string eligibilityName = "Blah";
const static std::string eligibilityIDValue = "1231239523";
const static std::string eligibilityIDType = "Trademark";
const auto_ptr<XMLGregorianCalendar> curExpDate(EPPDateFormatter::fromXSDateTime("2007-01-01T01:01:01.0Z"));
const static std::string kvListName = "ae";
void addSampleKVItems(DomainRegistrantTransferCommand *command);

void testWithoutPeriod()
{
   Timer::setTime("20070101.010101");
   CLTRID::setClID("JTKUTEST");

   auto_ptr<DomainRegistrantTransferCommand> command(
         new DomainRegistrantTransferCommand("jtkutest.com.ae", *curExpDate, kvListName, "testing"));
   addSampleKVItems(command.get());

   const std::string xml = command->toXML();
   ASSERT_EQ(xml,
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:registrant-1.0\"><registrantTransfer><name>jtkutest.com.ae</name><curExpDate>"
           + EPPDateFormatter::toXSDate(*curExpDate)
           + "</curExpDate><kvlist xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" name=\"ae\"><item key=\"eligibilityIDType\">Trademark</item><item key=\"eligibilityIDValue\">1231239523</item><item key=\"eligibilityName\">Blah</item><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantIDType\">Trade License</item><item key=\"registrantIDValue\">01241326211</item><item key=\"registrantName\">AusRegistry</item></kvlist><explanation>testing</explanation></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>"
         );
}

void testWithPeriod()
{
   Period period(2, PeriodUnit::YEARS());
   auto_ptr<DomainRegistrantTransferCommand> command(
         new DomainRegistrantTransferCommand("jtkutest.com.ae", *curExpDate, kvListName, "testing", &period));
   addSampleKVItems(command.get());

   const std::string xml = command->toXML();
   ASSERT_EQ(xml,
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:registrant-1.0\"><registrantTransfer><name>jtkutest.com.ae</name><curExpDate>"
           + EPPDateFormatter::toXSDate(*curExpDate)
           + "</curExpDate><period unit=\"y\">2</period><kvlist xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" name=\"ae\"><item key=\"eligibilityIDType\">Trademark</item><item key=\"eligibilityIDValue\">1231239523</item><item key=\"eligibilityName\">Blah</item><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantIDType\">Trade License</item><item key=\"registrantIDValue\">01241326211</item><item key=\"registrantName\">AusRegistry</item></kvlist><explanation>testing</explanation></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>"
           );
}

void addSampleKVItems(DomainRegistrantTransferCommand *command)
{
   command->addItem("policyReason", policyReason);
   command->addItem("eligibilityIDType", eligibilityIDType);
   command->addItem("registrantIDType", registrantIDType);
   command->addItem("registrantIDValue", registrantIDValue);
   command->addItem("registrantName", registrantName);
   command->addItem("eligibilityIDValue", eligibilityIDValue);
   command->addItem("eligibilityName", eligibilityName);
   command->addItem("eligibilityType", eligibilityType);
}

int main(int argc, char* argv[])
{
   init("etc/toolkit2.conf");
   TEST_run(testWithoutPeriod);
//   TEST_run(testWithPeriod);
   return TEST_errorCount();
}

