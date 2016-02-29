package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class TmchDomainCheckResponseExtensionTest {

    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">" +
            "<response>" +
            "<result code=\"1000\">" +
            "<msg lang=\"en\">Command completed successfully</msg>" +
            "</result>" +
            "<extension>" +
            "<tmch:chkData xmlns:tmch=\"urn:ar:params:xml:ns:tmch-1.0\">" +
            "<tmch:cd>" +
            "<tmch:name claim=\"1\">domain-with-claim.tld</tmch:name>" +
            "<tmch:key>claimsKey1</tmch:key>" +
            "</tmch:cd>" +
            "<tmch:cd>" +
            "<tmch:name claim=\"0\">domain-wihtout-claim.tld</tmch:name>" +
            "</tmch:cd>" +
            "<tmch:cd>" +
            "<tmch:name claim=\"0\">domain.not-a-tld</tmch:name>" +
            "</tmch:cd>" +
            "<tmch:cd>" +
            "<tmch:name claim=\"1\">just-a-label</tmch:name>" +
            "<tmch:key>claimsKey2</tmch:key>" +
            "</tmch:cd>" +
            "</tmch:chkData>" +
            "</extension>" +
            "<trID>" +
            "<clTRID>ABC-12345</clTRID>" +
            "<svTRID>57f39ac6-abd2-4fea-9a80-e791d1af86f7</svTRID>" +
            "</trID>" +
            "</response>" +
            "</epp>";


    private TmchDomainCheckResponseExtension response;

    @Before
    public void setUp() throws Exception {
        response = new TmchDomainCheckResponseExtension();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @Test
    public void shouldReturnClaimsKeyFromResponse() {
        assertEquals("claimsKey1", response.getClaimsKey("domain-with-claim.tld"));
        assertNull(response.getClaimsKey("domain-wihtout-claim.tld"));
        assertNull(response.getClaimsKey("domain.not-a-tld"));
        assertEquals("claimsKey2", response.getClaimsKey("just-a-label"));
        assertNull(response.getClaimsKey("example.tld"));
    }

    @Test
    public void shouldReturnClaimsKeyFromResponseUsingPosition() {
        assertEquals("claimsKey1", response.getClaimsKey(1L));
        assertNull(response.getClaimsKey(2L));
        assertNull(response.getClaimsKey(3L));
        assertEquals("claimsKey2", response.getClaimsKey(4L));
        assertNull(response.getClaimsKey(5L));
    }

    @Test
    public void shouldReturnExistsFlagFromResponse() {
        assertEquals(Boolean.TRUE, response.exists("domain-with-claim.tld"));
        assertEquals(Boolean.FALSE, response.exists("domain-wihtout-claim.tld"));
        assertEquals(Boolean.FALSE, response.exists("domain.not-a-tld"));
        assertEquals(Boolean.TRUE, response.exists("just-a-label"));
        assertNull(response.exists("example3.tld"));
    }

    @Test
    public void shouldReturnExistsFlagFromResponseUsingPosition() {
        assertEquals(Boolean.TRUE, response.exists(1L));
        assertEquals(Boolean.FALSE, response.exists(2L));
        assertEquals(Boolean.FALSE, response.exists(3L));
        assertEquals(Boolean.TRUE, response.exists(4L));
        assertNull(response.exists(5L));
    }

    @Test
    public void shouldBeInitialised() {
        assertTrue(response.isInitialised());
    }

}
