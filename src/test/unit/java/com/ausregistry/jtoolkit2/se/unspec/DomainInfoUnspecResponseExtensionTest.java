package com.ausregistry.jtoolkit2.se.unspec;

import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DomainInfoUnspecResponseExtensionTest {

    private static final String RESPONSE_XML_PART_ONE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">\n"
            + "<response>\n"
            + "  <result code=\"1000\">\n"
            + "   <msg lang=\"en-US\">Command completed successfully</msg>\n"
            + "   <value>\n"
            + "    <text>SRS Major Code: 2000</text>\n"
            + "   </value>\n"
            + "   <value>\n"
            + "    <text>SRS Minor Code: 20002</text>\n"
            + "   </value>\n"
            + "   <value>\n"
            + "    <text>--DOMAIN_SUCCESSFULLY_QUERIED</text>\n"
            + "   </value>\n"
            + "   <value>\n"
            + "    <text>Domain successfully queried</text>\n"
            + "   </value>\n"
            + "  </result>\n"
            + "  <resData>\n"
            + "   <domain:infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">\n"
            + "    <domain:name>ANU001.NYC</domain:name>\n"
            + "    <domain:roid>D982888-GA</domain:roid>\n"
            + "    <domain:status s=\"inactive\"/>\n"
            + "    <domain:registrant>EXTCONTACT-101</domain:registrant>\n"
            + "    <domain:contact type=\"admin\">EXTCONTACT-101</domain:contact>\n"
            + "    <domain:contact type=\"billing\">EXTCONTACT-101</domain:contact>\n"
            + "    <domain:contact type=\"tech\">EXTCONTACT-101</domain:contact>\n"
            + "    <domain:clID>NEUSTAR</domain:clID>\n"
            + "    <domain:crID>NEUSTAR</domain:crID>\n"
            + "    <domain:crDate>2016-07-19T16:54:15.0Z</domain:crDate>\n"
            + "    <domain:exDate>2017-07-18T23:59:59.0Z</domain:exDate>\n"
            + "    <domain:authInfo>\n"
            + "     <domain:pw>1234</domain:pw>\n"
            + "    </domain:authInfo>\n"
            + "   </domain:infData>\n"
            + "  </resData>\n"
            + "  <extension>\n";

    private final String RESPONSE_XML_NEULEVEL_START =
            "<neulevel:extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\" "
            + "xmlns:neulevel=\"urn:ietf:params:xml:ns:neulevel-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:neulevel-1.0 neulevel-1.0.xsd\">\n";

    private final String RESPONSE_XML_NEULEVEL_UNSPEC =
            "<neulevel:unspec>EXTContact=EXTCONTACT-101</neulevel:unspec>\n";

    private final String RESPONSE_XML_NEULEVEL_END = "   </neulevel:extension>\n";

    private final String RESPONSE_XML_FINAL_PART = "  </extension>\n"
            + "  <trID>\n"
            + "   <clTRID>chrecprvstg20-578E5B07-4684-1002</clTRID>\n"
            + "   <svTRID>20160719165428401-1090130104-55-ga</svTRID>\n"
            + "  </trID>\n"
            + "</response>\n"
            + "</epp>\n";

    private final String RESPONSE_XML = RESPONSE_XML_PART_ONE + RESPONSE_XML_NEULEVEL_START
            + RESPONSE_XML_NEULEVEL_UNSPEC + RESPONSE_XML_NEULEVEL_END + RESPONSE_XML_FINAL_PART;

    private DomainInfoUnspecResponseExtension response;
    private XMLParser parser;

    @Before
    public void setUp() throws Exception {
        response = new DomainInfoUnspecResponseExtension();
        parser = new XMLParser();
    }

    @Test
    public void shouldFindTheUnspecExtensionInTheReponseAndMarkItAsInitialized()
            throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
        assertTrue(response.isInitialised());
    }

    @Test
    public void shouldParseTheResponseAndPopulateTheUnspecDetails() throws ParsingException, XPathExpressionException {
        response.fromXML(parser.parse(RESPONSE_XML));
        assertEquals(response.getUnspecDetails(), "EXTContact=EXTCONTACT-101");
    }

    @Test
    public void shouldParseTheResponsEvenIfUnspecDetailsHasNoValue() throws ParsingException, XPathExpressionException {
        String xml = RESPONSE_XML_PART_ONE + RESPONSE_XML_NEULEVEL_START
                + "<neulevel:unspec></neulevel:unspec>\n"
                + RESPONSE_XML_NEULEVEL_END + RESPONSE_XML_FINAL_PART;
        response.fromXML(parser.parse(xml));
        assertThat(response.getUnspecDetails().isEmpty(), is(true));
    }

    @Test
    public void shouldParseTheResponsEvenIfUnspecNotProvided() throws ParsingException, XPathExpressionException {
        String xml = RESPONSE_XML_PART_ONE + RESPONSE_XML_NEULEVEL_START
                + RESPONSE_XML_NEULEVEL_END + RESPONSE_XML_FINAL_PART;
        response.fromXML(parser.parse(xml));
        assertNull(response.getUnspecDetails());
    }

}

