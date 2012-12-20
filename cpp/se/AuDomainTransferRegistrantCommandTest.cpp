#include "se/AuDomainTransferRegistrantCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/Period.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void doWork()
{
    init("etc/toolkit2.conf");

    const int policyReason = 1;
    const string registrantName = "AusRegistry";
    const string registrantID = "01241326211";
    const string registrantIDType = "ACN";
    const string eligibilityType = "Company";
    const string eligibilityName = "Blah";
    const string eligibilityID = "1231239523";
    const string eligibilityIDType = "OTHER";
    auto_ptr<XMLGregorianCalendar> curExpDate(EPPDateFormatter::fromXSDateTime("2007-01-01T01:01:01.0Z"));

    {
        Timer::setTime("20070101.010101");
        CLTRID::setClID("JTKUTEST");

        AuDomainTransferRegistrantCommand cmd(
                "jtkutest.com.au",      // domain name
                *curExpDate,            // current expire date
                "Other",
                policyReason,
                registrantName,
                "testing");
        const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><registrantTransfer><registrantTransfer xmlns=\"urn:X-au:params:xml:ns:audomain-1.0\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd\"><name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate><auProperties><registrantName>AusRegistry</registrantName><eligibilityType>Other</eligibilityType><policyReason>1</policyReason></auProperties><explanation>testing</explanation></registrantTransfer></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>");
    }

    {
        Timer::setTime("20070101.010101");
        CLTRID::setClID("JTKUTEST");
        Period p(2, PeriodUnit::YEARS());

        AuDomainTransferRegistrantCommand cmd(
                "jtkutest.com.au",  // name
                *curExpDate,        // curExpDate
                eligibilityType,
                policyReason,
                registrantName,
                "testing",
                &registrantID,
                &registrantIDType,
                &eligibilityName,
                &eligibilityID,
                &eligibilityIDType,
                &p);
        const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><registrantTransfer><registrantTransfer xmlns=\"urn:X-au:params:xml:ns:audomain-1.0\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd\"><name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate><period unit=\"y\">2</period><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties><explanation>testing</explanation></registrantTransfer></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>");
    }

}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

