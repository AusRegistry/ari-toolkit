package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class ContactCheckResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:chkData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:cd><contact:id avail=\"1\">sh8013</contact:id></contact:cd><contact:cd><contact:id avail=\"0\">sah8013</contact:id><contact:reason>In use</contact:reason></contact:cd><contact:cd><contact:id avail=\"1\">8013sah</contact:id></contact:cd></contact:chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private ContactCheckResponse response;

    @Before
    public void setUp() throws Exception {
        response = new ContactCheckResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIsAvailable() {
        assertTrue(response.isAvailable("sh8013"));
        assertFalse(response.isAvailable("sah8013"));
        assertTrue(response.isAvailable("8013sah"));
    }

    @Test
    public void testGetReasonString() {
        assertEquals("In use", response.getReason("sah8013"));
    }

    @Test
    public void testGetReasonInt() {
        assertEquals("In use", response.getReason(1));
    }

    @Test
    public void testGetAvailableList() {
        boolean[] availList = response.getAvailableList();
        assertEquals(3, availList.length);
        assertTrue(availList[0]);
        assertFalse(availList[1]);
        assertTrue(availList[2]);
    }

    @Test
    public void testGetReasonList() {
        String[] reasonList = response.getReasonList();
        assertEquals(3, reasonList.length);
        assertEquals("In use", reasonList[1]);
    }

}
