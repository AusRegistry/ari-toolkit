package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.DomainTransferResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DomainTransferFeeResponseExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void shouldGetFeeExtension() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final DomainTransferResponse response = new DomainTransferResponse();
        final DomainTransferFeeResponseExtension feeResponseExtension =
                new DomainTransferFeeResponseExtension(ResponseExtension.TRANSFER);
        final XMLDocument doc =
                PARSER.parse(getTransferResponseExpectedXml(dnsForm, true));

        response.registerExtension(feeResponseExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Fee extension should have been initialised",
                feeResponseExtension.isInitialised());
        assertEquals("USD", feeResponseExtension.getCurrency());
        assertEquals("10.00", feeResponseExtension.getFee().toPlainString());

    }

    @Test
    public void shouldNotGetFeeExtension() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainTransferResponse response = new DomainTransferResponse();
        final DomainTransferFeeResponseExtension feeResponseExtension =
                new DomainTransferFeeResponseExtension(ResponseExtension.TRANSFER);
        final XMLDocument doc = PARSER.parse(getTransferResponseExpectedXml(domainName, false));

        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Fee should not have been initialised", feeResponseExtension.isInitialised());
    }

    private static String getTransferResponseExpectedXml(final String domainName, boolean feeExtension) {

        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append(    "<response>");
        result.append(        "<result code=\"1000\">");
        result.append(            "<msg>Command completed successfully</msg>");
        result.append(        "</result>");
        result.append(        "<resData>");
        result.append(            "<domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append(                "<domain:name>" + domainName + "</domain:name>");
        result.append(                "<domain:trStatus>pending</domain:trStatus>");
        result.append(                "<domain:reID>ClientX</domain:reID>");
        result.append(                "<domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate>");
        result.append(                "<domain:acID>ClientY</domain:acID>");
        result.append(                "<domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate>");
        result.append(                "<domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate>");
        result.append(            "</domain:trnData>");
        result.append(        "</resData>");

        if (feeExtension) {
            result.append("<extension>");
                result.append("<fee:trnData xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">");
                result.append("<fee:currency>" + "USD" + "</fee:currency>");
                result.append("<fee:fee>" + "10.00" + "</fee:fee>");
                result.append("</fee:trnData>");
            result.append("</extension>");
        }

        result.append(        "<trID>");
        result.append(            "<clTRID>ABC-12345</clTRID>");
        result.append(            "<svTRID>54321-XYZ</svTRID>");
        result.append(        "</trID>");
        result.append(    "</response>");
        result.append("</epp>");
        return result.toString();
    }
}
