package com.ausregistry.jtoolkit2.se;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainNotificationResponseTest {
    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>1999-04-04T22:01:00.0Z</qDate><msg lang=\"en-AU\">Pending action completed successfully.</msg></msgQ><resData><domain:panData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"><domain:name paResult=\"1\">example.com</domain:name><domain:paTRID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></domain:paTRID><domain:paDate>1999-04-04T22:00:00.0Z</domain:paDate></domain:panData></resData><trID><clTRID>BCD-23456</clTRID><svTRID>65432-WXY</svTRID></trID></response></epp>";
    private PollResponse pollResponse;
    private DomainNotificationResponse dnResponse;

    @Before
    public void setup() throws Exception {
        pollResponse = new PollResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        pollResponse.fromXML(doc);
        dnResponse = pollResponse.getDomainNotificationResponse();
    }

    @Test
    public void testGetIdentifier() {
        Assert.assertEquals("example.com", dnResponse.getIdentifier());
    }

    @Test
    public void testGetResult() {
        Assert.assertTrue(dnResponse.getResult());
    }

    @Test
    public void testGetPaClTrID() {
        Assert.assertEquals("ABC-12345", dnResponse.getPaClTrID());
    }

    @Test
    public void testGetPaSvTrID() {
        Assert.assertEquals("54321-XYZ", dnResponse.getPaSvTrID());
    }

    @Test
    public void testGetPaDate() {
        Assert.assertEquals(EPPDateFormatter.fromXSDateTime("1999-04-04T22:00:00.0Z"),
                dnResponse.getPaDate());
    }

    @Test
    public void testGetResults() {
        Result[] results = dnResponse.getResults();

        Assert.assertEquals(Integer.valueOf(1),
                Integer.valueOf(results.length));
        Assert.assertEquals(Integer.valueOf(1301),
                Integer.valueOf(results[0].getResultCode()));
        Assert.assertEquals("Command completed successfully; ack to dequeue",
                results[0].getResultMessage());
    }

    @Test
    public void testGetCLTRID() {
        Assert.assertEquals("BCD-23456", dnResponse.getCLTRID());
    }

    @Test
    public void testGetSVTRID() {
        Assert.assertEquals("65432-WXY", dnResponse.getSVTRID());
    }

    @Test
    public void testGetMessageEnqueueDate() {
        Assert.assertEquals(EPPDateFormatter.fromXSDateTime("1999-04-04T22:01:00.0Z"),
                dnResponse.getMessageEnqueueDate());
    }

    @Test
    public void testGetMessage() {
        Assert.assertEquals("Pending action completed successfully.", dnResponse.getMessage());
    }

    @Test
    public void testGetMessageLanguage() {
        Assert.assertEquals("en-AU", dnResponse.getMessageLanguage());
    }

    @Test
    public void testGetMsgCount() {
        Assert.assertEquals(Integer.valueOf(5), Integer.valueOf(dnResponse.getMsgCount()));
    }

    @Test
    public void testGetMsgID() {
        Assert.assertEquals("12345", dnResponse.getMsgID());
    }
}
