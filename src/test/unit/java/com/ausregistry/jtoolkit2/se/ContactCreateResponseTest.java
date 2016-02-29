package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

/**
 * Test each of the get methods of the ContactCreateResponse class.  Each test
 * is set up by intialising a ContactCreateResponse instance from a parsed
 * XML document with known parameter values.
 *
 * @author anthony (anthony@ausregistry.com.au)
 */
public class ContactCreateResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><contact:creData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>sh8013</contact:id><contact:crDate>1999-04-03T22:00:00.0Z</contact:crDate></contact:creData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private ContactCreateResponse response;

    @Before
    public void setUp() throws Exception {
        response = new ContactCreateResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    /**
     * Test the ContactCreateResponse.getID() method for correct return value.
     *
     */
    @Test
    public void testGetID() {
        assertEquals("sh8013", response.getID());
    }

    /**
     * Test the ContactCreateResponse.getCreateDate() method for correct
     * return value.
     */
    @Test
    public void testGetCreateDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"),
                response.getCreateDate());
    }
}

