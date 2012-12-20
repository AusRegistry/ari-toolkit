#include "se/ContactCheckCommand.hpp"
#include "se/AuDomainModifyRegistrantCommand.hpp"
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

    {
        Timer::setTime("20070101.010101");
        CLTRID::setClID("JTKUTEST");

        AuDomainModifyRegistrantCommand cmd(
                "jtkutest.com.au",
                registrantName,
                "testing",
                &eligibilityType,
                policyReason,
                &registrantID,
                &registrantIDType,
                &eligibilityName,
                &eligibilityID,
                &eligibilityIDType);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties><explanation>testing</explanation></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

