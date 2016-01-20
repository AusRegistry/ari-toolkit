package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.app.DomainCreateApplicationCommandExtension;
import com.ausregistry.jtoolkit2.se.tmch.TmchDomainCreateCommandExtension;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import com.ausregistry.jtoolkit2.se.launch.DomainCreateLaunchCommandExtension.*;
import static org.junit.Assert.assertEquals;


public class DomainCreateLaunchCommandExtensionTest{


        private static final XMLParser PARSER = new XMLParser();

        @Before
        public void setUp() throws Exception {
            Timer.setTime("20070101.010101");
            CLTRID.setClID("JTKUTEST");
        }

        @Test
        public void shouldCreateValidXmlWhenSupplyLaunchExtension() throws SAXException {

            final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
            final DomainCreateLaunchCommandExtension ext = new DomainCreateLaunchCommandExtension();
            ext.setPhaseName("sunrise-fcfs");
            ext.setPhaseType(PhaseType.SUNRISE);
            ext.setEncodedSignedMarkData("dummyString");
            ext.setLaunchCreateType(LaunchCreateType.REGISTRATION);
            cmd.appendExtension(ext);
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                    + "<extension><create xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" type=\"registration\">"
                    + "<phase name=\"sunrise-fcfs\">sunrise</phase>"
                    + "<encodedSignedMark xmlns=\"urn:ietf:params:xml:ns:signedMark-1.0\">dummyString"
                    + "</encodedSignedMark></create>"
                    + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

        }

    @Test
    public void shouldCreateDefaultNoNamePhaseWhenNotProvidingPhaseName() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateLaunchCommandExtension ext = new DomainCreateLaunchCommandExtension();
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setEncodedSignedMarkData("dummyString");
        ext.setLaunchCreateType(LaunchCreateType.REGISTRATION);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension><create xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" type=\"registration\">"
                + "<phase>sunrise</phase>"
                + "<encodedSignedMark xmlns=\"urn:ietf:params:xml:ns:signedMark-1.0\">dummyString"
                + "</encodedSignedMark></create>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }
}

