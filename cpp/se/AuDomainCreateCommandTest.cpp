#include "se/ContactCheckCommand.hpp"
#include "se/AuDomainCreateCommand.hpp"
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
    const string registrantIDType = "ACN";
    const string eligibilityType = "Company";
    const int policyReason = 1;
    const string eligibilityName = "Blah";
    const string eligibilityID = "1231239523";
    const string eligibilityIDType = "OTHER";

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
        AuDomainCreateCommand cmd("jtkutest.com.au", "jtkUT3st", &registrant, &techIds,
                eligibilityType, policyReason, registrantName);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><auProperties><registrantName>AusRegistry</registrantName><eligibilityType>Company</eligibilityType><policyReason>1</policyReason></auProperties></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
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

        AuDomainCreateCommand cmd(
                "jtkutest.com.au", "jtkUT3st", &registrant,
                &techIds, &adminContacts,
                NULL, &nameServers,
                eligibilityType, policyReason,
                registrantName, &registrantID,
                &registrantIDType, &eligibilityName,
                &eligibilityID, &eligibilityIDType);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><ns><hostObj>ns1.ausregistry.net</hostObj><hostObj>ns2.ausregistry.net</hostObj></ns><registrant>JTKCON</registrant><contact type=\"admin\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");

    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

