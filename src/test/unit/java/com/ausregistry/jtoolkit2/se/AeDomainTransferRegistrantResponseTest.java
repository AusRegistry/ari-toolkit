package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class AeDomainTransferRegistrantResponseTest {
    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1000\">"
            + "<msg>Command completed successfully</msg></result><resData>"
            + "<rtrnData xmlns=\"urn:X-ae:params:xml:ns:aedomain-1.0\"><name>example.com</name>"
            + "<exDate>2009-04-03T22:00:00.0Z</exDate></rtrnData></resData><trID><clTRID>ABC-12345</clTRID>"
            + "<svTRID>54321-XYZ</svTRID></trID></response></epp>";

    private AeDomainTransferRegistrantResponse response;
    private Result[] results;

    @Before
    public void setUp() throws Exception {
        response = new AeDomainTransferRegistrantResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
        results = response.getResults();
    }

    @Test
    public void testGetName() {
        assertEquals("example.com", response.getName());
    }

    @Test
    public void testGetExpiryDate() {
        assertEquals(EPPDateFormatter.fromXSDateTime("2009-04-03T22:00:00.0Z"),
                response.getExpiryDate());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }

    @Test
    public void testGetResultCode() {
        assertEquals(Integer.valueOf(1000),
                Integer.valueOf(results[0].getResultCode()));
    }

    @Test
    public void testGetResultMessage() {
        assertEquals("Command completed successfully",
                results[0].getResultMessage());
    }
}
