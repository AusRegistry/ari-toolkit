#include "se/LoginCommand.hpp"
#include "se/CLTRID.hpp"
#include "se/Period.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	const char* objU[] = {
		"urn:ietf:params:xml:ns:domain-1.0",
		"urn:ietf:params:xml:ns:host-1.0",
		"urn:ietf:params:xml:ns:contact-1.0" };
	const vector<string> objURIs(objU, objU + 3);
	const vector<string> extURIs(1, "urn:au:params:xml:ns:auext-1.0");


	init("etc/toolkit2.conf");
	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");

		LoginCommand cmd("JTKUTEST", "1234abcd!@#$JTK");
		const string xml(cmd.toXML());
		ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw><options><version>1.0</version><lang>en</lang></options><svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI><objURI>urn:ietf:params:xml:ns:host-1.0</objURI><objURI>urn:ietf:params:xml:ns:contact-1.0</objURI></svcs></login><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");

        LoginCommand cmd("JTKUTEST", "1234abcd!@#$JTK", objURIs, extURIs);
		const string xml(cmd.toXML());
		ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw><options><version>1.0</version><lang>en</lang></options><svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI><objURI>urn:ietf:params:xml:ns:host-1.0</objURI><objURI>urn:ietf:params:xml:ns:contact-1.0</objURI><svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
	}

	{
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");
        LoginCommand cmd("JTKUTEST", "1234abcd!@#$JTK", "1.0", "fr", objURIs, extURIs);
		const string xml(cmd.toXML());
        ASSERT_EQ("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw><options><version>1.0</version><lang>fr</lang></options><svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI><objURI>urn:ietf:params:xml:ns:host-1.0</objURI><objURI>urn:ietf:params:xml:ns:contact-1.0</objURI><svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
    }

    {
		Timer::setTime("20070101.010101");
		CLTRID::setClID("JTKUTEST");

		const string newPw("n(-w18PW*");
        LoginCommand cmd("JTKUTEST", "1234abcd!@#$JTK", &newPw, "1.0", "fr", objURIs, extURIs);
		const string xml(cmd.toXML());
        ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw><newPW>n(-w18PW*</newPW><options><version>1.0</version><lang>fr</lang></options><svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI><objURI>urn:ietf:params:xml:ns:host-1.0</objURI><objURI>urn:ietf:params:xml:ns:contact-1.0</objURI><svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
    }

}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
