#include "se/AuDomainInfoResponse.hpp"
#include "xml/XMLParser.hpp"
#include "common/Test.hpp"
#include "common/init.hpp"
#include <memory>

using namespace std;

void doWork()
{
    init("etc/toolkit2.conf");
    const string xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.au</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.au</domain:hostObj><domain:hostObj>ns2.example.com.au</domain:hostObj></domain:ns><domain:host>ns1.example.com.au</domain:host><domain:host>ns2.exmaple.com.au</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><auext:infData xmlns:auext=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><auext:auProperties><auext:registrantName>RegistrantName Pty. Ltd.</auext:registrantName><auext:registrantID type=\"ACN\">123456789</auext:registrantID><auext:eligibilityType>Other</auext:eligibilityType><auext:eligibilityName>Registrant Eligi</auext:eligibilityName><auext:eligibilityID type=\"ABN\">987654321</auext:eligibilityID><auext:policyReason>2</auext:policyReason></auext:auProperties></auext:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";
    AuDomainInfoResponse response;
    try
    {
        auto_ptr<XMLParser> parser(new XMLParser);
        auto_ptr<XMLDocument> doc(parser->parse(xml));

        response.fromXML(doc.get());
        ASSERT_EQ("123456789", response.getAURegistrantID());
        ASSERT_EQ("RegistrantName Pty. Ltd.", response.getRegistrantName());
        ASSERT_EQ("ACN", response.getRegistrantIDType());
        ASSERT_EQ("Other", response.getEligibilityType());
        ASSERT_EQ("Registrant Eligi", response.getEligibilityName());
        ASSERT_EQ("987654321", response.getEligibilityID());
        ASSERT_EQ("ABN", response.getEligibilityIDType());
        ASSERT_EQ(2, response.getPolicyReason());
        ASSERT_EQ("example.com.au", response.getName());
        ASSERT_EQ("D0000003-AR", response.getROID());
        ASSERT_EQ("ABC-12345", response.getCLTRID());
    }
    catch (EPPException& e)
    {
        FAIL(e.getMessage());
    }
}

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

