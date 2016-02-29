package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainRenewResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:renData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:exDate>2005-04-03T22:00:00.0Z</domain:exDate></domain:renData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private DomainRenewResponse response;

    @Before
    public void setUp() throws Exception {
        response = new DomainRenewResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @Test
    public void testGetName() {
        assertEquals("example.com", response.getName());
    }

    @Test
    public void testGetExpiryDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("2005-04-03T22:00:00.0Z"),
                response.getExpiryDate());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }
}
