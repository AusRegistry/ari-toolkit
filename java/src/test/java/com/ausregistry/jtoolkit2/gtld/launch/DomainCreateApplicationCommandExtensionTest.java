package com.ausregistry.jtoolkit2.gtld.launch;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.launch.DomainCreateApplicationCommandExtension;
import com.ausregistry.jtoolkit2.launch.DomainCreateApplicationResponse;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCreateApplicationCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtension() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateApplicationCommandExtension ext = new DomainCreateApplicationCommandExtension();
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
        String dnsForm = "test-domain";
        DomainCreateApplicationResponse response = new DomainCreateApplicationResponse();
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(dnsForm, applicationId, creDate));

        response.fromXML(doc);
        assertEquals(applicationId, response.getApplicationId());
        assertEquals(EPPDateFormatter.fromXSDateTime(creDate), response.getCreateDate());
    }

    private String getCreateResponseExpectedXml(final String domainName, final String phase,
            final String creDate) {
        final StringBuilder result = new StringBuilder();
		result.append(//
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + //
				"<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" + //
        " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + //
        " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" + //
        "<response>" + //
        "<result code=\"1000\">" + //
        "<msg>Command completed successfully</msg>" + //
        "</result>" + //
        "<resData>" + //
        "<creData xmlns=\"urn:rbp:params:xml:ns:application-1.0\" >" + //
        "<id>").append(phase).append("</id>" + //
        "<crDate>").append(creDate).append("</crDate>" + //
        "</creData>" + //
        "</resData>" + //
        "<trID>" + //
        "<clTRID>ABC-12345</clTRID>" + //
        "<svTRID>54321-XYZ</svTRID>" + //
        "</trID>" + //
        "</response>" + //
        "</epp>");
        
        return result.toString();
    }

}
