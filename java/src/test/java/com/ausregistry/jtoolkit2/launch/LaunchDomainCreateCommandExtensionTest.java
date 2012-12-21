package com.ausregistry.jtoolkit2.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class LaunchDomainCreateCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtension() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final LaunchDomainCreateCommandExtension ext = new LaunchDomainCreateCommandExtension();
        ext.setPhase("sunrise");

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension><create xmlns=\"urn:rbp:params:xml:ns:application-1.0\"><phase>sunrise</phase></create>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

    @Test
    public void shouldReturnLaunchApplicationIdFromDomainCreateResponse() throws Exception {
        final String dnsForm = "test-domain";
        final DomainCreateResponse response = new DomainCreateResponse();
        final LaunchDomainCreateResponseExtension launchExtension = 
                new LaunchDomainCreateResponseExtension(ResponseExtension.CREATE);
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(dnsForm, applicationId, creDate));

        response.registerExtension(launchExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Launch extension should have been initialised", launchExtension.isInitialised());
        assertEquals(applicationId, launchExtension.getApplicationId());
        assertEquals(EPPDateFormatter.fromXSDateTime(creDate), launchExtension.getCreateDate());
    }

    private String getCreateResponseExpectedXml(final String domainName, final String phase,
            final String creDate) {
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
        result.append("<domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append("<domain:name>" + domainName + "</domain:name>");
        result.append("<domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate>");
        result.append("<domain:exDate>2001-04-03T22:00:00.0Z</domain:exDate>");
        result.append("</domain:creData>");
        result.append("</resData>");

        result.append("<extension>");
        result.append("<launch:creData xmlns:launch=\"urn:rbp:params:xml:ns:application-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:rbp:params:xml:ns:application-1.0 application-1.0.xsd\">");
        result.append("<launch:id>" + phase + "</launch:id>");
        result.append("<launch:crDate>" + creDate + "</launch:crDate>");
        result.append("</launch:creData>");
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
