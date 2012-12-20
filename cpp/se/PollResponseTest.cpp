#include "se/PollResponse.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "xml/XMLParser.hpp"
#include "session/Timer.hpp"
#include "common/init.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

void testContactTransferApprovePoll();
void testDomainTransferApprovePoll();
PollResponse& getPollResponse(const string xml);

void doWork()
{
    init("etc/toolkit2.conf");

    testContactTransferApprovePoll();
    testDomainTransferApprovePoll();
}

void testContactTransferApprovePoll()
{
    const string xml = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg lang=\"en\">Transfer requested.</msg></msgQ><resData><contact:trnData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\"><contact:id>JTKUTEST</contact:id><contact:trStatus>pending</contact:trStatus><contact:reID>ClientX</contact:reID><contact:reDate>2000-06-08T22:00:00.0Z</contact:reDate><contact:acID>ClientY</contact:acID><contact:acDate>2000-06-13T22:00:00.0Z</contact:acDate></contact:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";

    PollResponse& response(getPollResponse(xml));

    const ContactTransferResponse* ctr = response.getContactTransferResponse();
    ASSERT(ctr != NULL);
    ASSERT_EQ("JTKUTEST", ctr->getID());
    ASSERT_EQ("pending", ctr->getTransferStatus());
    ASSERT_EQ("ClientX", ctr->getRequestingClID());
    ASSERT_EQ("ClientY", ctr->getActioningClID());

    const vector<Result>& results(response.getResults());
    ASSERT_EQ(1, results.size());
    ASSERT_EQ(1301, results[0].getResultCode());
    ASSERT_EQ(
            "Command completed successfully; ack to dequeue",
            results[0].getResultMessage());

    ASSERT_EQ("ABC-12345", response.getCLTRID());
    const XMLGregorianCalendar *qDate1 = response.getMessageEnqueueDate();
    std::string res = EPPDateFormatter::toXSDateTime(*qDate1);
    ASSERT_EQ(res, "2000-06-08T22:00:00.0Z");

    ASSERT_EQ("Transfer requested.", response.getMessage());
    ASSERT_EQ("en", response.getMessageLanguage());
    ASSERT_EQ(5, response.getMsgCount());
    ASSERT_EQ("12345", response.getMsgID());
}

void testDomainTransferApprovePoll()
{
    const string xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg>Transfer requested.</msg></msgQ><resData><domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:trStatus>pending</domain:trStatus><domain:reID>ClientX</domain:reID><domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate><domain:acID>ClientY</domain:acID><domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate><domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate></domain:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";

    PollResponse& response(getPollResponse(xml));

    const DomainTransferResponse* dtr = response.getDomainTransferResponse();
    ASSERT(dtr != NULL);
    ASSERT_EQ("example.com", dtr->getName());
    ASSERT_EQ("pending", dtr->getTransferStatus());
    ASSERT_EQ("ClientX", dtr->getRequestingClID());
    ASSERT_EQ("ClientY", dtr->getActioningClID());

    const vector<Result>& results(response.getResults());
    ASSERT_EQ(1, results.size());
    ASSERT_EQ(1301, results[0].getResultCode());
    ASSERT_EQ(
            "Command completed successfully; ack to dequeue",
            results[0].getResultMessage());

    ASSERT_EQ("ABC-12345", response.getCLTRID());
    const XMLGregorianCalendar *qDate1 = response.getMessageEnqueueDate();
    std::string res = EPPDateFormatter::toXSDateTime(*qDate1);
    ASSERT_EQ(res, "2000-06-08T22:00:00.0Z");

    ASSERT_EQ("Transfer requested.", response.getMessage());
    ASSERT_EQ("en", response.getMessageLanguage());
    ASSERT_EQ(5, response.getMsgCount());
    ASSERT_EQ("12345", response.getMsgID());
}

PollResponse& getPollResponse(const string xml)
{
    PollResponse *response = new PollResponse();
    XMLParser parser;
    response->fromXML(parser.parse(xml));

    return *response;
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

