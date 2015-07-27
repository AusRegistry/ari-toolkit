package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainInfoCommand;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TmchDomainInfoCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyTmchExtension() throws SAXException {

        final Command cmd = new DomainInfoCommand("jtkutest.com.au");
        final TmchDomainInfoCommandExtension ext = new TmchDomainInfoCommandExtension();

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info " +
                "xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 " +
                "domain-1.0.xsd\"><name>jtkutest.com.au</name></info></info><extension><info " +
                "xmlns=\"urn:ar:params:xml:ns:tmch-1.0\"/></extension>" +
                "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }
}
