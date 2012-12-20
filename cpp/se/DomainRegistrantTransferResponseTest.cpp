#include "se/DomainRegistrantTransferResponse.hpp"
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
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><rtrnData xmlns=\"urn:X-ar:params:xml:ns:registrant-1.0\"><name>example.com</name><exDate>2009-04-03T22:00:00.0Z</exDate></rtrnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";

    DomainRegistrantTransferResponse response;

    XMLParser parser;
    auto_ptr<XMLDocument> doc(parser.parse(xml));
    response.fromXML(doc.get());
    {
        ASSERT_EQ(response.getName(), "example.com");
        const XMLGregorianCalendar *exDate = response.getExpiryDate();
        string res = EPPDateFormatter::toXSDateTime(*exDate);
        ASSERT_EQ(res, "2009-04-03T22:00:00.0Z");
        const vector<Result>& results(response.getResults());
        ASSERT_EQ(response.getCLTRID(), "ABC-12345");
        ASSERT_EQ(results[0].getResultCode(), 1000);
        ASSERT_EQ(results[0].getResultMessage(),
                  "Command completed successfully");
    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}
