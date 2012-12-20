#include "se/DomainCheckResponse.hpp"
// #include "session/TestEnvironment.hpp"
#include "xml/XMLParser.hpp"
#include "xml/XMLDocument.hpp"

#include "common/init.hpp"
#include "common/Test.hpp"

#include <memory>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");

    const string xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:chkData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:cd><domain:name avail=\"1\">example.com</domain:name></domain:cd><domain:cd><domain:name avail=\"0\">example.net</domain:name><domain:reason>In use</domain:reason></domain:cd><domain:cd><domain:name avail=\"1\">example.org</domain:name></domain:cd></domain:chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
	XMLParser parser;
	auto_ptr<XMLDocument> doc(parser.parse(xml));
	DomainCheckResponse response;
	response.fromXML(doc.get());

	ASSERT_EQ(response.isAvailable("example.com"), true);
	ASSERT_EQ(response.getReason("example.net"), "In use");
	ASSERT_EQ("In use", response.getReason(1));
	ASSERT_EQ(3, response.getAvailableList().size());
	ASSERT_EQ(3, response.getReasonList().size());
	ASSERT_EQ(1000, response.getResults()[0].getResultCode());
	ASSERT_EQ("ABC-12345", response.getCLTRID());
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
