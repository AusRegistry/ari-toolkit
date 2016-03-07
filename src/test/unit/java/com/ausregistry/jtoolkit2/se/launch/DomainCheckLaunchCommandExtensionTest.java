package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


public class DomainCheckLaunchCommandExtensionTest {


    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionWithTypeClaims() throws SAXException {

        final Command cmd = new DomainCheckCommand("domain-name.unit-test");
        final DomainCheckLaunchCommandExtension ext = new DomainCheckLaunchCommandExtension();
        ext.setPhaseName("sunrise-fcfs");
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setCheckType("claims");
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<check>"
                + "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</check>"
                + "</check>"
                + "<extension><check xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" type=\"claims\">"
                + "<phase name=\"sunrise-fcfs\">sunrise</phase>"
                + "</check>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());

    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionWithTypeAvail() throws SAXException {

        final Command cmd = new DomainCheckCommand("domain-name.unit-test");
        final DomainCheckLaunchCommandExtension ext = new DomainCheckLaunchCommandExtension();
        ext.setPhaseName("sunrise-for-hometown");
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setCheckType("avail");
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<check>"
                + "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</check>"
                + "</check>"
                + "<extension><check xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" type=\"avail\">"
                + "<phase name=\"sunrise-for-hometown\">sunrise</phase>"
                + "</check>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());

    }
}

