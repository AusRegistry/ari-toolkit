#include "se/DomainUpdateCommand.hpp"
#include "se/DomainUpdateSyncCommandExtension.hpp"
#include "se/CLTRID.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/DomainAdd.hpp"
#include "se/Status.hpp"
#include "se/DomainRem.hpp"
#include "se/IllegalArgException.hpp"
#include "xml/XMLParser.hpp"
#include "common/init.hpp"
#include "session/Timer.hpp"
#include "common/Test.hpp"
#include "common/ErrorPkg.hpp"

#include <iostream>

using namespace std;

void testSimpleUpdate()
{
    Timer::setTime("20070101.010101");
    CLTRID::setClID("JTKUTEST");

    DomainUpdateCommand cmd("jtkutest.com.au");
    const string xml(cmd.toXML());
    ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");

}

void testFullUpdate()
{
    Timer::setTime("20070101.010101");
    CLTRID::setClID("JTKUTEST");

    const char* addNsText[] = { "ns1.jtkutest.com.au", "ns2.jtkutest.com.au" };
    const vector<string> addNs(addNsText, addNsText + 2);

    const vector<string> addTechContacts(1, "JTKCON");
    const vector<string> addAdminContacts(1, "JTKCON2");
    const vector<string> addBillingContacts(1, "JTKCON3");


    vector<Status> addStatus;
    addStatus.push_back(Status("clientHold", "non-payment"));

    // Could take the address of temporaries here since we 'know' DomainAdd
    // only dereferences during construction and does so to deep copy, but
    // this is more conventional.
    const DomainAdd add(
        &addNs,
        &addTechContacts,
        &addAdminContacts,
        &addBillingContacts,
        &addStatus);


    const char* remNsText[] = { "ns3.jtkutest.com.au", "ns4.jtkutest.com.au" };
    const vector<string> remNs(remNsText, remNsText + 2);

    const vector<string> remTechContacts(1, "JTKCON2");
    const vector<string> remAdminContacts(1, "JTKCON");

    const char* remStatusText[] = { "clientDeleteProhibited" };
    const vector<Status> remStatus(remStatusText, remStatusText + 1 );

    const DomainRem rem(
            &remNs,
            &remTechContacts,
            &remAdminContacts,
            NULL,
            &remStatus);

    const string name("jtkutest.com.au");
    const string pw("jtkUT3st");
    const string registrantID("JTKCON");

    DomainUpdateCommand cmd("jtkutest.com.au", &pw, &add, &rem, &registrantID);

    const string xml(cmd.toXML());
    ASSERT_EQ(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><add><ns><hostObj>ns1.jtkutest.com.au</hostObj><hostObj>ns2.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"billing\">JTKCON3</contact><status s=\"clientHold\">non-payment</status></add><rem><ns><hostObj>ns3.jtkutest.com.au</hostObj><hostObj>ns4.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON2</contact><contact type=\"admin\">JTKCON</contact><status s=\"clientDeleteProhibited\"></status></rem><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
}

void testSyncExpiryDateExtension(void)
{
    Timer::setTime("20070101.010101");
    CLTRID::setClID("JTKUTEST");

    DomainUpdateCommand cmd("jtkutest.com.au");
    XMLGregorianCalendar *newExpiryDate = EPPDateFormatter::fromXSDateTime("2005-04-03T22:00:00.0Z");

    DomainUpdateSyncCommandExtension extension(newExpiryDate);
    cmd.appendExtension(extension);
    const string xml(cmd.toXML());

    delete newExpiryDate;
    ASSERT_EQ(xml,
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:sync-1.0\"><exDate>2005-04-03T22:00:00.0Z</exDate></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>");
}

void testSyncExpiryDateExtensionWithNullDate(void)
{
    try
    {
        DomainUpdateSyncCommandExtension extension(NULL);
        FAIL("Null exDate should have generated an exception");
    }
    catch (IllegalArgException &e)
    {
        ASSERT_EQ(e.getMessage(),
                ErrorPkg::getMessage("se.domain.update.sync.exDate.missing"));
    }
}

int main(int argc, char* argv[])
{
    init("etc/toolkit2.conf");
	TEST_run(testSimpleUpdate);
	TEST_run(testFullUpdate);
	TEST_run(testSyncExpiryDateExtension);
	TEST_run(testSyncExpiryDateExtensionWithNullDate);
	return TEST_errorCount();
}
