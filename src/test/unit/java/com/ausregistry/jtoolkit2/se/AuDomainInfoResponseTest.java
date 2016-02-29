package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class AuDomainInfoResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<response><result code=\"1000\"><msg>Command completed successfully</msg>"
                + "</result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<domain:name>example.com.au</domain:name><domain:roid>D0000003-AR</domain:roid>"
                + "<domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant>"
                + "<domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns>"
                + "<domain:hostObj>ns1.example.com.au</domain:hostObj>"
                + "<domain:hostObj>ns2.example.com.au</domain:hostObj>"
                + "</domain:ns><domain:host>ns1.example.com.au</domain:host>"
                + "<domain:host>ns2.exmaple.com.au</domain:host>"
                + "<domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID>"
                + "<domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate>"
                + "<domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo>"
                + "<domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData>"
                + "</resData><extension><auext:infData xmlns:auext=\"urn:X-au:params:xml:ns:auext-1.2\" "
                + "xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\">"
                + "<auext:auProperties><auext:registrantName>RegistrantName Pty. Ltd.</auext:registrantName>"
                + "<auext:registrantID type=\"ACN\">123456789</auext:registrantID>"
                + "<auext:eligibilityType>Other</auext:eligibilityType>"
                + "<auext:eligibilityName>Registrant Eligi</auext:eligibilityName>"
                + "<auext:eligibilityID type=\"ABN\">987654321</auext:eligibilityID>"
                + "<auext:policyReason>2</auext:policyReason></auext:auProperties></auext:infData>"
                + "</extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

    private AuDomainInfoResponse response;

    @Before
    public void setUp() throws Exception {
        response = new AuDomainInfoResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @Test
    public void testGetAuRegistrantID() {
        assertEquals("123456789", response.getAuRegistrantID());
    }

    @Test
    public void testGetRegistrantName() {
        assertEquals("RegistrantName Pty. Ltd.", response.getRegistrantName());
    }

    @Test
    public void testGetRegistrantIDType() {
        assertEquals("ACN", response.getRegistrantIDType());
    }

    @Test
    public void testGetEligibilityType() {
        assertEquals("Other", response.getEligibilityType());
    }

    @Test
    public void testGetEligibilityName() {
        assertEquals("Registrant Eligi", response.getEligibilityName());
    }

    @Test
    public void testGetEligibilityID() {
        assertEquals("987654321", response.getEligibilityID());
    }

    @Test
    public void testGetEligibilityIDType() {
        assertEquals("ABN", response.getEligibilityIDType());
    }

    @Test
    public void testGetPolicyReason() {
        assertEquals(2, response.getPolicyReason());
    }

    @Test
    public void testGetName() {
        assertEquals("example.com.au", response.getName());
    }

    @Test
    public void testGetROID() {
        assertEquals("D0000003-AR", response.getROID());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }
}
