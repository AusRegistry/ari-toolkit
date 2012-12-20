#include "se/ContactCheckCommand.hpp"
#include "se/AeDomainCreateCommand.hpp"
#include "se/CLTRID.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

using namespace std;

void doWork()
{
    init("etc/toolkit2.conf");

    const string registrantName = "AusRegistry";
    const string registrantID = "01241326211";
    const string registrantIDType = "Trade License";
    const string eligibilityType = "Trade License (IT)";
    const int policyReason = 1;
    const string eligibilityName = "Blah";
    const string eligibilityID = "1231239523";
    const string eligibilityIDType = "Trademark";

    /**
     * Test that the XML string generated for a minimal create domain command
     * matches the expected XML for an EPP create domain command with those
     * parameters.
     */
    {
        Timer::setTime("20070101.010101");
        CLTRID::setClID("JTKUTEST");

        vector<string> techIds;
        techIds.push_back("JTKCON2");
        string registrant("JTKCON");
        AeDomainCreateCommand cmd("jtkutest.co.ae", "jtkUT3st", &registrant, &techIds,
                eligibilityType, policyReason, registrantName);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.co.ae</name><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\"><aeProperties><registrantName>AusRegistry</registrantName><eligibilityType>Trade License (IT)</eligibilityType><policyReason>1</policyReason></aeProperties></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
    }

    /**
     * Test that the XML string generated for a sample create domain command
     * specified with all available parameters matches the expected XML for
     * an EPP create domain command with those parameters.
     *
     */
    {
        Timer::setTime("20070101.010101");
        CLTRID::setClID("JTKUTEST");

        vector<string> techIds;
        techIds.push_back("JTKCON2");

        vector<string> adminContacts;
        adminContacts.push_back("JTKCON");
        adminContacts.push_back("JTKCON2");

        string registrant("JTKCON");

        vector<string> nameServers;
        nameServers.push_back("ns1.ausregistry.net");
        nameServers.push_back("ns2.ausregistry.net");

	Period period(48, PeriodUnit::MONTHS());

        AeDomainCreateCommand cmd(
                "jtkutest.co.ae", "jtkUT3st", &registrant,
                &techIds, &adminContacts,
                NULL, &nameServers, &period,
                eligibilityType, policyReason,
                registrantName, &registrantID,
                &registrantIDType, &eligibilityName,
                &eligibilityID, &eligibilityIDType);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.co.ae</name><period unit=\"m\">48</period><ns><hostObj>ns1.ausregistry.net</hostObj><hostObj>ns2.ausregistry.net</hostObj></ns><registrant>JTKCON</registrant><contact type=\"admin\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\"><aeProperties><registrantName>AusRegistry</registrantName><registrantID type=\"Trade License\">01241326211</registrantID><eligibilityType>Trade License (IT)</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"Trademark\">1231239523</eligibilityID><policyReason>1</policyReason></aeProperties></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");

    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

