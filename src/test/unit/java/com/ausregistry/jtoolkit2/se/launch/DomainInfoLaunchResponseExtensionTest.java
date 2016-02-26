package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;

public class DomainInfoLaunchResponseExtensionTest {

    private static final String RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
         + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
         + "  <response>"
         + "    <result code=\"1000\">"
         + "      <msg lang=\"en\">Command completed successfully</msg>"
         + "    </result>"
         + "    <extension>"
         + "      <infData xmlns=\"urn:ietf:params:xml:ns:launch-1.0\">"
         + "          <phase name=\"sunrise-fcfs\">sunrise</phase>"
         + "          <applicationID>myApp</applicationID>"
         + "          <status s=\"custom\" name=\"pendingAllocation\"/>"
         + "      </infData>"
         + "    </extension>"
         + "    <trID>"
         + "      <clTRID>ABC-12345</clTRID>"
         + "      <svTRID>57f39ac6-abd2-4fea-9a80-e791d1af86f7</svTRID>"
         + "    </trID>"
         + "  </response>"
         + "</epp>";

    private DomainInfoLaunchResponseExtension response;
    private XMLParser parser;

    @Before
    public void setUp() throws Exception {
        response = new DomainInfoLaunchResponseExtension();
        parser = new XMLParser();
        response.fromXML(parser.parse(RESPONSE_XML));
    }

    @Test
    public void shouldParseAllTheDomainsInTheExtendedAvailabilityInfoResponseXml() {
        assertTrue(response.isInitialised());
    }

    @Test
    public void shouldReturnApplicationIDFromXML() {
        assertEquals(response.getApplicationID(), "myApp");
    }

    @Test
    public void shouldReturnStatusFromXML() {
        assertEquals(response.getStatus(), "custom");
        assertEquals(response.getStatusName(), "pendingAllocation");
    }

    @Test
    public void shouldReturnPhaseFromXML() {
        assertEquals(response.getPhaseName(), "sunrise-fcfs");
        assertEquals(response.getPhaseType(), "sunrise");
    }

    @Test
    public void shouldReturnNoStatusNameWhenNotSupplied() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
                + "  <response>"
                + "    <result code=\"1000\">"
                + "      <msg lang=\"en\">Command completed successfully</msg>"
                + "    </result>"
                + "    <extension>"
                + "      <infData xmlns=\"urn:ietf:params:xml:ns:launch-1.0\">"
                + "          <phase name=\"sunrise-fcfs\">sunrise</phase>"
                + "          <applicationID>myApp</applicationID>"
                + "          <status s=\"pendingAllocation\"/>"
                + "      </infData>"
                + "    </extension>"
                + "    <trID>"
                + "      <clTRID>ABC-12345</clTRID>"
                + "      <svTRID>57f39ac6-abd2-4fea-9a80-e791d1af86f7</svTRID>"
                + "    </trID>"
                + "  </response>"
                + "</epp>"));
        assertEquals(response.getStatus(), "pendingAllocation");
    }

}
