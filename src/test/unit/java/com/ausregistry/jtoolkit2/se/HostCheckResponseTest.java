package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class HostCheckResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><host:chkData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><host:cd><host:name avail=\"1\">ns1.example.com</host:name></host:cd><host:cd><host:name avail=\"0\">ns2.example2.com</host:name><host:reason>In use</host:reason></host:cd><host:cd><host:name avail=\"1\">ns3.example3.com</host:name></host:cd></host:chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private HostCheckResponse response;

    @Before
    public void setUp() throws Exception {
        response = new HostCheckResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIsAvailable() {
        assertTrue(response.isAvailable("ns1.example.com"));
        assertFalse(response.isAvailable("ns2.example2.com"));
        assertTrue(response.isAvailable("ns3.example3.com"));
    }

    @Test
    public void testGetReasonString() {
        assertEquals("In use", response.getReason("ns2.example2.com"));
    }

    @Test
    public void testGetReasonInt() {
        assertEquals("In use", response.getReason(1));
    }

    @Test
    public void testGetAvailableList() {
        boolean[] availList = response.getAvailableList();
        assertTrue(availList[0]);
        assertFalse(availList[1]);
        assertTrue(availList[2]);
    }

    @Test
    public void testGetReasonList() {
        String[] reasonList = response.getReasonList();
        assertEquals("In use", reasonList[1]);
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", response.getCLTRID());
    }
}
