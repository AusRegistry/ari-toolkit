package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainVariantInfoResponseTest {
    private static final String SINGLE_DOMAIN_RESPONSE_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result>"
            + "<resData>"
            + "<varInfData xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\""
            + " xsi:schemaLocation=\"urn:X-ar:params:xml:ns:variant-1.0 variant-1.0.xsd\">"
            + "<variant userForm=\"ا١٢٣-١.idn.allowed.ae\">xn--testdom1.idn.allowed.ae</variant>"
            + "</varInfData>"
            + "</resData>"
            + "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>"
            + "</response>" + "</epp>";
    private static final String TWO_DOMAINS_RESPONSE_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result>"
            + "<resData>"
            + "<varInfData xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\""
            + " xsi:schemaLocation=\"urn:X-ar:params:xml:ns:variant-1.0 variant-1.0.xsd\">"
            + "<variant userForm=\"ا١٢٣-١.idn.allowed.ae\">xn--testdom1.idn.allowed.ae</variant>"
            + "<variant userForm=\"ا١٢١٢٣-١.idn.allowed.ae\">xn--testdom2.idn.allowed.ae</variant>"
            + "</varInfData>"
            + "</resData>"
            + "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>"
            + "</response>" + "</epp>";
    private static final XMLParser PARSER = new XMLParser();

    private DomainVariantInfoResponse twoDomainsResponse;
    private DomainVariantInfoResponse singleDomainsResponse;

    @Before
    public void setUp() throws Exception {
        twoDomainsResponse = new DomainVariantInfoResponse();
        singleDomainsResponse = new DomainVariantInfoResponse();
        twoDomainsResponse.fromXML(PARSER.parse(TWO_DOMAINS_RESPONSE_XML));
        singleDomainsResponse.fromXML(PARSER.parse(SINGLE_DOMAIN_RESPONSE_XML));
    }

    @Test
    public void testSingleDomainResponse() {
        final ArrayList<IdnaDomainVariant> domains = singleDomainsResponse.getDomains();
        assertTrue(singleDomainsResponse.getDomains().size() == 1);

        assertEquals("xn--testdom1.idn.allowed.ae", domains.get(0).getName());
        assertEquals("ا١٢٣-١.idn.allowed.ae", domains.get(0).getUserForm());
    }

    @Test
    public void testTwoDomainResponse() {
        final ArrayList<IdnaDomainVariant> domains = twoDomainsResponse.getDomains();
        assertTrue(twoDomainsResponse.getDomains().size() == 2);

        assertEquals("xn--testdom1.idn.allowed.ae", domains.get(0).getName());
        assertEquals("ا١٢٣-١.idn.allowed.ae", domains.get(0).getUserForm());

        assertEquals("xn--testdom2.idn.allowed.ae", domains.get(1).getName());
        assertEquals("ا١٢١٢٣-١.idn.allowed.ae", domains.get(1).getUserForm());
    }

    @Test
    public void testGetResult() {
        assertEquals(1000, twoDomainsResponse.getResults()[0].getResultCode());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", twoDomainsResponse.getCLTRID());
    }

    @Test
    public void testGetSVTRID() {
        assertEquals("54322-XYZ", twoDomainsResponse.getSVTRID());
    }
}
