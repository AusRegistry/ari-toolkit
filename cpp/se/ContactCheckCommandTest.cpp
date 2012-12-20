#include "se/ContactCheckCommand.hpp"
#include "se/CLTRID.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");
		auto_ptr<Command> cmd(new ContactCheckCommand("JTKCON"));

		const string xml(cmd->toXML());
		ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><check xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON</id></check></check><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");
		vector<string> ids;
		ids.push_back("JTKCON1");
		ids.push_back("JTKCON2");
		auto_ptr<Command> cmd(new ContactCheckCommand(ids));
        const string xml(cmd->toXML());
        ASSERT_EQ(xml,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><check xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON1</id><id>JTKCON2</id></check></check><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}
}


int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
