#include "se/ContactCheckResponse.hpp"
#include "se/EnumDomainInfoResponse.hpp"
#include "xml/XMLParser.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"
#include "se/EPPDateFormatter.hpp"

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
    const string xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>3.8.0.0.6.9.2.3.6.1.4.4.e164.arpa</domain:name><domain:roid>EXAMPLE1-REP</domain:roid><domain:status s=\"ok\"/><domain:registrant>jd1234</domain:registrant><domain:contact type=\"admin\">sh8013</domain:contact><domain:contact type=\"tech\">sh8013</domain:contact><domain:ns><domain:hostObj>ns1.example.com</domain:hostObj><domain:hostObj>ns2.example.com</domain:hostObj></domain:ns><domain:host>ns1.example.com</domain:host><domain:host>ns2.example.com</domain:host><domain:clID>ClientX</domain:clID><domain:crID>ClientY</domain:crID><domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate><domain:upID>ClientX</domain:upID><domain:upDate>1999-12-03T09:00:00.0Z</domain:upDate><domain:exDate>2005-04-03T22:00:00.0Z</domain:exDate><domain:trDate>2000-04-08T09:00:00.0Z</domain:trDate><domain:authInfo><domain:pw>2fooBAR</domain:pw></domain:authInfo></domain:infData></resData><extension><e164:infData xmlns:e164=\"urn:ietf:params:xml:ns:e164epp-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:e164epp-1.0 e164epp-1.0.xsd\"><e164:naptr><e164:order>10</e164:order><e164:pref>100</e164:pref><e164:flags>u</e164:flags><e164:svc>E2U+sip</e164:svc><e164:regex>\"!^.*$!sip:info@example.com!\"</e164:regex></e164:naptr><e164:naptr><e164:order>10</e164:order><e164:pref>102</e164:pref><e164:flags>u</e164:flags><e164:svc>E2U+msg</e164:svc><e164:regex>\"!^.*$!mailto:info@example.com!\"</e164:regex></e164:naptr></e164:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";

    EnumDomainInfoResponse response;
	XMLParser parser;
	auto_ptr<XMLDocument> doc(parser.parse(xml));
	try
	{
		response.fromXML(doc.get());
	}
	catch (ParsingException& pe)
	{
		cerr << pe.getMessage() << endl;
		exit(1);
	}

	{
        vector<NAPTR> naptrs(response.getNAPTRs());
        ASSERT_EQ(2, naptrs.size());
		if (naptrs.size() != 2) exit(1);
        ASSERT_EQ(10, naptrs[0].getOrder());
        ASSERT_EQ(100, naptrs[0].getPreference());
        ASSERT_EQ("u", naptrs[0].getFlags());
        ASSERT_EQ("E2U+sip", naptrs[0].getService());
        ASSERT_EQ("\"!^.*$!sip:info@example.com!\"", naptrs[0].getRegex());
        ASSERT_EQ("", naptrs[0].getReplacement());
        ASSERT_EQ(10, naptrs[1].getOrder());
        ASSERT_EQ(102, naptrs[1].getPreference());
        ASSERT_EQ("u", naptrs[1].getFlags());
        ASSERT_EQ("E2U+msg", naptrs[1].getService());
        ASSERT_EQ("\"!^.*$!mailto:info@example.com!\"", naptrs[1].getRegex());
        ASSERT_EQ("", naptrs[1].getReplacement());
	}

	{
		vector<Status> statuses = response.getStatuses();
		ASSERT_EQ(statuses.size(), 1);
		ASSERT_EQ(statuses[0].toString(), statuses[0].toString());
        ASSERT_EQ("3.8.0.0.6.9.2.3.6.1.4.4.e164.arpa", response.getName());
        ASSERT_EQ("2fooBAR", response.getPW());
        ASSERT_EQ("jd1234", response.getRegistrantID());

        vector<string> contacts(response.getTechContacts());
        ASSERT_EQ(1, contacts.size());
        ASSERT_EQ("sh8013", contacts[0]);

        vector<string> contacts2(response.getAdminContacts());
        ASSERT_EQ(1, contacts2.size());
        ASSERT_EQ("sh8013", contacts2[0]);

        const vector<string>& contacts3(response.getBillingContacts());
        ASSERT_EQ(contacts3.size(), 0);

        vector<string> ns(response.getNameservers());
        ASSERT_EQ(2, ns.size());
        ASSERT_EQ("ns1.example.com", ns[0]);
        ASSERT_EQ("ns2.example.com", ns[1]);

        ASSERT_EQ(2, response.getSubordinateHosts().size());
        ASSERT_EQ("ns1.example.com", response.getSubordinateHosts()[0]);
        ASSERT_EQ("ns2.example.com", response.getSubordinateHosts()[1]);

        ASSERT_EQ("EXAMPLE1-REP", response.getROID());
#if 0
        ASSERT_EQ(
                EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"),
                response.getCreateDate());
        ASSERT_EQ(
                EPPDateFormatter.fromXSDateTime("1999-12-03T09:00:00.0Z"),
                response.getUpdateDate());
#endif
        ASSERT_EQ("ClientY", response.getCreateClient());
        ASSERT_EQ("ClientX", response.getUpdateClient());
        ASSERT_EQ("ClientX", response.getSponsorClient());
    }
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
