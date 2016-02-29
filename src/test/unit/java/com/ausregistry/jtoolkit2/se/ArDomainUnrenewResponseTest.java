package com.ausregistry.jtoolkit2.se;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;


public class ArDomainUnrenewResponseTest {
    private static final String XML_1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1000\">"
                + "<msg>Command completed successfully</msg></result><resData>"
                + "<urenData xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\"><name>example.com</name>"
                + "<exDate>2005-04-03T22:00:00.0Z</exDate></urenData></resData><trID>"
                + "<clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private static final String XML_2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1001\">"
                + "<msg>Command completed successfully; action pending</msg></result><resData>"
                + "<urenData xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\"><name>example.com</name>"
                + "</urenData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>"
                + "</response></epp>";

    private ArDomainUnrenewResponse response1, response2;

    @Before
    public void setUp() throws Exception {
        response1 = new ArDomainUnrenewResponse();
        response2 = new ArDomainUnrenewResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML_1);
        response1.fromXML(doc);
        doc = parser.parse(XML_2);
        response2.fromXML(doc);
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("example.com", response1.getName());
    }

    @Test
    public void testGetExpiryDate() {
        Assert.assertEquals(
                EPPDateFormatter.fromXSDateTime("2005-04-03T22:00:00.0Z"),
                response1.getExpiryDate());
    }

    @Test
    public void testGetExpiryDateNull() {
        Assert.assertTrue(response2.getExpiryDate() == null);
    }

    @Test
    public void testGetCLTRID() {
        Assert.assertEquals("ABC-12345", response1.getCLTRID());
    }
}
