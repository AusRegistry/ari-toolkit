#include "common/init.hpp"
#include "common/Test.hpp"
#include "se/CLTRID.hpp"
#include "se/DomainTransferQueryCommand.hpp"
#include "session/Timer.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	{
		CLTRID::setClID("JTKUTEST");
		Timer::setTime("20070101.010101");

		DomainTransferQueryCommand cmd("jtkutest.com.au");
		const string xml(cmd.toXML());

		ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"query\"><transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

    {
		CLTRID::setClID("JTKUTEST");
		Timer::setTime("20070101.010101");

        DomainTransferQueryCommand cmd("jtkutest.com.au", "jtkUt3st");
		const string xml(cmd.toXML());

        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"query\"><transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUt3st</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
    }

    {
		CLTRID::setClID("JTKUTEST");
		Timer::setTime("20070101.010101");

        DomainTransferQueryCommand cmd("jtkutest.com.au", "C100000-AR", "jtkUt3st");
		const string xml(cmd.toXML());

        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"query\"><transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw roid=\"C100000-AR\">jtkUt3st</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
    }

}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
