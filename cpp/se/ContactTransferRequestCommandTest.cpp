#include "se/ContactTransferRequestCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");
        ContactTransferRequestCommand cmd("JTKCON1", "jtkcon1pw");

        const string expected("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"request\"><transfer xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON1</id><authInfo><pw>jtkcon1pw</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
        const string xml(cmd.toXML());
        ASSERT_EQ(expected, xml);
	}
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
