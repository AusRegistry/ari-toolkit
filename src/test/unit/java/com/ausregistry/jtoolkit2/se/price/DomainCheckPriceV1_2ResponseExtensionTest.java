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

public class DomainCheckPriceV1_2ResponseExtensionTest {

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
                    + "            <chkData xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
                    + "                <cd>"
                    + "                    <name>premiumdomain.zone</name>"
                    + "                    <category>CAT1</category>"
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
                    + "                    <name>nonpremiumdomain.zone</name>"
                    + "                    <category>STANDARD</category>"
                    + "                    <period unit=\"y\">1</period>"
                    + "                    <createPrice>50.00</createPrice>"
                    + "                    <renewPrice>55.00</renewPrice>"
                    + "                    <restorePrice>60.00</restorePrice>"
                    + "                    <transferPrice>65.00</transferPrice>"
                    + "                </cd>"
                    + "                <cd>"
                    + "                    <name>invalidperioddomain.zone</name>"
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

    private DomainCheckPriceV1_2ResponseExtension domainCheckPriceV12Response;

    @Before
    public void setUp() throws Exception {
        domainCheckPriceV12Response = new DomainCheckPriceV1_2ResponseExtension();

        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(PRICE_XML);
        domainCheckPriceV12Response.fromXML(doc);
    }

    @Test
    public void testGetPricesForPremiumDomain() throws Exception {
        assertEquals("CAT1", domainCheckPriceV12Response.getCategory("premiumdomain.zone"));
        assertTrue(domainCheckPriceV12Response.isPremium("premiumdomain.zone"));
        assertEquals(1, domainCheckPriceV12Response.getPeriod("premiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV12Response.getPeriod("premiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(100.0), domainCheckPriceV12Response.getCreatePrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(150.0), domainCheckPriceV12Response.getRenewPrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(200.0), domainCheckPriceV12Response.getRestorePrice("premiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(250.0), domainCheckPriceV12Response.getTransferPrice("premiumdomain.zone"));
    }

    @Test
    public void testGetPricesForNonPremiumDomains() {
        assertEquals("STANDARD", domainCheckPriceV12Response.getCategory("nonpremiumdomain.zone"));
        assertFalse(domainCheckPriceV12Response.isPremium("nonpremiumdomain.zone"));
        assertEquals(1, domainCheckPriceV12Response.getPeriod("nonpremiumdomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV12Response.getPeriod("nonpremiumdomain.zone").getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceV12Response.getCreatePrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceV12Response.getRenewPrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(60.0), domainCheckPriceV12Response.getRestorePrice("nonpremiumdomain.zone"));
        assertEquals(BigDecimal.valueOf(65.0), domainCheckPriceV12Response.getTransferPrice("nonpremiumdomain.zone"));
    }

    @Test
    public void testGetReasonForInvalidRegistrationPeriods() {
        assertEquals(1, domainCheckPriceV12Response.getPeriod("invalidperioddomain.zone").getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV12Response.getPeriod("invalidperioddomain.zone").getUnit());
        assertEquals("Valid Registration Periods 2-10", domainCheckPriceV12Response.getReason("invalidperioddomain.zone"));
    }

    @Test
    public void testShouldBeAbleToGetValuesUsingPosition() {
        assertEquals("CAT1", domainCheckPriceV12Response.getCategory(1L));
        assertTrue(domainCheckPriceV12Response.isPremium(1L));
        assertEquals(1, domainCheckPriceV12Response.getPeriod(1L).getPeriod());
        assertEquals(PeriodUnit.YEARS, domainCheckPriceV12Response.getPeriod(1L).getUnit());
        assertEquals(BigDecimal.valueOf(50.0), domainCheckPriceV12Response.getCreatePrice(3L));
        assertEquals(BigDecimal.valueOf(55.0), domainCheckPriceV12Response.getRenewPrice(3L));
        assertEquals(BigDecimal.valueOf(60.0), domainCheckPriceV12Response.getRestorePrice(3L));
        assertEquals(BigDecimal.valueOf(65.0), domainCheckPriceV12Response.getTransferPrice(3L));
        assertEquals("Invalid domain name", domainCheckPriceV12Response.getReason(2L));
    }

    @Test
    public void testShouldGetNullForInvalidDomains() {
        assertNull(domainCheckPriceV12Response.getCategory("invalid"));
        assertNull(domainCheckPriceV12Response.isPremium("invalid"));
        assertNull(domainCheckPriceV12Response.getPeriod("invalid"));
        assertNull(domainCheckPriceV12Response.getCreatePrice("invalid"));
        assertNull(domainCheckPriceV12Response.getRenewPrice("invalid"));
        assertNull(domainCheckPriceV12Response.getRestorePrice("invalid"));
        assertNull(domainCheckPriceV12Response.getTransferPrice("invalid"));
        assertNull(domainCheckPriceV12Response.getReason("invalid"));
    }

}
