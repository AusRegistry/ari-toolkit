package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainTransferResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1001\"><msg>Command completed successfully; action pending</msg></result><resData><domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:trStatus>pending</domain:trStatus><domain:reID>ClientX</domain:reID><domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate><domain:acID>ClientY</domain:acID><domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate><domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate></domain:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private DomainTransferResponse response;

    @Before
    public void setUp() throws Exception {
        response = new DomainTransferResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetExpiryDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("2002-09-08T22:00:00.0Z"),
                response.getExpiryDate());
    }

    @Test
    public void testGetTransferStatus() {
        assertEquals("pending", response.getTransferStatus());
    }

    @Test
    public void testGetRequestingClID() {
        assertEquals("ClientX", response.getRequestingClID());
    }

    @Test
    public void testGetRequestDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"),
                response.getRequestDate());
    }

    @Test
    public void testGetActioningClID() {
        assertEquals("ClientY", response.getActioningClID());
    }

    @Test
    public void testGetActionDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("2000-06-13T22:00:00.0Z"),
                response.getActionDate());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }

}
