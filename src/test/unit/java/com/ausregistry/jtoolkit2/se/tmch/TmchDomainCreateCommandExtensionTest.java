package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

public class TmchDomainCreateCommandExtensionTest {

    private static final String DOMAIN_CREATE_WITH_TMCH_SMD_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>tmch.example</name>"
            + "<authInfo><pw>jtkUT3st</pw></authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:tmch-1.0\">"
            + "<smd>ZW5jb2RlZFNpZ25lZE1hcmtEYXRh</smd>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";


    private static final String DOMAIN_CREATE_WITH_TMCH_NOTICE_ID_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>tmch.example</name>"
            + "<authInfo><pw>jtkUT3st</pw></authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:tmch-1.0\">"
            + "<noticeID>49FD46E6C4B45C55D4AC</noticeID>"
            + "<notAfter>2007-01-01T01:01:01.000Z</notAfter>"
            + "<accepted>2007-02-02T02:02:02.000Z</accepted>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DomainCreateCommand domainCreateCommand;
    private TmchDomainCreateCommandExtension tmchDomainCreateCommandExtension;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainCreateCommand = new DomainCreateCommand("tmch.example", "jtkUT3st");
        tmchDomainCreateCommandExtension = new TmchDomainCreateCommandExtension();
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyingTmchExtensionWithSMD() throws SAXException {
        tmchDomainCreateCommandExtension.setEncodedSignedMarkData("ZW5jb2RlZFNpZ25lZE1hcmtEYXRh");
        domainCreateCommand.appendExtension(tmchDomainCreateCommandExtension);

        assertEquals(DOMAIN_CREATE_WITH_TMCH_SMD_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyingTmchExtensionWithNoticeId() throws SAXException {
        tmchDomainCreateCommandExtension.setNoticeId("49FD46E6C4B45C55D4AC");
        tmchDomainCreateCommandExtension.setNotAfterDateTime(
                EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z"));
        tmchDomainCreateCommandExtension.setAcceptedDateTime(
                EPPDateFormatter.fromXSDateTime("2007-02-02T02:02:02.0Z"));
        domainCreateCommand.appendExtension(tmchDomainCreateCommandExtension);

        assertEquals(DOMAIN_CREATE_WITH_TMCH_NOTICE_ID_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldThrowParsingExceptionWhenProvidingBothSMDAndNoticeIdAsTmchExtension() throws SAXException {
        tmchDomainCreateCommandExtension.setEncodedSignedMarkData("ZW5jb2RlZFNpZ25lZE1hcmtEYXRh");
        tmchDomainCreateCommandExtension.setNoticeId("49FD46E6C4B45C55D4AC");
        tmchDomainCreateCommandExtension.setNotAfterDateTime(
                EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z"));
        tmchDomainCreateCommandExtension.setAcceptedDateTime(
                EPPDateFormatter.fromXSDateTime("2007-02-02T02:02:02.0Z"));
        domainCreateCommand.appendExtension(tmchDomainCreateCommandExtension);

        thrown.expect(SAXException.class);

        domainCreateCommand.toXML();
    }

}
