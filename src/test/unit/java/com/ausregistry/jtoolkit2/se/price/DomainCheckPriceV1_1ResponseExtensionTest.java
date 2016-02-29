package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.se.PeriodUnit;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;

public class DomainCheckPriceV1_1ResponseExtensionTest {

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
                    + "            <chkData xmlns=\"urn:ar:params:xml:ns:price-1.1\">"
                    + "                <cd>"
                    + "                    <name premium=\"1\">premiumdomain.zone</name>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <createPrice>100.00</createPrice>"
                    + "                    <renewPrice>150.00</renewPrice>"
                    + "                    <restorePrice>200.00</restorePrice>"
                    + "                    <transferPrice>250.00</transferPrice>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name>def.notexistzone</name>"
                    + "                    <reason>Invalid domain name</reason>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name premium=\"0\">nonpremiumdomain.zone</name>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <createPrice>50.00</createPrice>"
                    + "                    <renewPrice>55.00</renewPrice>"
                    + "                    <restorePrice>60.00</restorePrice>"
                    + "                    <transferPrice>65.00</transferPrice>"
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

    private DomainCheckPriceV1_1ResponseExtension domainCheckPriceV11Response;

    @Before
    public void setUp() throws Exception {
        domainCheckPriceV11Response = new DomainCheckPriceV1_1ResponseExtension();

        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(PRICE_XML);
        domainCheckPriceV11Response.fromXML(doc);
    }

    @Test
    public void testGetPricesForPremiumDomain() throws Exception {
        assertTrue(domainCheckPriceV11Response.isPremium("premiumdomain.zone"));
        assertEquals(1, domainCheckPriceV11Response.getPeriod("nonpremiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV11Response.getPeriod("nonpremiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(100.0), domainCheckPriceV11Response.getCreatePrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(150.0), domainCheckPriceV11Response.getRenewPrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(200.0), domainCheckPriceV11Response.getRestorePrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(250.0), domainCheckPriceV11Response.getTransferPrice("premiumdomain.zone"));
    }

    @Test
    public void testGetPricesForNonPremiumDomains() {
        assertFalse(domainCheckPriceV11Response.isPremium("nonpremiumdomain.zone"));
        assertEquals(1, domainCheckPriceV11Response.getPeriod("nonpremiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV11Response.getPeriod("nonpremiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceV11Response.getCreatePrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceV11Response.getRenewPrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(60.0), domainCheckPriceV11Response.getRestorePrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(65.0), domainCheckPriceV11Response.getTransferPrice("nonpremiumdomain.zone"));
    }

    @Test
    public void testGetReasonForInvalidRegistrationPeriods() {
        assertFalse(domainCheckPriceV11Response.isPremium("invalidperioddomain.zone"));
        assertEquals(1, domainCheckPriceV11Response.getPeriod("invalidperioddomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV11Response.getPeriod("invalidperioddomain.zone").getUnit());
        assertEquals("Valid Registration Periods 2-10", domainCheckPriceV11Response.getReason("invalidperioddomain.zone"));
    }

    @Test
    public void testShouldBeAbleToGetValuesUsingPosition() {
        assertTrue(domainCheckPriceV11Response.isPremium(1L));
        assertEquals(1, domainCheckPriceV11Response.getPeriod(1L).getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV11Response.getPeriod(1L).getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceV11Response.getCreatePrice(3L));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceV11Response.getRenewPrice(3L));
        assertEquals(BigDecimal.valueOf(60.0), domainCheckPriceV11Response.getRestorePrice(3L));
        assertEquals(BigDecimal.valueOf(65.0), domainCheckPriceV11Response.getTransferPrice(3L));
        assertEquals("Invalid domain name", domainCheckPriceV11Response.getReason(2L));
    }

    @Test
    public void testShouldGetNullForInvalidDomains() {
        assertNull(domainCheckPriceV11Response.isPremium("invalid"));
        assertNull(domainCheckPriceV11Response.getPeriod("invalid"));
        assertNull(domainCheckPriceV11Response.getCreatePrice("invalid"));
        assertNull(domainCheckPriceV11Response.getRenewPrice("invalid"));
        assertNull(domainCheckPriceV11Response.getRestorePrice("invalid"));
        assertNull(domainCheckPriceV11Response.getTransferPrice("invalid"));
        assertNull(domainCheckPriceV11Response.getReason("invalid"));
    }

}
