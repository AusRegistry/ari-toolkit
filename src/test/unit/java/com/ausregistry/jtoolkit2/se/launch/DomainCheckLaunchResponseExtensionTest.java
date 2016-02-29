package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;

public class DomainCheckLaunchResponseExtensionTest {

    private static final String AVAILABLE_DOMAIN = "domain-avail.tld";
    private static final String PENDING_CREATE_DOMAIN = "domain-pending.tld";
    private static final String DOMAIN_APPLICATION = "domain-application.tld";

    private static final String RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
         + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
         + "  <response>"
         + "    <result code=\"1000\">"
         + "      <msg lang=\"en\">Command completed successfully</msg>"
         + "    </result>"
         + "    <extension>"
         + "      <chkData xmlns=\"urn:ietf:params:xml:ns:launch-1.0\">"
         + "        <cd>"
         + "          <name exists=\"0\">" + AVAILABLE_DOMAIN + "</name>"
         + "        </cd>"
         + "        <cd>"
         + "          <name exists=\"1\">" + PENDING_CREATE_DOMAIN + "</name>"
         + "        </cd>"
         + "        <cd>"
         + "            <name exists=\"0\">" + DOMAIN_APPLICATION + "</name>"
         + "            <claimKey>SomeKeyValue</claimKey>"
         + "        </cd>"
         + "      </chkData>"
         + "    </extension>"
         + "    <trID>"
         + "      <clTRID>ABC-12345</clTRID>"
         + "      <svTRID>57f39ac6-abd2-4fea-9a80-e791d1af86f7</svTRID>"
         + "    </trID>"
         + "  </response>"
         + "</epp>";

    private DomainCheckLaunchResponseExtension response;

    @Before
    public void setUp() throws Exception {
        response = new DomainCheckLaunchResponseExtension();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(RESPONSE_XML);
        response.fromXML(doc);
    }

    @Test
    public void shouldParseAllTheDomainsInTheExtendedAvailabilityCheckResponseXml() {
        assertTrue(response.isInitialised());
    }

    @Test
    public void shouldReturnCorrectStateForDomainThatIsAvailable() {
        assertFalse(response.exists(AVAILABLE_DOMAIN));
        assertNull(response.getClaimsKey(AVAILABLE_DOMAIN));
    }

    @Test
    public void shouldReturnCorrectStateDetailsForDomainThatIsPendingCreate() {
        assertTrue(response.exists(PENDING_CREATE_DOMAIN));
        assertNull(response.getClaimsKey(PENDING_CREATE_DOMAIN));
    }

    @Test
    public void shouldReturnCorrectStateDetailsForAnApplication() {
        assertFalse(response.exists(DOMAIN_APPLICATION));
        assertEquals(response.getClaimsKey(DOMAIN_APPLICATION), "SomeKeyValue");
    }
}
