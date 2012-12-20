#include "se/HostInfoResponse.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "xml/XMLParser.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");

    const string xml(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><host:infData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><host:name>ns1.example.com</host:name><host:roid>NS1_EXAMPLE1-REP</host:roid><host:status s=\"linked\"/><host:status s=\"clientUpdateProhibited\"/><host:addr ip=\"v4\">192.0.2.2</host:addr><host:addr ip=\"v4\">192.0.2.29</host:addr><host:addr ip=\"v6\">1080:0:0:0:8:800:200C:417A</host:addr><host:clID>ClientY</host:clID><host:crID>ClientX</host:crID><host:crDate>1999-04-03T22:00:00.0Z</host:crDate><host:upID>ClientX</host:upID><host:upDate>1999-12-03T09:00:00.0Z</host:upDate><host:trDate>2000-04-08T09:00:00.0Z</host:trDate></host:infData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>");

    HostInfoResponse response;
	XMLParser parser;
	std::auto_ptr<XMLDocument> doc(parser.parse(xml));
	response.fromXML(doc.get());

    {
		ASSERT_EQ(response.getName(), "ns1.example.com");

		const vector<InetAddress>& addrs(response.getAddresses());
		ASSERT_EQ(addrs.size(), 3);
		if (addrs.size() != 3) exit(1);
        ASSERT_EQ("192.0.2.2", addrs[0].getTextRep());
        ASSERT_EQ("192.0.2.29", addrs[1].getTextRep());
        ASSERT_EQ("1080:0:0:0:8:800:200C:417A", addrs[2].getTextRep());
        ASSERT_EQ("v4", addrs[0].getVersion());
        ASSERT_EQ("v4", addrs[1].getVersion());
        ASSERT_EQ("v6", addrs[2].getVersion());
    }

	{
        ASSERT_EQ("NS1_EXAMPLE1-REP", response.getROID());
		auto_ptr<XMLGregorianCalendar> dt(
			EPPDateFormatter::fromXSDateTime("1999-04-03T22:00:00.0Z"));
        ASSERT_EQ(EPPDateFormatter::toXSDateTime(*dt), "1999-04-03T22:00:00.0Z");

		auto_ptr<XMLGregorianCalendar> dt2(
			EPPDateFormatter::fromXSDateTime("1999-04-03T22:00:00.0Z"));
        ASSERT_EQ(EPPDateFormatter::toXSDateTime(*dt2), "1999-04-03T22:00:00.0Z");

        ASSERT_EQ("ClientX", response.getCreateClient());
        ASSERT_EQ("ClientX", response.getUpdateClient());
        ASSERT_EQ("ClientY", response.getSponsorClient());
        const vector<Status>& statuses(response.getStatuses());
        ASSERT_EQ(2, statuses.size());
		if (statuses.size() != 2) exit(1);
        ASSERT_EQ("linked", statuses[0].toString());
        ASSERT_EQ("clientUpdateProhibited", statuses[1].toString());
        ASSERT_EQ("ABC-12345", response.getCLTRID());
	}

}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
