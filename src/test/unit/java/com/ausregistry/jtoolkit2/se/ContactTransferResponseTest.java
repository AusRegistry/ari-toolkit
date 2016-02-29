package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class ContactTransferResponseTest {
    private static final String XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:trnData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>sh8013</contact:id><contact:trStatus>pending</contact:trStatus><contact:reID>ClientX</contact:reID><contact:reDate>2000-06-08T22:00:00.0Z</contact:reDate><contact:acID>ClientY</contact:acID><contact:acDate>2000-06-13T22:00:00.0Z</contact:acDate></contact:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private ContactTransferResponse response;

    @Before
    public void setUp() throws Exception {
        response = new ContactTransferResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
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
    public void testGetResults() {
        Result[] results = response.getResults();
        assertEquals(1, results.length);
        assertEquals(1000, results[0].getResultCode());
        assertEquals("Command completed successfully", results[0].getResultMessage());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }

}
