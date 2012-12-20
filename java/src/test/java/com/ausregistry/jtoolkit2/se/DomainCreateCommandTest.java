package com.ausregistry.jtoolkit2.se;

import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DomainCreateCommandTest {

    @Before
    public void setUp() throws Exception {
		Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainCreateCommandIdnaWithLanguage() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIdnaCommandExtension idnExt = new DomainCreateIdnaCommandExtension("jtkutest.com.au", "test");
        cmd.appendExtension(idnExt);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:idnadomain-1.0\"><userForm language=\"test\">jtkutest.com.au</userForm></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
    
    @Test
    public void testDomainCreateCommandStringStringString() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainCreateCommandStringStringStringStringArray() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON", "JTKCON2"});
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON</contact><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainCreateCommandStringStringStringStringArrayStringArray() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON", "JTKCON2"},
                new String[] {"ns1.jtkutest.com", "ns2.jtkutest.com"});
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><ns><hostObj>ns1.jtkutest.com</hostObj><hostObj>ns2.jtkutest.com</hostObj></ns><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON</contact><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainCreateCommandStringStringStringStringArrayStringArrayStringArrayStringArrayPeriod() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON", "JTKCON2"},
                null, new String[] {"JTKCON3"},
                new String[] {"ns1.jtkutest.com", "ns2.jtkutest.com"},
                new Period(6));
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><period unit=\"y\">6</period><ns><hostObj>ns1.jtkutest.com</hostObj><hostObj>ns2.jtkutest.com</hostObj></ns><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON</contact><contact type=\"tech\">JTKCON2</contact><contact type=\"billing\">JTKCON3</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

