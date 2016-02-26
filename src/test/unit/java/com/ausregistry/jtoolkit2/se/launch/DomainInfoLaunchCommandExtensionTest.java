package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainInfoCommand;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


public class DomainInfoLaunchCommandExtensionTest {


    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtension() throws SAXException {

        final Command cmd = new DomainInfoCommand("domain-name.unit-test");
        final DomainInfoLaunchCommandExtension ext = new DomainInfoLaunchCommandExtension();
        ext.setPhaseName("sunrise-fcfs");
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setApplicationID("myApp");
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<info>"
                + "<info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</info>"
                + "</info>"
                + "<extension><info xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" includeMark=\"false\">"
                + "<phase name=\"sunrise-fcfs\">sunrise</phase>"
                + "<applicationID>myApp</applicationID>"
                + "</info>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionWithNoApplicationID() throws SAXException {

        final Command cmd = new DomainInfoCommand("domain-name.unit-test");
        final DomainInfoLaunchCommandExtension ext = new DomainInfoLaunchCommandExtension();
        ext.setPhaseName("sunrise-fcfs");
        ext.setPhaseType(PhaseType.SUNRISE);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<info>"
                + "<info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</info>"
                + "</info>"
                + "<extension><info xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" includeMark=\"false\">"
                + "<phase name=\"sunrise-fcfs\">sunrise</phase>"
                + "</info>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionNoPhaseName() throws SAXException {

        final Command cmd = new DomainInfoCommand("domain-name.unit-test");
        final DomainInfoLaunchCommandExtension ext = new DomainInfoLaunchCommandExtension();
        ext.setPhaseType(PhaseType.SUNRISE);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<info>"
                + "<info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</info>"
                + "</info>"
                + "<extension><info xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" includeMark=\"false\">"
                + "<phase>sunrise</phase>"
                + "</info>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionIncludeMarkIsSet() throws SAXException {

        final Command cmd = new DomainInfoCommand("domain-name.unit-test");
        final DomainInfoLaunchCommandExtension ext = new DomainInfoLaunchCommandExtension();
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setIncludeMark(true);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<info>"
                + "<info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain-name.unit-test</name>"
                + "</info>"
                + "</info>"
                + "<extension><info xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" includeMark=\"true\">"
                + "<phase>sunrise</phase>"
                + "</info>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", cmd.toXML());
    }
}

