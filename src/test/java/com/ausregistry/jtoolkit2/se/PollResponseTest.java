package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class PollResponseTest {
    private static final String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg>Transfer requested.</msg></msgQ><resData><domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:trStatus>pending</domain:trStatus><domain:reID>ClientX</domain:reID><domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate><domain:acID>ClientY</domain:acID><domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate><domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate></domain:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String xml2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg lang=\"en\">Transfer requested.</msg></msgQ><resData><contact:trnData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>JTKUTEST</contact:id><contact:trStatus>pending</contact:trStatus><contact:reID>ClientX</contact:reID><contact:reDate>2000-06-08T22:00:00.0Z</contact:reDate><contact:acID>ClientY</contact:acID><contact:acDate>2000-06-13T22:00:00.0Z</contact:acDate></contact:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String xml3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg lang=\"en\">Host updated by TRO.</msg></msgQ><resData><host:infData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><host:name>ns1.example.com</host:name><host:roid>NS1_EXAMPLE1-REP</host:roid><host:status s=\"linked\"/><host:status s=\"clientUpdateProhibited\"/><host:addr ip=\"v4\">192.0.2.2</host:addr><host:addr ip=\"v4\">192.0.2.29</host:addr><host:addr ip=\"v6\">1080:0:0:0:8:800:200C:417A</host:addr><host:clID>ClientY</host:clID><host:crID>ClientX</host:crID><host:crDate>1999-04-03T22:00:00.0Z</host:crDate><host:upID>ClientX</host:upID><host:upDate>1999-12-03T09:00:00.0Z</host:upDate><host:trDate>2000-04-08T09:00:00.0Z</host:trDate></host:infData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static PollResponse response1, response2, response3;

    static {
        response1 = new PollResponse();
        final XMLParser parser = new XMLParser();
        XMLDocument doc;
        try {
            doc = parser.parse(xml1);
            response1.fromXML(doc);

            response2 = new PollResponse();
            doc = parser.parse(xml2);
            response2.fromXML(doc);

            response3 = new PollResponse();
            doc = parser.parse(xml3);
            response3.fromXML(doc);
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContactTransferResponse() {
        final ContactTransferResponse ctr = response2.getContactTransferResponse();
        assertNotNull(ctr);
        assertEquals("JTKUTEST", ctr.getID());
        assertEquals("pending", ctr.getTransferStatus());
        assertEquals("ClientX", ctr.getRequestingClID());
        assertEquals("ClientY", ctr.getActioningClID());
    }

    @Test
    public void testGetDomainTransferResponse() {
        final DomainTransferResponse dtr = response1.getDomainTransferResponse();
        assertNotNull(dtr);
        assertEquals("example.com", dtr.getName());
        assertEquals("pending", dtr.getTransferStatus());
        assertEquals("ClientX", dtr.getRequestingClID());
        assertEquals("ClientY", dtr.getActioningClID());
    }

    @Test
    public void testGetResults() {
        final Result[] results1 = response1.getResults();
        final Result[] results2 = response2.getResults();
        final Result[] results3 = response3.getResults();
        assertEquals(1, results1.length);
        assertEquals(1, results2.length);
        assertEquals(1, results3.length);
        assertEquals(1301, results1[0].getResultCode());
        assertEquals(1301, results2[0].getResultCode());
        assertEquals(1301, results3[0].getResultCode());
        assertEquals("Command completed successfully; ack to dequeue", results1[0].getResultMessage());
        assertEquals("Command completed successfully; ack to dequeue", results2[0].getResultMessage());
        assertEquals("Command completed successfully; ack to dequeue", results3[0].getResultMessage());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response1.getCLTRID());
        assertEquals("ABC-12345", response2.getCLTRID());
        assertEquals("ABC-12345", response3.getCLTRID());
    }

    @Test
    public void testGetMessageEnqueueDate() {
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), response1.getMessageEnqueueDate());
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), response2.getMessageEnqueueDate());
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), response3.getMessageEnqueueDate());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Transfer requested.", response1.getMessage());
        assertEquals("Transfer requested.", response2.getMessage());
        assertEquals("Host updated by TRO.", response3.getMessage());
    }

    @Test
    public void testGetMessageLanguage() {
        assertEquals("en", response2.getMessageLanguage());
    }

    @Test
    public void testGetMsgCount() {
        assertEquals("Require 5 messages", 5, response1.getMsgCount());
        assertEquals("Require 5 messages", 5, response2.getMsgCount());
        assertEquals("Require 5 messages", 5, response3.getMsgCount());
    }

    @Test
    public void testGetMsgID() {
        assertEquals("12345", response1.getMsgID());
        assertEquals("12345", response2.getMsgID());
        assertEquals("12345", response3.getMsgID());
    }

    @Test
    public void testGetHostUpdateResponse() {
        final HostInfoResponse hostInfoResponse = response3.getHostInfoResponse();
        assertNotNull(hostInfoResponse);
        assertEquals("ns1.example.com", hostInfoResponse.getName());
        assertEquals("NS1_EXAMPLE1-REP", hostInfoResponse.getROID());
        assertEquals("linked", hostInfoResponse.getStatuses()[0].toString());
        assertEquals("clientUpdateProhibited", hostInfoResponse.getStatuses()[1].toString());
        assertEquals("v4", hostInfoResponse.getAddresses()[0].getVersion());
        assertEquals("192.0.2.2", hostInfoResponse.getAddresses()[0].getTextRep());
        assertEquals("v4", hostInfoResponse.getAddresses()[1].getVersion());
        assertEquals("192.0.2.29", hostInfoResponse.getAddresses()[1].getTextRep());
        assertEquals("v6", hostInfoResponse.getAddresses()[2].getVersion());
        assertEquals("1080:0:0:0:8:800:200C:417A", hostInfoResponse.getAddresses()[2].getTextRep());
        assertEquals("ClientY", hostInfoResponse.getSponsorClient());
        assertEquals("ClientX", hostInfoResponse.getCreateClient());
        assertEquals("ClientX", hostInfoResponse.getUpdateClient());
    }
}
