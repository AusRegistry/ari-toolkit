package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    private static final String RESPONSE_WITH_MARK_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
         + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
         + "  <response>"
         + "    <result code=\"1000\">"
         + "      <msg lang=\"en\">Command completed successfully</msg>"
         + "    </result>"
         + "    <extension>"
         + "      <launch:infData xmlns:launch=\"urn:ietf:params:xml:ns:launch-1.0\">"
         + "          <launch:phase name=\"sunrise-fcfs\">sunrise</launch:phase>"
         + "          <launch:applicationID>myApp</launch:applicationID>"
         + "          <launch:status s=\"custom\" name=\"pendingAllocation\"/>"
         + "          <mark:mark xmlns:mark=\"urn:ietf:params:xml:ns:mark-1.0\">"
         + "    <mark:trademark>\n"
         + "      <mark:id>1234-2</mark:id>\n"
         + "      <mark:markName>Example One</mark:markName>\n"
         + "      <mark:holder entitlement=\"owner\">\n"
         + "        <mark:name>holderName</mark:name><mark:org>Example Inc.</mark:org>\n"
         + "        <mark:addr>\n"
         + "          <mark:street>123 Example Dr.</mark:street>\n"
         + "          <mark:street>Suite 100</mark:street>\n"
         + "          <mark:city>Reston</mark:city>\n"
         + "          <mark:sp>VA</mark:sp>\n"
         + "          <mark:pc>20190</mark:pc>\n"
         + "          <mark:cc>US</mark:cc>\n"
         + "        </mark:addr>\n"
         + "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>"
         + "          <mark:email>support@example.tld</mark:email>\n"
         + "      </mark:holder>\n"
         + "<mark:contact type=\"owner\">"
         + "<mark:name>contactName</mark:name><mark:org>Example Inc.</mark:org>\n"
         + "        <mark:addr>\n"
         + "          <mark:street>123 Example Dr.</mark:street>\n"
         + "          <mark:street>Suite 100</mark:street>\n"
         + "          <mark:city>Reston</mark:city>\n"
         + "          <mark:sp>VA</mark:sp>\n"
         + "          <mark:pc>20190</mark:pc>\n"
         + "          <mark:cc>US</mark:cc>\n"
         + "        </mark:addr>\n"
         + "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>"
         + "<mark:email>123@123.com</mark:email>"
         + "      </mark:contact>"
         + "      <mark:jurisdiction>US</mark:jurisdiction>\n"
         + "      <mark:class>35</mark:class>\n"
         + "      <mark:class>36</mark:class>\n"
         + "      <mark:label>example-one</mark:label>\n"
         + "      <mark:label>exampleone</mark:label>\n"
         + "      <mark:goodsAndServices>Dirigendas et eiusmodi\n"
         + "        featuring infringo in airfare et cartam servicia.\n"
         + "      </mark:goodsAndServices> \n"
         + "      <mark:apId>SOMEAPID</mark:apId>\n"
         + "      <mark:apDate>2009-08-16T09:00:00.0Z</mark:apDate>\n"
         + "      <mark:regNum>234235-A</mark:regNum>\n"
         + "      <mark:regDate>2009-08-16T09:00:00.0Z</mark:regDate>\n"
         + "      <mark:exDate>2015-08-16T09:00:00.0Z</mark:exDate>\n"
         + "    </mark:trademark>\n"
         + "    <mark:treatyOrStatute>\n"
         + "      <mark:id>1234-2</mark:id>\n"
         + "      <mark:markName>Example One</mark:markName>\n"
         + "      <mark:holder entitlement=\"owner\">\n"
         + "        <mark:org>Example Inc.</mark:org>\n"
         + "        <mark:addr>\n"
         + "          <mark:street>123 Example Dr.</mark:street>\n"
         + "          <mark:street>Suite 100</mark:street>\n"
         + "          <mark:city>Reston</mark:city>\n"
         + "          <mark:sp>VA</mark:sp>\n"
         + "          <mark:pc>20190</mark:pc>\n"
         + "          <mark:cc>US</mark:cc>\n"
         + "        </mark:addr>\n"
         + "      </mark:holder>\n"
         + "      <mark:protection>"
         + "<mark:cc>US</mark:cc>"
         + "<mark:region>region</mark:region>"
         + "<mark:ruling>US</mark:ruling>"
         + "<mark:ruling>CA</mark:ruling>"
         + "</mark:protection>\n"
         + "      <mark:label>example-one</mark:label>\n"
         + "      <mark:label>exampleone</mark:label>\n"
         + "      <mark:goodsAndServices>Dirigendas et eiusmodi\n"
         + "        featuring infringo in airfare et cartam servicia.\n"
         + "      </mark:goodsAndServices> \n"
         + "      <mark:refNum>234235-A</mark:refNum>\n"
         + "      <mark:proDate>2009-08-16T09:00:00.0Z</mark:proDate>\n"
         + "      <mark:title>title</mark:title>\n"
         + "      <mark:execDate>2015-08-16T09:00:00.0Z</mark:execDate>\n"
         + "    </mark:treatyOrStatute>\n"
         + "    <mark:court>\n"
         + "      <mark:id>1234-2</mark:id>\n"
         + "      <mark:markName>Example One</mark:markName>\n"
         + "      <mark:holder entitlement=\"owner\">\n"
         + "        <mark:org>Example Inc.</mark:org>\n"
         + "        <mark:addr>\n"
         + "          <mark:street>123 Example Dr.</mark:street>\n"
         + "          <mark:street>Suite 100</mark:street>\n"
         + "          <mark:city>Reston</mark:city>\n"
         + "          <mark:sp>VA</mark:sp>\n"
         + "          <mark:pc>20190</mark:pc>\n"
         + "          <mark:cc>US</mark:cc>\n"
         + "        </mark:addr>\n"
         + "      </mark:holder>\n"
         + "      <mark:label>example-one</mark:label>\n"
         + "      <mark:label>exampleone</mark:label>\n"
         + "      <mark:goodsAndServices>Dirigendas et eiusmodi\n"
         + "        featuring infringo in airfare et cartam servicia.\n"
         + "      </mark:goodsAndServices> \n"
         + "      <mark:refNum>234235-A</mark:refNum>\n"
         + "      <mark:proDate>2009-08-16T09:00:00.0Z</mark:proDate>\n"
         + "      <mark:cc>cc</mark:cc><mark:region>r1</mark:region><mark:region>r3</mark:region>\n"
         + "      <mark:courtName>courtName</mark:courtName>\n"
         + "    </mark:court>\n"
         + "  </mark:mark>\n"
         + "      </launch:infData>"
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
    }

    @Test
    public void shouldParseAllTheDomainsInTheExtendedAvailabilityInfoResponseXml()
            throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
        assertTrue(response.isInitialised());
    }

    @Test
    public void shouldReturnApplicationIDFromXML() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
        assertEquals(response.getApplicationID(), "myApp");
    }

    @Test
    public void shouldReturnStatusFromXML() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
        assertEquals(response.getStatus(), "custom");
        assertEquals(response.getStatusName(), "pendingAllocation");
    }

    @Test
    public void shouldReturnPhaseFromXML() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
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

    @Test
    public void shouldReturnMarkDataWhenSupplied() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_WITH_MARK_XML));
        assertNotNull(response.getMarksList());
        assertEquals(response.getMarksList().getMarks().size(), 3);
    }

}
