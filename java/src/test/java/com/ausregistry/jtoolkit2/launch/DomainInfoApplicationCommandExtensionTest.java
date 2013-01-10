package com.ausregistry.jtoolkit2.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainInfoCommand;
import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainInfoApplicationCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtensionForInfo() throws SAXException {

        final Command cmd = new DomainInfoCommand("jtkutest.com.au");
        final DomainInfoApplicationCommandExtension ext = new DomainInfoApplicationCommandExtension();
        ext.setApplicationId("sunrise-application-id");

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name></info></info>" + "<extension>"
                + "<info xmlns=\"urn:rbp:params:xml:ns:application-1.0\"><id>sunrise-application-id</id></info>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

    @Test
    public void shouldReturnLaunchDetailsForInfoCommand() throws ParsingException {
        final String dnsForm = "test-domain";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoApplicationResponseExtension launchExtension = new DomainInfoApplicationResponseExtension(
                ResponseExtension.INFO);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        String phase = "sunrise";
        String status = "ok";
        String updDate = "2012-01-01T00:00:00Z";
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(dnsForm, applicationId, phase, status, creDate,
                updDate));

        response.registerExtension(launchExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Launch extension should have been initialised", launchExtension.isInitialised());
        assertEquals(applicationId, launchExtension.getApplicationId());
        assertEquals(phase, launchExtension.getPhase());
        assertEquals(status, launchExtension.getStatus());
        assertEquals(EPPDateFormatter.fromXSDateTime(creDate), launchExtension.getCreateDate());
        assertEquals(EPPDateFormatter.fromXSDateTime(updDate), launchExtension.getUpdateDate());
    }

    @Test
    public void shouldReturnLaunchDetailsForInfoCommandWhenNotUpdated() throws ParsingException {
        final String dnsForm = "test-domain";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoApplicationResponseExtension launchExtension = new DomainInfoApplicationResponseExtension(
                ResponseExtension.INFO);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        String phase = "sunrise";
        String status = "ok";
        String updDate = null;
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(dnsForm, applicationId, phase, status, creDate,
                updDate));

        response.registerExtension(launchExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Launch extension should have been initialised", launchExtension.isInitialised());
        assertNull(launchExtension.getUpdateDate());
    }

    private String getInfoResponseExpectedXml(final String domainName, final String applicationId, final String phase,
            final String status, final String creDate, final String updDate) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append("<response>");
        result.append("<result code=\"1000\">");
        result.append("<msg>Command completed successfully</msg>");
        result.append("</result>");
        result.append("<resData>");
        result.append("<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append("<name>" + domainName + "</name>");
        result.append("<roid>D0000003-AR</roid>");
        result.append("<status s=\"ok\" lang=\"en\"/>");
        result.append("<registrant>EXAMPLE</registrant>");
        result.append("<contact type=\"tech\">EXAMPLE</contact>");
        result.append("<ns>");
        result.append("<hostObj>ns1.example.com.au</hostObj>");
        result.append("<hostObj>ns2.example.com.au</hostObj>");
        result.append("</ns>");
        result.append("<host>ns1.example.com.au</host>");
        result.append("<host>ns2.exmaple.com.au</host>");
        result.append("<clID>Registrar</clID>");
        result.append("<crID>Registrar</crID>");
        result.append("<crDate>2006-02-09T15:44:58.0Z</crDate>");
        result.append("<exDate>2008-02-10T00:00:00.0Z</exDate>");
        result.append("<authInfo>");
        result.append("<pw>0192pqow</pw>");
        result.append("</authInfo>");
        result.append("</infData>");
        result.append("</resData>");

        result.append("<extension>");
        result.append("<launch:infData xmlns:launch=\"urn:rbp:params:xml:ns:application-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:rbp:params:xml:ns:application-1.0 application-1.0.xsd\">");
        result.append("<launch:id>" + applicationId + "</launch:id>");
        result.append("<launch:phase>" + phase + "</launch:phase>");
        result.append("<launch:status s=\"" + status + "\" />");
        result.append("<launch:crDate>" + creDate + "</launch:crDate>");
        if (updDate != null) {
            result.append("<launch:upDate>" + updDate + "</launch:upDate>");
        }
        result.append("</launch:infData>");
        result.append("</extension>");

        result.append("<trID>");
        result.append("<clTRID>ABC-12345</clTRID>");
        result.append("<svTRID>54321-XYZ</svTRID>");
        result.append("</trID>");
        result.append("</response>");
        result.append("</epp>");

        return result.toString();
    }

}
