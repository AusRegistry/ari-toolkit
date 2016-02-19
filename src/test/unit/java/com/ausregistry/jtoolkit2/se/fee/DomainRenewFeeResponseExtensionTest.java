package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.DomainRenewResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class DomainRenewFeeResponseExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void shouldGetFeeExtension() throws Exception {
        final String dnsForm = "jtkutest.com.au";
        final DomainRenewResponse response = new DomainRenewResponse();
        final DomainRenewFeeResponseExtension feeResponseExtension =
                new DomainRenewFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRenewResponseExpectedXml(dnsForm, true));

        response.registerExtension(feeResponseExtension);
        response.fromXML(doc);

        assertEquals(dnsForm, response.getName());
        assertTrue("Fee extension should have been initialised", feeResponseExtension.isInitialised());
        assertEquals("USD", feeResponseExtension.getCurrency());
        assertEquals("5.00", feeResponseExtension.getFee().toPlainString());
    }

    @Test
    public void shouldNotGetFeeExtension() throws Exception {
        final String domainName = "jtkutest.com.au";
        final DomainRenewResponse response = new DomainRenewResponse();
        final DomainRenewFeeResponseExtension feeResponseExtension =
                new DomainRenewFeeResponseExtension(ResponseExtension.RENEW);
        final XMLDocument doc = PARSER.parse(getRenewResponseExpectedXml(domainName, false));

        response.registerExtension(feeResponseExtension);
        response.fromXML(doc);

        assertEquals(domainName, response.getName());
        assertFalse("Fee should not have been initialised", feeResponseExtension.isInitialised());
    }

    private static String getRenewResponseExpectedXml(final String domainName, boolean feeExtension) {
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
            result.append("<fee:fee>5.00</fee:fee>");
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