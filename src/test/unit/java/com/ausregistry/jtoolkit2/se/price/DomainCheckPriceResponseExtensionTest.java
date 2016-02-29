package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.se.PeriodUnit;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCheckPriceResponseExtensionTest {

    private static final String PRICE_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + "     xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "    <response>"
                    + "        <result code=\"1000\">"
                    + "            <msg>Command completed successfully</msg>"
                    + "        </result>"
                    + "        <extension>"
                    + "            <chkData xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
                    + "                <cd>"
                    + "                    <name premium=\"1\">premiumdomain.zone</name>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <price>100.00</price>"
                    + "                    <renewalPrice>150.00</renewalPrice>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name>def.notexistzone</name>"
                    + "                    <reason>Invalid domain name</reason>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name premium=\"0\">nonpremiumdomain.zone</name>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <price>50.00</price>"
                    + "                    <renewalPrice>55.00</renewalPrice>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name premium=\"0\">invalidperioddomain.zone</name>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <reason>Valid Registration Periods 2-10</reason>"
                    + "                </cd>"
                    + "            </chkData>"
                    + "        </extension>"
                    + "        <trID>"
                    + "            <clTRID>ABC-12345</clTRID>"
                    + "            <svTRID>54322-XYZ</svTRID>"
                    + "        </trID>"
                    + "    </response>"
                    + "</epp>";

    private DomainCheckPriceResponseExtension domainCheckPriceResponse;

    @Before
    public void setUp() throws Exception {
        domainCheckPriceResponse = new DomainCheckPriceResponseExtension();

        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(PRICE_XML);
        domainCheckPriceResponse.fromXML(doc);
    }

    @Test
    public void testGetCreateAndRenewPriceForPremiumDomain() throws Exception {
        assertTrue(domainCheckPriceResponse.isPremium("premiumdomain.zone"));
        assertEquals(1, domainCheckPriceResponse.getPeriod("nonpremiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceResponse.getPeriod("nonpremiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(100.0), domainCheckPriceResponse.getCreatePrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(150.0), domainCheckPriceResponse.getRenewPrice("premiumdomain.zone"));
    }

    @Test
    public void testGetCreateAndRenewPriceForNonPremiumDomains() {
        assertFalse(domainCheckPriceResponse.isPremium("nonpremiumdomain.zone"));
        assertEquals(1, domainCheckPriceResponse.getPeriod("nonpremiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceResponse.getPeriod("nonpremiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceResponse.getCreatePrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceResponse.getRenewPrice("nonpremiumdomain.zone"));
    }

    @Test
    public void testGetReasonForInvalidRegistrationPeriods() {
        assertFalse(domainCheckPriceResponse.isPremium("invalidperioddomain.zone"));
        assertEquals(1, domainCheckPriceResponse.getPeriod("invalidperioddomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceResponse.getPeriod("invalidperioddomain.zone").getUnit());
        assertEquals("Valid Registration Periods 2-10", domainCheckPriceResponse.getReason("invalidperioddomain.zone"));
    }

    @Test
    public void testShouldBeAbleToGetValuesUsingPosition() {
        assertTrue(domainCheckPriceResponse.isPremium(1L));
        assertEquals(1, domainCheckPriceResponse.getPeriod(1L).getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceResponse.getPeriod(1L).getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceResponse.getCreatePrice(3L));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceResponse.getRenewPrice(3L));
        assertEquals("Invalid domain name", domainCheckPriceResponse.getReason(2L));
    }

    @Test
    public void testShouldGetNullForInvalidDomains() {
        assertNull(domainCheckPriceResponse.isPremium("invalid"));
        assertNull(domainCheckPriceResponse.getPeriod("invalid"));
        assertNull(domainCheckPriceResponse.getCreatePrice("invalid"));
        assertNull(domainCheckPriceResponse.getRenewPrice("invalid"));
        assertNull(domainCheckPriceResponse.getReason("invalid"));
    }

}
