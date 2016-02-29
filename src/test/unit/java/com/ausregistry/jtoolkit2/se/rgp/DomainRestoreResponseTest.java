package com.ausregistry.jtoolkit2.se.rgp;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DomainRestoreResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testRestoreResponse() throws Exception {
        final List<RgpStatus> expectedRgpStatuses = Arrays.asList(new RgpStatus("pendingRestore", "lang", "a message"));

        final DomainRestoreResponse response = new DomainRestoreResponse();
        final XMLDocument doc = PARSER.parse(getUpdateResponseExpectedXml(expectedRgpStatuses));

        response.fromXML(doc);

        List<RgpStatus> rgpStatuses = response.getRgpStatuses();
        assertEquals("RgpStatuses size differ", expectedRgpStatuses.size(), rgpStatuses.size());
        for (int i = 0; i < expectedRgpStatuses.size(); i++) {
            RgpStatus expectedRgpStatus = expectedRgpStatuses.get(i);
            RgpStatus rgpStatus = rgpStatuses.get(i);
            assertEquals("Should return expected status", expectedRgpStatus.getStatus(), rgpStatus.getStatus());
            assertEquals("Should return expected language", expectedRgpStatus.getLanguage(), rgpStatus.getLanguage());
            assertEquals("Should return expected message", expectedRgpStatus.getMessage(), rgpStatus.getMessage());
        }
    }

    private static String getUpdateResponseExpectedXml(final List<RgpStatus> rgpStatuses) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append("<response>");
        result.append("<result code=\"1000\">");
        result.append("<msg>Command completed successfully</msg>");
        result.append("</result>");

        if (rgpStatuses != null && rgpStatuses.size() > 0) {
            result.append("<extension>");
            result.append("<upData xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\"");
            result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:rgp-1.0 rgp-1.0.xsd\">");
            for (RgpStatus rgpStatus : rgpStatuses) {
                result.append("<rgpStatus s=\"" + rgpStatus.getStatus()
                        + "\" lang=\"" + rgpStatus.getLanguage() + "\">"
                        + rgpStatus.getMessage());
            }
            result.append("</rgpStatus></upData></extension>");

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
