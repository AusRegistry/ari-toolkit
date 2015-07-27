package com.ausregistry.jtoolkit2.se.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.se.tmch.TmchDomainInfoResponseExtension;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainInfoApplicationCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyApplicationExtensionForInfo() throws SAXException {

        final Command cmd = new DomainInfoCommand("jtkutest.com.au");
        final DomainInfoApplicationCommandExtension ext = new DomainInfoApplicationCommandExtension();
        ext.setApplicationId("sunrise-application-id");

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name></info></info>" + "<extension>"
                + "<info xmlns=\"urn:ar:params:xml:ns:application-1.0\"><id>sunrise-application-id</id></info>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

    @Test
    public void shouldReturnApplicationDetailsForInfoCommand() throws ParsingException {
        final String dnsForm = "test-domain";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoApplicationResponseExtension applicationExtension = new DomainInfoApplicationResponseExtension(
                ResponseExtension.INFO);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        String phase = "sunrise";
        List<String> statuses = new ArrayList<String>();
        statuses.add("ok");
        String updDate = "2012-01-01T00:00:00Z";
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(dnsForm, applicationId, phase, statuses,
                creDate, updDate));

        response.registerExtension(applicationExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Application extension should have been initialised", applicationExtension.isInitialised());
        assertEquals(applicationId, applicationExtension.getApplicationId());
        assertEquals(phase, applicationExtension.getPhase());
        assertEquals(statuses, applicationExtension.getStatuses());
    }

    @Test
    public void shouldReturnApplicationDetailsForInfoCommandWhenNotUpdated() throws ParsingException {
        final String dnsForm = "test-domain";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoApplicationResponseExtension applicationExtension = new DomainInfoApplicationResponseExtension(
                ResponseExtension.INFO);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        String phase = "sunrise";
        List<String> statuses = new ArrayList<String>();
        statuses.add("pendingOutcome");
        statuses.add("deleteProhibited");
        statuses.add("updateProhibited");

        String updDate = "2012-01-01T00:00:00Z";
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(dnsForm, applicationId, phase, statuses,
                creDate, updDate));

        response.registerExtension(applicationExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Application extension should have been initialised", applicationExtension.isInitialised());
    }

    private String getInfoResponseExpectedXml(final String domainName, final String applicationId, final String phase,
            final List<String> statuses, final String creDate, final String updDate) {
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
        result.append("<name>").append(domainName).append("</name>");
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
        result.append("<crDate>").append(creDate).append("</crDate>");
        result.append("<upDate>").append(updDate).append("</upDate>");
        result.append("<exDate>2008-02-10T00:00:00.0Z</exDate>");
        result.append("<authInfo>");
        result.append("<pw>0192pqow</pw>");
        result.append("</authInfo>");
        result.append("</infData>");
        result.append("</resData>");

        result.append("<extension>");
        result.append("<app:infData xmlns:app=\"urn:ar:params:xml:ns:application-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ar:params:xml:ns:application-1.0 application-1.0.xsd\">");
        result.append("<app:id>").append(applicationId).append("</app:id>");
        result.append("<app:phase>").append(phase).append("</app:phase>");
        for (String status : statuses) {
            result.append("<app:status s=\"").append(status).append("\" />");
        }
        result.append("</app:infData>");
        result.append("<tmch:infData xmlns:tmch=\"urn:ar:params:xml:ns:tmch-1.0\">");
        result.append("<tmch:smd>ZW5jb2RlZFNpZ25lZE1hcmtEYXRh</tmch:smd>");
        result.append("</tmch:infData>");
        result.append("</extension>");

        result.append("<trID>");
        result.append("<clTRID>ABC-12345</clTRID>");
        result.append("<svTRID>54321-XYZ</svTRID>");
        result.append("</trID>");
        result.append("</response>");
        result.append("</epp>");

        return result.toString();
    }

    @Test
    public void shouldAddTmchExtensionInApplicationInfoResponse() throws ParsingException {
        final String dnsForm = "test-domain";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoApplicationResponseExtension applicationExtension = new DomainInfoApplicationResponseExtension(
                ResponseExtension.INFO);
        final TmchDomainInfoResponseExtension tmchExtension = new TmchDomainInfoResponseExtension(
                ResponseExtension.INFO);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        String phase = "sunrise";
        List<String> statuses = new ArrayList<String>();
        statuses.add("ok");
        String updDate = "2012-01-01T00:00:00Z";
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(dnsForm, applicationId, phase, statuses,
                creDate, updDate));

        response.registerExtension(applicationExtension);
        response.registerExtension(tmchExtension);
        response.fromXML(doc);
        assertTrue("Tmch extension should have been initialised", tmchExtension.isInitialised());
        assertEquals(tmchExtension.getEncodedSignedMarkData(), "ZW5jb2RlZFNpZ25lZE1hcmtEYXRh");
    }
}
