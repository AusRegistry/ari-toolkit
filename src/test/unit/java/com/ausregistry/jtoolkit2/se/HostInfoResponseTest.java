package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class HostInfoResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testGetName() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("ns1.example.com", response.getName());
    }

    @Test
    public void testGetAddresses() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        final InetAddress[] addrs = response.getAddresses();
        assertNotNull(addrs);
        assertEquals(3, addrs.length);
        assertEquals("192.0.2.2", addrs[0].getTextRep());
        assertEquals("192.0.2.29", addrs[1].getTextRep());
        assertEquals("1080:0:0:0:8:800:200C:417A", addrs[2].getTextRep());
        assertEquals("v4", addrs[0].getVersion());
        assertEquals("v4", addrs[1].getVersion());
        assertEquals("v6", addrs[2].getVersion());
    }

    @Test
    public void testGetROID() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("NS1_EXAMPLE1-REP", response.getROID());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals(EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"), response.getCreateDate());
    }

    @Test
    public void testGetUpdateDate() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals(EPPDateFormatter.fromXSDateTime("1999-12-03T09:00:00.0Z"), response.getUpdateDate());
    }

    @Test
    public void testGetCreateClient() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("ClientX", response.getCreateClient());
    }

    @Test
    public void testGetUpdateClient() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("ClientX", response.getUpdateClient());
    }

    @Test
    public void testGetSponsorClient() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("ClientY", response.getSponsorClient());
    }

    @Test
    public void testGetStatuses() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        final Status[] statuses = response.getStatuses();
        assertEquals(2, statuses.length);
        assertEquals("linked", statuses[0].toString());
        assertEquals("clientUpdateProhibited", statuses[1].toString());
    }

    @Test
    public void testGetCLTRID() throws Exception {
        final HostInfoResponse response = new HostInfoResponse();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("ns1.example.com"));
        response.fromXML(doc);
        assertEquals("ABC-12345", response.getCLTRID());
    }

    private static String getInfoResponseExpectedXml(final String hostName) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append("<response>");
            result.append("<result code=\"1000\">");
                result.append("<msg>Command completed successfully</msg>");
            result.append("</result>");
            result.append("<resData>");
                result.append("<host:infData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\"");
                result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\">");
                    result.append("<host:name>" + hostName + "</host:name>");
                    result.append("<host:roid>NS1_EXAMPLE1-REP</host:roid>");
                    result.append("<host:status s=\"linked\"/>");
                    result.append("<host:status s=\"clientUpdateProhibited\"/>");
                    result.append("<host:addr ip=\"v4\">192.0.2.2</host:addr>");
                    result.append("<host:addr ip=\"v4\">192.0.2.29</host:addr>");
                    result.append("<host:addr ip=\"v6\">1080:0:0:0:8:800:200C:417A</host:addr>");
                    result.append("<host:clID>ClientY</host:clID>");
                    result.append("<host:crID>ClientX</host:crID>");
                    result.append("<host:crDate>1999-04-03T22:00:00.0Z</host:crDate>");
                    result.append("<host:upID>ClientX</host:upID>");
                    result.append("<host:upDate>1999-12-03T09:00:00.0Z</host:upDate>");
                    result.append("<host:trDate>2000-04-08T09:00:00.0Z</host:trDate>");
                result.append("</host:infData>");
            result.append("</resData>");
            result.append("<trID>");
                result.append("<clTRID>ABC-12345</clTRID>");
                result.append("<svTRID>54322-XYZ</svTRID>");
            result.append("</trID>");
        result.append("</response>");
        result.append("</epp>");
        return result.toString();
    }

}
