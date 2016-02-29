package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

public class ContactTransferCancelCommandTest {

    private String id = "C100000-AR";

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testContactTransferCancelCommandWithIdOnly() throws SAXException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><transfer op=\"cancel\">"
                + "<transfer xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\">"
                + "<id>C100000-AR</id>"
                + "</transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

        String actual = new ContactTransferCancelCommand(id).toXML();
        assertEquals(expected, actual);
    }

    @Test
    public void testContactTransferCancelCommandWithIdAndPassword() throws SAXException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><transfer op=\"cancel\">"
                + "<transfer xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\">"
                + "<id>C100000-AR</id>"
                + "<authInfo><pw>jtkUT3st</pw></authInfo>"
                + "</transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

        String actual = new ContactTransferCancelCommand(id, "jtkUT3st").toXML();
        assertEquals(expected, actual);
    }
}

