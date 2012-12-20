#include "se/ArDomainUnrenewResponse.hpp"
#include "xml/XMLParser.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"
#include "se/EPPDateFormatter.hpp"

using namespace std;

void doWork()
{
    init("etc/toolkit2.conf");
    const string xml1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><urenData xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\"><name>example.com</name><exDate>2009-04-03T22:00:00.0Z</exDate></urenData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    const string xml2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1001\"><msg>Command completed successfully; action pending</msg></result><resData><urenData xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\"><name>example.com</name></urenData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";

    ArDomainUnrenewResponse response1;
    ArDomainUnrenewResponse response2;
    XMLParser parser;
    auto_ptr<XMLDocument> doc(parser.parse(xml1));
    response1.fromXML(doc.get());
    {
        ASSERT_EQ(response1.getName(), "example.com");
        const XMLGregorianCalendar *exDate = response1.getExpiryDate();
        string res = EPPDateFormatter::toXSDateTime(*exDate);
        ASSERT_EQ(res, "2009-04-03T22:00:00.0Z");
        const vector<Result>& results(response1.getResults());
        ASSERT_EQ(response1.getCLTRID(), "ABC-12345");
        ASSERT_EQ(results[0].getResultCode(), 1000);
        ASSERT_EQ(results[0].getResultMessage(),
                  "Command completed successfully");
    }

    auto_ptr<XMLDocument> doc2(parser.parse(xml2));
    response2.fromXML(doc2.get());
    {
        ASSERT_EQ(response2.getName(), "example.com");
        const XMLGregorianCalendar *exDate = response2.getExpiryDate();
        ASSERT_NULL(exDate);
        const vector<Result>& results(response2.getResults());
        ASSERT_EQ(response2.getCLTRID(), "ABC-12345");
        ASSERT_EQ(results[0].getResultCode(), 1001);
        ASSERT_EQ(results[0].getResultMessage(),
                "Command completed successfully; action pending");
    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

