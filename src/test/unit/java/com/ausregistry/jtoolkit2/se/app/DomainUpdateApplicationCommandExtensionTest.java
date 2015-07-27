package com.ausregistry.jtoolkit2.se.app;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainUpdateApplicationCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyApplicationExtension() throws SAXException {

        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, null);
        final DomainUpdateApplicationCommandExtension ext = new DomainUpdateApplicationCommandExtension();
        ext.setApplicationId("applicationId");

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><chg><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update>"
                + "<extension><update xmlns=\"urn:ar:params:xml:ns:application-1.0\"><id>applicationId</id></update>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }


}
