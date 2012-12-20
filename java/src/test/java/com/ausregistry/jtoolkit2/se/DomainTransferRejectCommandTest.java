package com.ausregistry.jtoolkit2.se;

import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DomainTransferRejectCommandTest {

    @Before
    public void setUp() throws Exception {
		Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainTransferRejectCommandStringString() {
        Command cmd = new DomainTransferRejectCommand("jtkutest.com.au",
                "jtkUT3st");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"reject\"><transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainTransferRejectCommandStringStringString() {
        Command cmd = new DomainTransferRejectCommand("jtkutest.com.au",
                "C100000-AR",
                "jtkUT3st");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"reject\"><transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw roid=\"C100000-AR\">jtkUT3st</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}
