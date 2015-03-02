package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.util.GregorianCalendar;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BlockedCreateCommandTest {
    public static final String DOMAIN_NAME = "jtkutest.com.au";
    private static String registrantID = "01241326211";
    private String id = "BD-001";

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXML() {
        Command cmd = new BlockedCreateCommand(DOMAIN_NAME, id, registrantID);
        try {
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:blocked-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\"><create><create xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\"><id>" + id + "</id><name>" + DOMAIN_NAME + "</name><registrant>" + registrantID + "</registrant></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

