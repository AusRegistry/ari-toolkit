package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class GreetingTest {
    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><greeting><svID>Example EPP server epp.example.com</svID><svDate>2000-06-08T22:00:00.0Z</svDate><svcMenu><version>1.0</version><lang>en</lang><lang>fr</lang><objURI>urn:ietf:params:xml:ns:obj1</objURI><objURI>urn:ietf:params:xml:ns:obj2</objURI><objURI>urn:ietf:params:xml:ns:obj3</objURI><svcExtension><extURI>http://custom/obj1ext-1.0</extURI></svcExtension></svcMenu><dcp><access><all/></access><statement><purpose><admin/><prov/></purpose><recipient><ours/><public/></recipient><retention><stated/></retention></statement><statement><purpose><admin/><contact/></purpose><recipient><ours><recDesc>Mr Squiggle</recDesc></ours><public/></recipient><retention><stated/></retention></statement><expiry><absolute>2020-01-01T00:00:00.0Z</absolute></expiry></dcp></greeting></epp>";
    private Greeting greeting;

    @Before
    public void setUp() throws Exception {
        greeting = new Greeting();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        greeting.fromXML(doc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetServerID() {
        assertEquals("Example EPP server epp.example.com",
                greeting.getServerID());
    }

    @Test
    public void testGetServerDateTime() {
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"),
                greeting.getServerDateTime());
    }

    @Test
    public void testGetProtocolVersions() {
        assertArrayEquals(new String[] {
            "1.0"
        }, greeting.getProtocolVersions());
    }

    @Test
    public void testGetLanguages() {
        assertArrayEquals(new String[] {
                "en", "fr"
        }, greeting.getLanguages());
    }

    @Test
    public void testObjURIs() {
        assertArrayEquals(new String[] {
                "urn:ietf:params:xml:ns:obj1", "urn:ietf:params:xml:ns:obj2",
                "urn:ietf:params:xml:ns:obj3"
        }, greeting.objURIs());
    }

    @Test
    public void testExtURIs() {
        assertArrayEquals(new String[] {
            "http://custom/obj1ext-1.0"
        }, greeting.extURIs());
    }

    @Test
    public void testDcpAccess() {
        assertEquals("all", greeting.dcpAccess());
    }

    @Test
    public void testDcpExpiry() {
        assertEquals("absolute", greeting.dcpExpiry());
    }

    @Test
    public void testGetDataCollectionPolicyStatements() {
        Greeting.DCPStatement[] stmts = greeting.getDataCollectionPolicyStatements();
        assertEquals(2, stmts.length);
        assertArrayEquals(new String[] {
                "admin", "prov"
        }, stmts[0].getPurpose());
        assertArrayEquals(new String[] {
                "ours", "public"
        }, stmts[0].getRecipients());
        assertEquals("stated", stmts[0].getRetentionPolicy());
        assertArrayEquals(new String[] {
                "admin", "contact"
        }, stmts[1].getPurpose());
        assertArrayEquals(new String[] {
                "ours", "public"
        }, stmts[1].getRecipients());
        assertEquals("stated", stmts[1].getRetentionPolicy());
    }
}
