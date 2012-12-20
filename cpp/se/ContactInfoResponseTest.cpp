#include "se/ContactInfoResponse.hpp"
#include "se/IntPostalInfo.hpp"
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
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:infData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>sh8013</contact:id><contact:roid>SH8013-REP</contact:roid><contact:status s=\"linked\"/><contact:status s=\"clientDeleteProhibited\"/><contact:postalInfo type=\"int\"><contact:name>John Doe</contact:name><contact:org>Example Inc.</contact:org><contact:addr><contact:street>123 Example Dr.</contact:street><contact:street>Suite 100</contact:street><contact:city>Dulles</contact:city><contact:sp>VA</contact:sp><contact:pc>20166-6503</contact:pc><contact:cc>US</contact:cc></contact:addr></contact:postalInfo><contact:voice x=\"1234\">+1.7035555555</contact:voice><contact:fax>+1.7035555556</contact:fax><contact:email>jdoe@example.com</contact:email><contact:clID>ClientY</contact:clID><contact:crID>ClientX</contact:crID><contact:crDate>1999-04-03T22:00:00.0Z</contact:crDate><contact:upID>ClientX</contact:upID><contact:upDate>1999-12-03T09:00:00.0Z</contact:upDate><contact:trDate>2000-04-08T09:00:00.0Z</contact:trDate><contact:authInfo><contact:pw>2fooBAR</contact:pw></contact:authInfo></contact:infData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>");

    ContactInfoResponse response;
    XMLParser parser;
    std::auto_ptr<XMLDocument> doc(parser.parse(xml));
    response.fromXML(doc.get());

    {
        vector<string> streets = response.getIntPostalInfo().getStreet();

        ASSERT_EQ(streets[0], "123 Example Dr.");
    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}
