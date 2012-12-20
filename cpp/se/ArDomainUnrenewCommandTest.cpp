#include "se/ArDomainUnrenewCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void doWork()
{
    init("etc/toolkit2.conf");

    Timer::setTime("20070101.010101");
    CLTRID::setClID("JTKUTEST");

    ArDomainUnrenewCommand cmd(
            "jtkutest.com.au", *EPPDateFormatter::fromXSDateTime("2007-01-01T00:00:00.0Z"));
    const string xml(cmd.toXML());
    ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:arext-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd\"><unrenew><unrenew xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd\"><name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate></unrenew></unrenew><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>");
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

