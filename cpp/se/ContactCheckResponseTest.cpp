#include "se/ContactCheckResponse.hpp"
#include "xml/XMLParser.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");

	const string xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:chkData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:cd><contact:id avail=\"1\">sh8013</contact:id></contact:cd><contact:cd><contact:id avail=\"0\">sah8013</contact:id><contact:reason>In use</contact:reason></contact:cd><contact:cd><contact:id avail=\"1\">8013sah</contact:id></contact:cd></contact:chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";

	ContactCheckResponse response;
	XMLParser parser;
	std::auto_ptr<XMLDocument> doc(parser.parse(xml));
	response.fromXML(doc.get());
	ASSERT_EQ(response.isAvailable("sh8013"), true);
	ASSERT_EQ(response.isAvailable("sah8013"), false);
	ASSERT_EQ(response.isAvailable("8013sah"), true);
	ASSERT_EQ(response.getReason("sah8013"), "In use");
	ASSERT_EQ(response.getReason(1), "In use");
	{
		vector<bool> availList(response.getAvailableList());
		ASSERT_EQ(availList.size(), 3);
		ASSERT_EQ(availList[0], true);
		ASSERT_EQ(availList[1], false);
		ASSERT_EQ(availList[2], true);
	}
	{
		vector<string> reasonList(response.getReasonList());
		ASSERT_EQ(reasonList.size(), 3);
		ASSERT_EQ(reasonList[1], "In use");
	}
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
