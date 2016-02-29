package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.assertEquals;


public class DomainCreateLaunchCommandExtensionTest {
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

    @Test
    public void shouldCreateValidXmlCommandWithNoticeIdWhenClaimsPhaseTypeProvided() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateLaunchCommandExtension ext = new DomainCreateLaunchCommandExtension();
        ext.setPhaseType(PhaseType.CLAIMS);
        ext.setLaunchCreateType(LaunchCreateType.REGISTRATION);
        ext.setNoticeId("49FD46E6C4B45C55D4AC");
        ext.setNotAfterDateTime(
                EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z"));
        ext.setAcceptedDateTime(
                EPPDateFormatter.fromXSDateTime("2007-02-02T02:02:02.0Z"));

        cmd.appendExtension(ext);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension>"
                + "<create xmlns=\"urn:ietf:params:xml:ns:launch-1.0\" type=\"registration\">"
                + "<phase>claims</phase>"
                + "<notice>"
                + "<noticeID>49FD46E6C4B45C55D4AC</noticeID>"
                + "<notAfter>2007-01-01T01:01:01.000Z</notAfter>"
                + "<acceptedDate>2007-02-02T02:02:02.000Z</acceptedDate>"
                + "</notice>"
                + "</create>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

    @Test
    public void shouldCreateValidXmlCommandWithNoTypeAttributeProvided() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateLaunchCommandExtension ext = new DomainCreateLaunchCommandExtension();
        ext.setPhaseType(PhaseType.CLAIMS);
        ext.setNoticeId("49FD46E6C4B45C55D4AC");
        ext.setNotAfterDateTime(
                EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z"));
        ext.setAcceptedDateTime(
                EPPDateFormatter.fromXSDateTime("2007-02-02T02:02:02.0Z"));

        cmd.appendExtension(ext);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension>"
                + "<create xmlns=\"urn:ietf:params:xml:ns:launch-1.0\">"
                + "<phase>claims</phase>"
                + "<notice>"
                + "<noticeID>49FD46E6C4B45C55D4AC</noticeID>"
                + "<notAfter>2007-01-01T01:01:01.000Z</notAfter>"
                + "<acceptedDate>2007-02-02T02:02:02.000Z</acceptedDate>"
                + "</notice>"
                + "</create>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }
}

