package com.ausregistry.jtoolkit2.se.tmch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

import com.ausregistry.jtoolkit2.se.DomainCheckResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;

public class TmchDomainCheckResponseTest {

    private static final String xml = "<?xml version=\"1.0\" standalone=\"no\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
                    + "<response>"
                    + "<result code=\"1000\">"
                    + "<msg>Command completed successfully</msg>"
                    + "</result>"
                    + "<resData>"
                    + "<chkData xmlns=\"urn:ar:params:xml:ns:tmch-1.0\">"
                    + "<cd>"
                    + "<name exists=\"1\">example1.tld</name>"
                    + "<key>CLAIM_KEY</key>"
                    + "</cd>"
                    + "<cd>"
                    + "<name exists=\"0\">example2.tld</name>"
                    + "</cd>"
                    + "</chkData>"
                    + "</resData>"
                    + "<trID>"
                    + "<clTRID>ABC-12345</clTRID>"
                    + "<svTRID>54321-XYZ</svTRID>"
                    + "</trID>"
                    + "</response>"
                    + "</epp>";


    private TmchDomainCheckResponse response;

    @Before
    public void setUp() throws Exception {
        response = new TmchDomainCheckResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(xml);
        response.fromXML(doc);
    }

    @Test
    public void shouldReturnClaimsKeyFromResponse() {
        assertEquals("CLAIM_KEY", response.getClaimsKey("example1.tld"));
        assertNull(response.getClaimsKey("example2.tld"));
        assertNull(response.getClaimsKey("example3.tld"));
    }

    @Test
    public void shouldReturnClaimsKeyFromResponseUsingPosition() {
        assertEquals("CLAIM_KEY", response.getClaimsKey(1L));
        assertNull(response.getClaimsKey(2L));
        assertNull(response.getClaimsKey(3L));
    }

    @Test
    public void shouldReturnExistsFlagFromResponse() {
        assertEquals(Boolean.TRUE, response.exists("example1.tld"));
        assertEquals(Boolean.FALSE, response.exists("example2.tld"));
        assertNull(response.exists("example3.tld"));
    }

    @Test
    public void shouldReturnExistsFlagFromResponseUsingPosition() {
        assertEquals(Boolean.TRUE, response.exists(1L));
        assertEquals(Boolean.FALSE, response.exists(2L));
        assertNull(response.exists(3L));
    }
}
