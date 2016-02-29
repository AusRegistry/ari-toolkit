package com.ausregistry.jtoolkit2.se.rgp;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

public class DomainInfoRgpResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testInfoWithRgpExtensionMultipleStatusesResponse() throws Exception {
        final String domainName = "test.com.au";
        final List<RgpStatus> expectedRgpStatuses = Arrays.asList(
                new RgpStatus("addPeriod", "lang", "message"),
                new RgpStatus("renewPeriod", "lang", "message"));

        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoRgpResponseExtension rgpExtension = new DomainInfoRgpResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc = PARSER.parse(getInfoResponseWithRgpExtensionExpectedXml(domainName, expectedRgpStatuses));

        response.registerExtension(rgpExtension);
        response.fromXML(doc);

        assertTrue("RgpExtension should be initialised", rgpExtension.isInitialised());
        assertEquals("Should return domain name", domainName, response.getName());
        List<RgpStatus> rgpStatuses = rgpExtension.getRgpStatuses();
        assertEquals("RgpStatuses size differ", expectedRgpStatuses.size(), rgpStatuses.size());
        for (int i = 0; i < expectedRgpStatuses.size(); i++) {
            RgpStatus expectedRgpStatus = expectedRgpStatuses.get(i);
            RgpStatus rgpStatus = rgpStatuses.get(i);
            assertEquals("Should return expected status", expectedRgpStatus.getStatus(), rgpStatus.getStatus());
            assertEquals("Should return expected language", expectedRgpStatus.getLanguage(), rgpStatus.getLanguage());
            assertEquals("Should return expected message", expectedRgpStatus.getMessage(), rgpStatus.getMessage());
        }
    }

    @Test
    public void testInfoWithRgpExtensionResponse() throws Exception {
        final String domainName = "test.com.au";
        final List<RgpStatus> expectedRgpStatuses = Arrays.asList(new RgpStatus("addPeriod", "lang", "message"));

        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoRgpResponseExtension rgpExtension = new DomainInfoRgpResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc = PARSER.parse(getInfoResponseWithRgpExtensionExpectedXml(domainName, expectedRgpStatuses));

        response.registerExtension(rgpExtension);
        response.fromXML(doc);

        assertTrue("RgpExtension should be initialised", rgpExtension.isInitialised());
        assertEquals("Should return domain name", domainName, response.getName());
        List<RgpStatus> rgpStatuses = rgpExtension.getRgpStatuses();
        assertEquals("RgpStatuses size differ", expectedRgpStatuses.size(), rgpStatuses.size());
        for (int i = 0; i < expectedRgpStatuses.size(); i++) {
            RgpStatus expectedRgpStatus = expectedRgpStatuses.get(i);
            RgpStatus rgpStatus = rgpStatuses.get(i);
            assertEquals("Should return expected status", expectedRgpStatus.getStatus(), rgpStatus.getStatus());
            assertEquals("Should return expected language", expectedRgpStatus.getLanguage(), rgpStatus.getLanguage());
            assertEquals("Should return expected message", expectedRgpStatus.getMessage(), rgpStatus.getMessage());
        }
    }

    @Test
    public void testInfoWithNoRgpExtensionInResponse() throws Exception {
        final String domainName = "test.com.au";
        final List<RgpStatus> expectedRgpStatuses = null;

        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoRgpResponseExtension rgpExtension = new DomainInfoRgpResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc = PARSER.parse(getInfoResponseWithRgpExtensionExpectedXml(domainName, expectedRgpStatuses));

        response.registerExtension(rgpExtension);
        response.fromXML(doc);

        assertFalse("Extension initialised", rgpExtension.isInitialised());
        assertEquals("Should return domain name", domainName, response.getName());
        assertNull("Should not return RgpStatuses", rgpExtension.getRgpStatuses());
    }

    private static String getInfoResponseWithRgpExtensionExpectedXml(final String domainName, List<RgpStatus> rgpStatuses) {
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
        result.append("<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append("<name>" + domainName + "</name>");
        result.append("<roid>D0000003-AR</roid>");
        result.append("<status s=\"ok\" lang=\"en\"/>");
        result.append("<registrant>EXAMPLE</registrant>");
        result.append("<contact type=\"tech\">EXAMPLE</contact>");
        result.append("<ns>");
        result.append("<hostObj>ns1.example.com.au</hostObj>");
        result.append("<hostObj>ns2.example.com.au</hostObj>");
        result.append("</ns>");
        result.append("<host>ns1.example.com.au</host>");
        result.append("<host>ns2.exmaple.com.au</host>");
        result.append("<clID>Registrar</clID>");
        result.append("<crID>Registrar</crID>");
        result.append("<crDate>2006-02-09T15:44:58.0Z</crDate>");
        result.append("<exDate>2008-02-10T00:00:00.0Z</exDate>");
        result.append("<authInfo>");
        result.append("<pw>0192pqow</pw>");
        result.append("</authInfo>");
        result.append("</infData>");
        result.append("</resData>");

        if (rgpStatuses != null && rgpStatuses.size() > 0) {
            result.append("<extension>").append("<infData xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\"")
                    .append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:rgp-1.0 rgp-1.0.xsd\">");
            for (RgpStatus rgpStatus : rgpStatuses) {
                result.append(
                        "<rgpStatus s=\"" + rgpStatus.getStatus()
                                + "\" lang=\"" + rgpStatus.getLanguage()
                                + "\">" + rgpStatus.getMessage()).append(
                        "</rgpStatus>");
            }
            result.append("</infData></extension>");
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
