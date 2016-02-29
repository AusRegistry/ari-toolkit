package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class ContactInfoResponseTest {
    private static final String XML_1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:infData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>sh8013</contact:id><contact:roid>SH8013-REP</contact:roid><contact:status s=\"linked\"/><contact:status s=\"clientDeleteProhibited\"/><contact:postalInfo type=\"int\"><contact:name>John Doe</contact:name><contact:org>Example Inc.</contact:org><contact:addr><contact:street>123 Example Dr.</contact:street><contact:street>Suite 100</contact:street><contact:city>Dulles</contact:city><contact:sp>VA</contact:sp><contact:pc>20166-6503</contact:pc><contact:cc>US</contact:cc></contact:addr></contact:postalInfo><contact:voice x=\"1234\">+1.7035555555</contact:voice><contact:fax>+1.7035555556</contact:fax><contact:email>jdoe@example.com</contact:email><contact:clID>ClientY</contact:clID><contact:crID>ClientX</contact:crID><contact:crDate>1999-04-03T22:00:00.0Z</contact:crDate><contact:upID>ClientX</contact:upID><contact:upDate>1999-12-03T09:00:00.0Z</contact:upDate><contact:trDate>2000-04-08T09:00:00.0Z</contact:trDate><contact:authInfo><contact:pw>2fooBAR</contact:pw></contact:authInfo><contact:disclose flag=\"0\"><contact:voice/><contact:email/></contact:disclose></contact:infData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private static final String XML_2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result><resData><contact:infData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>C0573762-AR</contact:id><contact:roid>C0573762-AR</contact:roid><contact:status s=\"linked\"/><contact:status s=\"ok\"/><contact:postalInfo type=\"int\"><contact:name>Dominic Main</contact:name><contact:org>NetRegistry Ltd</contact:org><contact:addr><contact:street>97 Rose Street</contact:street><contact:street>Chippendale</contact:street><contact:city>Chippendale</contact:city><contact:sp>NSW</contact:sp><contact:pc>2008</contact:pc><contact:cc>au</contact:cc></contact:addr></contact:postalInfo><contact:voice>+61.296996099</contact:voice><contact:fax>+61.296996088</contact:fax><contact:email>unknown@ausregistry.com.au</contact:email><contact:clID>NetRegistry</contact:clID><contact:crID>auDA</contact:crID><contact:crDate>1998-12-01T00:00:00.0Z</contact:crDate><contact:upID>NetRegistry</contact:upID><contact:upDate>2002-08-06T02:10:27.0Z</contact:upDate><contact:authInfo><contact:pw>A00799</contact:pw></contact:authInfo></contact:infData></resData><trID><clTRID>NETREGISTRY.20070717.152924.4</clTRID><svTRID>109802</svTRID></trID></response></epp>";
    private ContactInfoResponse response;
    private XMLParser parser;

    @Before
    public void setUp() throws Exception {
        response = new ContactInfoResponse();
        parser = new XMLParser();
        XMLDocument doc = parser.parse(XML_1);
        response.fromXML(doc);
    }

    @Test
    public void testMissingDisclose() {
        XMLDocument tmpDoc;
        try {
            tmpDoc = parser.parse(XML_2);
            ContactInfoResponse tmpResponse = new ContactInfoResponse();
            tmpResponse.fromXML(tmpDoc);
        } catch (ParsingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetID() {
        assertEquals("sh8013", response.getID());
    }

    @Test
    public void testGetIntPostalInfo() {
        IntPostalInfo pi = response.getIntPostalInfo();
        assertEquals("John Doe", pi.getName());
        assertEquals("Example Inc.", pi.getOrganisation());
        assertArrayEquals(
                new String[] {"123 Example Dr.", "Suite 100"},
                pi.getStreet());
        assertEquals("Dulles", pi.getCity());
        assertEquals("VA", pi.getSp());
        assertEquals("20166-6503", pi.getPostcode());
        assertEquals("US", pi.getCountryCode());
    }

    @Test
    public void testGetLocPostalInfo() {
        assertNull(response.getLocPostalInfo());
    }

    @Test
    public void testGetVoice() {
        assertEquals("+1.7035555555", response.getVoice());
    }

    @Test
    public void testGetVoiceExtension() {
        assertEquals(1234, response.getVoiceExtension());
    }

    @Test
    public void testGetFax() {
        assertEquals("+1.7035555556", response.getFax());
    }

    @Test
    public void testGetFaxExtension() {
        assertEquals(-1, response.getFaxExtension());
    }

    @Test
    public void testGetEmail() {
        assertEquals("jdoe@example.com", response.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("2fooBAR", response.getPassword());
    }

    @Test
    public void testGetROID() {
        assertEquals("SH8013-REP", response.getROID());
    }

    @Test
    public void testGetCreateDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"),
                response.getCreateDate());
    }

    @Test
    public void testGetUpdateDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("1999-12-03T09:00:00.0Z"),
                response.getUpdateDate());
    }

    @Test
    public void testGetCreateClient() {
        assertEquals("ClientX", response.getCreateClient());
    }

    @Test
    public void testGetUpdateClient() {
        assertEquals("ClientX", response.getUpdateClient());
    }

    @Test
    public void testGetSponsorClient() {
        assertEquals("ClientY", response.getSponsorClient());
    }

    @Test
    public void testGetStatuses() {
        assertArrayEquals(
                new Status[] {
                        new Status("linked"),
                        new Status("clientDeleteProhibited")
                },
                response.getStatuses());
    }
}
