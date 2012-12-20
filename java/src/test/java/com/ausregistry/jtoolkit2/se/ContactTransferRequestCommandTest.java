package com.ausregistry.jtoolkit2.se;

import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContactTransferRequestCommandTest {
    private static final String clID = "JTKUTEST";
    private static final String id1 = "JTKCON1";
    private static final String id1pw = "jtkcon1pw";
    private Command cmd;

    @Before
    public void setUp() throws Exception {
		Timer.setTime("20070101.010101");
        CLTRID.setClID(clID);
        cmd = new ContactTransferRequestCommand(id1, id1pw);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContactTransferRequestCommand() {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><transfer op=\"request\"><transfer xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON1</id><authInfo><pw>jtkcon1pw</pw></authInfo></transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
        try {
            String actual = cmd.toXML();
            assertEquals(expected, actual);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

