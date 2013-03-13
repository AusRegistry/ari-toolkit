package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

import static org.junit.Assert.assertEquals;

public class DomainCheckPremiumResponseTest {

    private static XMLParser xmlParser;

    private DomainCheckPremiumResponse domainCheckPremiumResponse;
    private static final String premiumXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><chkData xmlns=\"urn:ar:params:xml:ns:premium-1.0\"><cd><name premium=\"1\">premiumdomain1.zone</name><price>100.00</price><renewalPrice>150.00</renewalPrice></cd><cd><name premium=\"1\">premiumdomain2.zone</name><price>500.00</price><renewalPrice>550.00</renewalPrice></cd><cd><name premium=\"0\">nonpremiumdomain1.zone</name><price>500.00</price><renewalPrice>550.00</renewalPrice></cd></chkData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";

    @Before
    public void setUp() throws Exception {
        xmlParser = new XMLParser();
        domainCheckPremiumResponse = new DomainCheckPremiumResponse();

        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(premiumXml);
        domainCheckPremiumResponse.fromXML(doc);
    }

    @Test
    public void testGetCreateAndRenewPriceForPremiumDomain() throws Exception {
        assertEquals(domainCheckPremiumResponse.isPremium("premiumdomain1.zone"), true);
        assertEquals(domainCheckPremiumResponse.getCreatePrice("premiumdomain1.zone"), BigDecimal.valueOf(100.0));
        assertEquals(domainCheckPremiumResponse.getRenewPrice("premiumdomain1.zone"), BigDecimal.valueOf(150.0));

        assertEquals(domainCheckPremiumResponse.isPremium("premiumdomain2.zone"), true);
        assertEquals(domainCheckPremiumResponse.getCreatePrice("premiumdomain2.zone"), BigDecimal.valueOf(500.0));
        assertEquals(domainCheckPremiumResponse.getRenewPrice("premiumdomain2.zone"), BigDecimal.valueOf(550.0));
    }

    @Test
    public void testShouldNotHavePriceForNonPremiumDomains() {
        assertEquals(domainCheckPremiumResponse.isPremium("nonpremiumdomain1.zone"), false);
        assertEquals(domainCheckPremiumResponse.getCreatePrice("nonpremiumdomain1.zone"), null);
        assertEquals(domainCheckPremiumResponse.getRenewPrice("nonpremiumdomain1.zone"), null);
    }

    @Test
    public void testShouldBeAbleToGetValuesUsingPosition() {
        assertEquals(domainCheckPremiumResponse.isPremium(1L), true);
        assertEquals(domainCheckPremiumResponse.getCreatePrice(2L), BigDecimal.valueOf(500.0));
        assertEquals(domainCheckPremiumResponse.getRenewPrice(3L), null);
    }

    @Test
    public void testShouldGetNullForInvalidDomains() {
        assertEquals(domainCheckPremiumResponse.isPremium("invalid"), null);
        assertEquals(domainCheckPremiumResponse.getCreatePrice("invalid"), null);
        assertEquals(domainCheckPremiumResponse.getRenewPrice("invalid"), null);

        assertEquals(domainCheckPremiumResponse.isPremium("invalid"), null);
        assertEquals(domainCheckPremiumResponse.getCreatePrice("invalid"), null);
        assertEquals(domainCheckPremiumResponse.getRenewPrice("invalid"), null);
    }

}
