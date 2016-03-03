package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.se.rgp.DomainRestoreResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class DomainRestoreFeeResponseExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void shouldNotGetFeeExtensionWithRestoreOnly() throws Exception {
        final String dnsForm = "jtkutest.com.au";
        final DomainRestoreResponse response = new DomainRestoreResponse();
        final DomainRestoreFeeResponseExtension restoreFeeResponseFeeExtension =
                new DomainRestoreFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRestoreResponseExpectedXml(dnsForm, false, false, false));

        response.registerExtension(restoreFeeResponseFeeExtension);
        response.fromXML(doc);

        assertFalse("Fee extension should not be initialised", restoreFeeResponseFeeExtension.isInitialised());
    }

    @Test
    public void shouldGetFeeExtensionWithRestoreOnly() throws Exception {
        final String dnsForm = "jtkutest.com.au";
        final DomainRestoreResponse response = new DomainRestoreResponse();
        final DomainRestoreFeeResponseExtension restoreFeeResponseFeeExtension =
                new DomainRestoreFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRestoreResponseExpectedXml(dnsForm, true, true, false));

        response.registerExtension(restoreFeeResponseFeeExtension);
        response.fromXML(doc);

        assertTrue("Fee extension should have been initialised", restoreFeeResponseFeeExtension.isInitialised());
        assertEquals("USD", restoreFeeResponseFeeExtension.getCurrency());
        assertEquals("60.00", restoreFeeResponseFeeExtension.getRestoreFee().toPlainString());
        assertNull(restoreFeeResponseFeeExtension.getRenewFee());
    }

    @Test
    public void shouldGetFeeExtensionWithRenewOnly() throws Exception {
        final String dnsForm = "jtkutest.com.au";
        final DomainRestoreResponse response = new DomainRestoreResponse();
        final DomainRestoreFeeResponseExtension restoreFeeResponseFeeExtension =
                new DomainRestoreFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRestoreResponseExpectedXml(dnsForm, true, false, true));

        response.registerExtension(restoreFeeResponseFeeExtension);
        response.fromXML(doc);

        assertTrue("Fee extension should have been initialised", restoreFeeResponseFeeExtension.isInitialised());
        assertEquals("USD", restoreFeeResponseFeeExtension.getCurrency());
        assertEquals("10.00", restoreFeeResponseFeeExtension.getRenewFee().toPlainString());
        assertNull(restoreFeeResponseFeeExtension.getRestoreFee());
    }

    @Test
    public void shouldGetFeeExtensionWithRenewAndRestoreFees() throws Exception {
        final String dnsForm = "jtkutest.com.au";
        final DomainRestoreResponse response = new DomainRestoreResponse();
        final DomainRestoreFeeResponseExtension restoreFeeResponseFeeExtension =
                new DomainRestoreFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRestoreResponseExpectedXml(dnsForm, true, true, true));

        response.registerExtension(restoreFeeResponseFeeExtension);
        response.fromXML(doc);

        assertTrue("Fee extension should have been initialised", restoreFeeResponseFeeExtension.isInitialised());
        assertEquals("USD", restoreFeeResponseFeeExtension.getCurrency());
        assertEquals("10.00", restoreFeeResponseFeeExtension.getRenewFee().toPlainString());
        assertEquals("60.00", restoreFeeResponseFeeExtension.getRestoreFee().toPlainString());
    }

    private static String getRestoreResponseExpectedXml(final String domainName,
                                                                         boolean feeExtension,
                                                                         boolean withRestore,
                                                                         boolean withRenew) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">");
        result.append("<response>");
        result.append("<result code=\"1000\">");
        result.append("<msg>Command completed successfully</msg>");
        result.append("</result>");
        result.append("<resData>");
        result.append("<domain:renData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\">");
        result.append("<domain:name>" + domainName + "</domain:name>");
        result.append("<domain:exDate>2005-04-03T22:00:00.0Z</domain:exDate>");
        result.append("</domain:renData>");
        result.append("</resData>");
        if (feeExtension) {
            result.append("<extension>");
            result.append("<fee:renData xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">");
            result.append("<fee:currency>USD</fee:currency>");
            if (withRenew) {
                result.append("<fee:fee description=\"Renewal Fee\">10.00</fee:fee>");
            }
            if (withRestore) {
                result.append("<fee:fee description=\"Restore Fee\">60.00</fee:fee>");
            }
            result.append("</fee:renData>");
            result.append("</extension>");
        }
        result.append("<trID>");
        result.append("<clTRID>ABC-12345</clTRID>");
        result.append("<svTRID>54322-XYZ</svTRID>");
        result.append("</trID>");
        result.append("</response>");
        result.append("</epp>");
        return result.toString();
    }

}