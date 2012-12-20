#include "se/HostInfoCommand.hpp"
#include "se/CLTRID.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	Timer::setTime("20070101.010101");
	CLTRID::setClID("JTKUTEST");

	HostInfoCommand cmd("ns1.jtkutest.com.au");
	const string xml = cmd.toXML();
	ASSERT_EQ("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><name>ns1.jtkutest.com.au</name></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
