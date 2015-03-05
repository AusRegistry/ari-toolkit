package com.ausregistry.jtoolkit2.se.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainRenewCommand;

public class DomainRenewBlockCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXML() {
        String domainName = "jtkutest.com.au";
        String id = "BD-001";
        String currentExpiry = "2006-12-25";
        final Command cmd = new DomainRenewCommand(domainName, EPPDateFormatter.fromXSDateTime(currentExpiry));
        DomainRenewBlockCommandExtension ext = new DomainRenewBlockCommandExtension();
        ext.setId(id);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command>"
                    + "<renew>"
                    + "<renew xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                        + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>" + domainName + "</name>"
                    + "<curExpDate>" + currentExpiry + "</curExpDate>"
                    + "</renew>"
                    + "</renew>"
                    + "<extension>"
                    + "<renew xmlns=\"urn:ar:params:xml:ns:block-1.0\">"
                    + "<id>" + id + "</id>"
                    + "</renew>"
                    + "</extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command>"
                    + "</epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

