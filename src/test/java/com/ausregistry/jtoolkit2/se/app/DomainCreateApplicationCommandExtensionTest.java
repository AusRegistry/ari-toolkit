package com.ausregistry.jtoolkit2.se.app;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainCreateApplicationCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyApplicationExtension() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateApplicationCommandExtension ext = new DomainCreateApplicationCommandExtension();
        ext.setPhase("sunrise");
        String encodedSignedMarkData = "ZW5jb2RlZFNpZ25lZE1hcmtEYXRh";
        ext.setEncodedSignedMarkData(encodedSignedMarkData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension><create xmlns=\"urn:ar:params:xml:ns:application-1.0\"><phase>sunrise</phase></create>"
                + "<create xmlns=\"urn:ar:params:xml:ns:tmch-1.0\"><smd>" + encodedSignedMarkData + "</smd></create>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

    @Test
    public void shouldReturnApplicationIdFromDomainCreateResponse() throws Exception {
        String dnsForm = "test-domain";
        DomainCreateApplicationResponse response = new DomainCreateApplicationResponse();
        String applicationId = "sunrise-application-id";
        String creDate = "2011-01-01T00:00:00Z";
        XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(dnsForm, applicationId, creDate));

        response.fromXML(doc);
        assertEquals(applicationId, response.getApplicationId());
        assertEquals(dnsForm, response.getDomainName());
        assertEquals(EPPDateFormatter.fromXSDateTime(creDate), response.getCreateDate());
    }

    private String getCreateResponseExpectedXml(final String domainName, final String applicationId,
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
        "<creData xmlns=\"urn:ar:params:xml:ns:application-1.0\" >" + //
        "<id>").append(applicationId).append("</id>" + //
        "<name>").append(domainName).append("</name>" + //
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
