package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class AeDomainInfoResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<response><result code=\"1000\"><msg>Command completed successfully</msg></result>"
                + "<resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid>"
                + "<domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant>"
                + "<domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns>"
                + "<domain:hostObj>ns1.example.com.ae</domain:hostObj>"
                + "<domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns>"
                + "<domain:host>ns1.example.com.ae</domain:host>"
                + "<domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID>"
                + "<domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate>"
                + "<domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo>"
                + "<domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData>"
                + "<extension><aeext:infData xmlns:aeext=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\">"
                + "<aeext:aeProperties><aeext:registrantName>RegistrantName Pty. Ltd.</aeext:registrantName>"
                + "<aeext:registrantID type=\"Trade License\">123456789</aeext:registrantID>"
                + "<aeext:eligibilityType>Trademark</aeext:eligibilityType>"
                + "<aeext:eligibilityName>Registrant Eligi</aeext:eligibilityName>"
                + "<aeext:eligibilityID type=\"Trademark\">987654321</aeext:eligibilityID>"
                + "<aeext:policyReason>2</aeext:policyReason></aeext:aeProperties></aeext:infData>"
                + "</extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

    private AeDomainInfoResponse response;

    @Before
    public void setUp() throws Exception {
        response = new AeDomainInfoResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @Test
    public void testGetAeRegistrantID() {
        assertEquals("123456789", response.getAeRegistrantID());
    }

    @Test
    public void testGetRegistrantName() {
        assertEquals("RegistrantName Pty. Ltd.", response.getRegistrantName());
    }

    @Test
    public void testGetRegistrantIDType() {
        assertEquals("Trade License", response.getRegistrantIDType());
    }

    @Test
    public void testGetEligibilityType() {
        assertEquals("Trademark", response.getEligibilityType());
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
        assertEquals("Trademark", response.getEligibilityIDType());
    }

    @Test
    public void testGetPolicyReason() {
        assertEquals(2, response.getPolicyReason());
    }

    @Test
    public void testGetName() {
        assertEquals("example.com.ae", response.getName());
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
