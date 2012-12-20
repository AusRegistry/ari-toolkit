#include "se/ArDomainUndeleteCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/Period.hpp"
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
    ArDomainUndeleteCommand cmd("jtkutest.com.au");
    const string xml(cmd.toXML());
    ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:arext-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd\"><undelete><undelete xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd\"><name>jtkutest.com.au</name></undelete></undelete><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>");
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

