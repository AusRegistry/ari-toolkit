package com.ausregistry.jtoolkit2.se.idn;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainInfoIdnResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testGetIdnName() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final String languageTag = "test";

        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoIdnResponseExtension re = new DomainInfoIdnResponseExtension();

        final XMLDocument doc =
                PARSER.parse(getInfoResponseExpectedXml(dnsForm, true, languageTag));
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("IDN extension should have been initialised", re.isInitialised());
        assertEquals(languageTag, re.getLanguageTag());
    }

    @Test
    public void testGetNoIdn() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoIdnResponseExtension re = new DomainInfoIdnResponseExtension();

        final XMLDocument doc =
            PARSER.parse(getInfoResponseExpectedXml(domainName, false, null));
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("IDN extension should not have been initialised", re.isInitialised());
    }

    private static String getInfoResponseExpectedXml(final String domainName, final boolean isIdn,
            final String languageTag) {
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

        if (isIdn) {
            result.append("<extension>");
            result.append("<infData xmlns=\"urn:ar:params:xml:ns:idn-1.0\"");
            result.append(" xsi:schemaLocation=\"urn:ar:params:xml:ns:idn-1.0 idn-1.0.xsd\">");
            result.append("<languageTag>" + languageTag + "</languageTag>");
            result.append("</infData>");
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
