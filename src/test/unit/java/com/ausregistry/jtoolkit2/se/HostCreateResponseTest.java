package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class HostCreateResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    private static final String RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><host:creData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><host:name>ns1.example.com</host:name><host:crDate>1999-04-03T22:00:00.0Z</host:crDate></host:creData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private static final String RESPONSE_XML_IDN_USER_FORM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result>"
            + "<resData>"
            + "<host:creData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\">"
            + "<host:name>xn--ns16-kdba.jtkutest.com.au</host:name>"
            + "<host:crDate>1999-04-03T22:00:00.0Z</host:crDate>"
            + "</host:creData>"
            + "</resData>"
            + "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private static final String RESPONSE_XML_IDN_LDH_FORM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result>"
            + "<resData>"
            + "<host:creData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\">"
            + "<host:name>ns1.jtkutest.com.au</host:name>"
            + "<host:crDate>1999-04-03T22:00:00.0Z</host:crDate>"
            + "</host:creData>"
            + "</resData>"
            + "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";

    @Test
    public void testGetName() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML);
        assertEquals("ns1.example.com", response.getName());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML);
        assertEquals(EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"),
                response.getCreateDate());
    }

    @Test
    public void testGetResults() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML);
        final Result[] results = response.getResults();
        assertEquals(1, results.length);
        assertEquals(1000, results[0].getResultCode());
        assertEquals("Command completed successfully",
                results[0].getResultMessage());
    }

    @Test
    public void testGetCLTRID() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML);
        assertEquals("ABC-12345", response.getCLTRID());
    }

    @Test
    public void testGetIdnName() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML_IDN_USER_FORM);
        assertEquals("xn--ns16-kdba.jtkutest.com.au", response.getName());
    }

    @Test
    public void testLdhOnlyGetName() throws Exception {
        final HostCreateResponse response = makeResponse(RESPONSE_XML_IDN_LDH_FORM);
        assertEquals("ns1.jtkutest.com.au", response.getName());
    }

    private HostCreateResponse makeResponse(final String responseXml)
            throws Exception {
        final HostCreateResponse result = new HostCreateResponse();
        final XMLDocument doc = PARSER.parse(responseXml);
        result.fromXML(doc);
        return result;
    }


}
