package com.ausregistry.jtoolkit2.se.blocked;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;

public class BlockedInfoCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXML() {
        String id = "BD-001";
        Command cmd = new BlockedInfoCommand(id);
        try {
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:X-ar:params:xml:ns:blocked-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\"><id>" + id + "</id></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

