package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainCheckCommandTest {
    private DomainCheckCommand cmd1;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
        cmd1 = new DomainCheckCommand("test.com.au");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDomainCheckCommand() {
        try {
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>test.com.au</name></check></check><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testGetCommandType() {
        assertEquals(cmd1.getCommandType().getCommandName(), "check");
    }

    @Test
    public void testGetObjectType() {
        assertEquals(cmd1.getObjectType().getName(), "domain");
    }

    @Test
    public void testToXML() {
        try {
            assertNotNull(cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

