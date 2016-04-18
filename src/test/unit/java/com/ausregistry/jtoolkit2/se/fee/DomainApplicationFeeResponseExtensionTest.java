package com.ausregistry.jtoolkit2.se.fee;

import org.junit.Test;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DomainApplicationFeeResponseExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void shouldGetFeeExtension() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainApplicationFeeResponseExtension feeResponseExtension =
                new DomainApplicationFeeResponseExtension(ResponseExtension.CREATE);
        final XMLDocument doc =
                PARSER.parse(getCreateResponseExpectedXml(dnsForm, true));

        response.registerExtension(feeResponseExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Fee extension should have been initialised",
                feeResponseExtension.isInitialised());
        assertEquals("USD", feeResponseExtension.getCurrency());
        assertEquals("10.00", feeResponseExtension.getApplicationFee().toPlainString());
        assertEquals("15.00", feeResponseExtension.getAllocationFee().toPlainString());
        assertEquals("30.00", feeResponseExtension.getRegistrationFee().toPlainString());

    }

    @Test
    public void shouldNotGetFeeExtension() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainCreateFeeResponseExtension feeResponseExtension =
                new DomainCreateFeeResponseExtension(ResponseExtension.CREATE);
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(domainName, false));

        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Fee should not have been initialised", feeResponseExtension.isInitialised());
    }

    private static String getCreateResponseExpectedXml(final String domainName, boolean feeExtension) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append("<response>");
        result.append("<result code=\"1000\">");
        result.append("<msg>Command completed successfully</msg>");
        result.append("</result>");
        result.append("<resData>");
        result.append("<domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append("<domain:name>" + domainName + "</domain:name>");
        result.append("<domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate>");
        result.append("<domain:exDate>2001-04-03T22:00:00.0Z</domain:exDate>");
        result.append("</domain:creData>");
        result.append("</resData>");

        if (feeExtension) {
            result.append("<extension>");
            result.append("<fee:creData xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">");
            result.append("<fee:currency>" + "USD" + "</fee:currency>");
            result.append("<fee:fee description=\"Application Fee\">" + "10.00" + "</fee:fee>");
            result.append("<fee:fee description=\"Allocation Fee\">" + "15.00" + "</fee:fee>");
            result.append("<fee:fee description=\"Registration Fee\">" + "30.00" + "</fee:fee>");
            result.append("</fee:creData>");
            result.append("</extension>");
        }

        result.append("<trID>");
        result.append("<clTRID>ABC-12345</clTRID>");
        result.append("<svTRID>54321-XYZ</svTRID>");
        result.append("</trID>");
        result.append("</response>");
        result.append("</epp>");
        return result.toString();
    }
}
