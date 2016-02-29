package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCheckResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:chkData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:cd><domain:name avail=\"1\">example.com</domain:name></domain:cd><domain:cd><domain:name avail=\"0\">example.net</domain:name><domain:reason>In use</domain:reason></domain:cd><domain:cd><domain:name avail=\"1\">example.org</domain:name></domain:cd></domain:chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private DomainCheckResponse response;

    @Before
    public void setUp() throws Exception {
        response = new DomainCheckResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIsAvailable() {
        assertEquals(response.isAvailable("example.com"), true);
    }

    @Test
    public void testGetReasonString() {
        assertEquals(response.getReason("example.net"), "In use");
    }

    @Test
    public void testGetReasonInt() {
        assertEquals("In use", response.getReason(1));
    }

    @Test
    public void testGetAvailableList() {
        assertEquals(3, response.getAvailableList().length);
    }

    @Test
    public void testGetReasonList() {
        assertEquals(3, response.getReasonList().length);
    }

    @Test
    public void testGetResults() {
        assertEquals(1000, response.getResults()[0].getResultCode());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }

}
