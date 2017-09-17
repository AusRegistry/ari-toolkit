package com.ausregistry.jtoolkit2.se.unspec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainDeleteCommandUnspecExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForRservationDomain() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainDeleteCommandUnspecExtension ext = new DomainDeleteCommandUnspecExtension();
        ext.setReservationDomain(true);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("ReservationDomain=Yes");
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    private String getCommandXmlWithUnspec(String unspec) {
        String element;
        if (unspec == null) {
            element = "<unspec/>";
        } else {
            element = "<unspec>" + unspec + "</unspec>";
        }
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension>"
                + "<extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                + element
                + "</extension>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
    }

}