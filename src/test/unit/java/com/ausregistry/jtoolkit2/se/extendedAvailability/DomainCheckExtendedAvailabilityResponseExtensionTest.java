package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DomainCheckExtendedAvailabilityResponseExtensionTest {

    private static final String AVAILABLE_DOMAIN = "domain-avail.tld";
    private static final String PENDING_CREATE_DOMAIN = "domain-pending.tld";
    private static final String UNAVAILABLE_DOMAIN = "domain-inuse.tld";
    private static final String INVALID_DOMAIN = "domain-invalid.tld";
    private static final String RESERVED_DOMAIN = "domain-reserved.tld";
    private static final String DOMAIN_APPLICATION = "domain-application.tld";
    private static final String DOMAIN_ACTIVE_VARIANT = "domain-variant.tld";
    private static final String DOMAIN_WITHHELD_VARIANT = "domain-VaRiAnT.tld";
    private static final String DOMAIN_BLOCKED_VARIANT = "DOMAIN-variant.tld";

    private static final String RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
         + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">"
         + "  <response>"
         + "    <result code=\"1000\">"
         + "      <msg lang=\"en\">Command completed successfully</msg>"
         + "    </result>"
         + "    <extension>"
         + "      <chkData xmlns=\"urn:ar:params:xml:ns:exAvail-1.0\">"
         + "        <cd>"
         + "          <name>" + AVAILABLE_DOMAIN + "</name>"
         + "          <state s=\"available\">"
         + "            <date>2010-04-23T00:00:00Z</date>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + PENDING_CREATE_DOMAIN + "</name>"
         + "          <state s=\"pendingCreate\" />"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + UNAVAILABLE_DOMAIN + "</name>"
         + "          <state s=\"unavailable\">"
         + "            <reason lang=\"en\">In use</reason>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + INVALID_DOMAIN + "</name>"
         + "          <state s=\"invalid\">"
         + "            <reason lang=\"en\">invalid char '!'</reason>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + RESERVED_DOMAIN + "</name>"
         + "          <state s=\"reserved\">"
         + "            <reason lang=\"en\">ICANN Reserved</reason>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + DOMAIN_APPLICATION + "</name>"
         + "          <state s=\"application\">"
         + "            <phase>sunrise</phase>"
         + "            <date>2010-04-25T00:00:00Z</date>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + DOMAIN_ACTIVE_VARIANT + "</name>"
         + "          <state s=\"activatedVariant\">"
         + "            <primaryDomainName>domain-VARIANT-one.tld</primaryDomainName>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + DOMAIN_WITHHELD_VARIANT + "</name>"
         + "          <state s=\"withheldVariant\">"
         + "            <primaryDomainName>domain-VARIANT-two.tld</primaryDomainName>"
         + "          </state>"
         + "        </cd>"
         + "        <cd>"
         + "          <name>" + DOMAIN_BLOCKED_VARIANT + "</name>"
         + "          <state s=\"blockedVariant\">"
         + "            <primaryDomainName>domain-VARIANT-three.tld</primaryDomainName>"
         + "          </state>"
         + "        </cd>"
         + "      </chkData>"
         + "    </extension>"
         + "    <trID>"
         + "      <clTRID>ABC-12345</clTRID>"
         + "      <svTRID>57f39ac6-abd2-4fea-9a80-e791d1af86f7</svTRID>"
         + "    </trID>"
         + "  </response>"
         + "</epp>";

    private DomainCheckExtendedAvailabilityResponseExtension response;

    @Before
    public void setUp() throws Exception {
        response = new DomainCheckExtendedAvailabilityResponseExtension();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(RESPONSE_XML);
        response.fromXML(doc);
    }

    @Test
    public void shouldParseAllTheDomainsInTheExtendedAvailabilityCheckResponseXml() {
        assertEquals(response.getDomainExtAvailabilityStateMap().size(), 9);
    }

    @Test
    public void shouldReturnCorrectStateForDomainThatIsAvailable() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(AVAILABLE_DOMAIN);
        assertEquals(domainState.getState(), "available");
        assertEquals(domainState.getDate(), EPPDateFormatter.fromXSDateTime("2010-04-23T00:00:00Z"));
        assertNull(domainState.getReason());
        assertNull(domainState.getPhase());
        assertNull(domainState.getVariantPrimaryDomainName());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForDomainThatIsPendingCreate() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(PENDING_CREATE_DOMAIN);
        assertEquals(domainState.getState(), "pendingCreate");
        assertNull(domainState.getDate());
        assertNull(domainState.getReason());
        assertNull(domainState.getPhase());
        assertNull(domainState.getVariantPrimaryDomainName());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForDomainThatIsUnavailable() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(UNAVAILABLE_DOMAIN);
        assertEquals(domainState.getState(), "unavailable");
        assertNull(domainState.getDate());
        assertEquals(domainState.getReason(), "In use");
        assertNull(domainState.getPhase());
        assertNull(domainState.getVariantPrimaryDomainName());
    }


    @Test
    public void shouldReturnCorrectStateDetailsForDomainThatIsInvalid() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(INVALID_DOMAIN);
        assertEquals(domainState.getState(), "invalid");
        assertNull(domainState.getDate());
        assertEquals(domainState.getReason(), "invalid char '!'");
        assertNull(domainState.getPhase());
        assertNull(domainState.getVariantPrimaryDomainName());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForDomainThatIsReserved() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(RESERVED_DOMAIN);
        assertEquals(domainState.getState(), "reserved");
        assertNull(domainState.getDate());
        assertEquals(domainState.getReason(), "ICANN Reserved");
        assertNull(domainState.getPhase());
        assertNull(domainState.getVariantPrimaryDomainName());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForAnApplication() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(DOMAIN_APPLICATION);
        assertEquals(domainState.getState(), "application");
        assertEquals(domainState.getPhase(), "sunrise");
        assertEquals(domainState.getDate(), EPPDateFormatter.fromXSDateTime("2010-04-25T00:00:00Z"));
        assertNull(domainState.getReason());
        assertNull(domainState.getVariantPrimaryDomainName());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForAnActiveVariant() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(DOMAIN_ACTIVE_VARIANT);
        assertEquals(domainState.getState(), "activatedVariant");
        assertEquals(domainState.getVariantPrimaryDomainName(), "domain-VARIANT-one.tld");
        assertNull(domainState.getDate());
        assertNull(domainState.getPhase());
        assertNull(domainState.getReason());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForAWithheldVariant() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(DOMAIN_WITHHELD_VARIANT);
        assertEquals(domainState.getState(), "withheldVariant");
        assertEquals(domainState.getVariantPrimaryDomainName(), "domain-VARIANT-two.tld");
        assertNull(domainState.getDate());
        assertNull(domainState.getPhase());
        assertNull(domainState.getReason());
    }

    @Test
    public void shouldReturnCorrectStateDetailsForABlockedVariant() {
        DomainCheckExtendedAvailabilityDetails domainState = response.getStateForDomain(DOMAIN_BLOCKED_VARIANT);
        assertEquals(domainState.getState(), "blockedVariant");
        assertEquals(domainState.getVariantPrimaryDomainName(), "domain-VARIANT-three.tld");
        assertNull(domainState.getDate());
        assertNull(domainState.getPhase());
        assertNull(domainState.getReason());
    }
}
