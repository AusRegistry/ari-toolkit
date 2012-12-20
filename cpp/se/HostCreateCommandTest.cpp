#include "common/init.hpp"
#include "common/Test.hpp"
#include "se/CLTRID.hpp"
#include "se/HostCreateCommand.hpp"
#include "session/Timer.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	{
		CLTRID::setClID("JTKUTEST");
		Timer::setTime("20070101.010101");

		HostCreateCommand hcc("ns1.jtkutest.com.au");
		const string xml(hcc.toXML());
		ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><name>ns1.jtkutest.com.au</name></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

	{
		CLTRID::setClID("JTKUTEST");
		Timer::setTime("20070101.010101");

		vector<InetAddress> addrs;
		addrs.push_back(InetAddress("192.168.0.1", IPVersion::IPv4()));
		addrs.push_back(InetAddress("::1", IPVersion::IPv6()));

		HostCreateCommand hcc("ns1.jtkutest.com.au", &addrs);
		const string xml(hcc.toXML());
		ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><name>ns1.jtkutest.com.au</name><addr ip=\"v4\">192.168.0.1</addr><addr ip=\"v6\">::1</addr></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
