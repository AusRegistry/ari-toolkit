package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainInfoResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testFromXML() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
    }

    @Test
    public void testGetPW() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        assertEquals("0192pqow", response.getPW());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        GregorianCalendar exp = EPPDateFormatter.fromXSDateTime("2006-02-09T15:44:58.0Z");
        GregorianCalendar act = response.getCreateDate();
        assertNotNull(act);
        assertEquals(exp, act);
    }

    @Test
    public void testGetExpireDate() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        GregorianCalendar exp = EPPDateFormatter.fromXSDateTime("2008-02-10T00:00:00.0Z");
        GregorianCalendar act = response.getExpireDate();
        assertEquals(exp, act);
    }

    @Test
    public void testGetRegistrantID() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        String exp = "EXAMPLE";
        String act = response.getRegistrantID();
        assertEquals(exp, act);
    }

    @Test
    public void testGetTechContacts() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        String[] act = response.getTechContacts();
        String[] exp = new String[] { "EXAMPLE" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetNameservers() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        String[] act = response.getNameservers();
        String[] exp = new String[] { "ns1.example.com.au", "ns2.example.com.au" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetSubordinateHosts() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        String[] act = response.getSubordinateHosts();
        String[] exp = new String[] { "ns1.example.com.au", "ns2.exmaple.com.au" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetStatuses() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml("example.com.au"));
        response.fromXML(doc);
        Status[] act = response.getStatuses();
        Status[] exp = new Status[] { new Status("ok", null, "lang") };
        assertEquals(exp[0].toString(), act[0].toString());
    }

    @Test
    public void testGetVariants() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final String variantUserForm = "\u0257\u015c\u0661.com";
        final String variantDnsForm = "xn--lga31c50h.com";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainVariantResponseExtension variantsExtension =
                new DomainVariantResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc =
                PARSER.parse(getInfoResponseExpectedXml(dnsForm, variantUserForm, variantDnsForm));

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Variant extension should have been initialised",
                variantsExtension.isInitialised());
        final ArrayList<IdnaDomainVariant> variantList = variantsExtension.getVariants();
        assertEquals("Incorrect number of variants returned", 1, variantList.size());
        assertEquals(variantDnsForm, variantList.get(0).getName());
        assertEquals(variantUserForm, variantList.get(0).getUserForm());
    }

    @Test
    public void testGetNoVariants() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainVariantResponseExtension variantsExtension =
                new DomainVariantResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(domainName));

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Variant extension should not have been initialised",
                variantsExtension.isInitialised());
    }

    private static String getInfoResponseExpectedXml(final String domainName, final boolean isIdn,
            final String userForm, final String canonicalForm, final String variantUserForm,
            final String variantDnsForm) {
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

        if (isIdn || variantDnsForm != null) {
            result.append("<extension>");
            if (isIdn) {
                result.append("<infData xmlns=\"urn:X-ar:params:xml:ns:idnadomain-1.0\"");
                result.append(" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:idnadomain-1.0 idnadomain-1.0.xsd\">");
                result.append("<userForm language=\"test\">" + userForm + "</userForm>");
                result.append("<canonicalForm>" + canonicalForm + "</canonicalForm>");
                result.append("</infData>");
            }

            if (variantDnsForm != null && variantUserForm != null) {
                result.append("<infData xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"");
                result.append(" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:variant-1.0 variant-1.0.xsd\">");
                result.append("<variant userForm=\"" + variantUserForm + "\">" + variantDnsForm + "</variant>");
                result.append("</infData>");
            }

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

    private static String getInfoResponseExpectedXml(final String domainName,
            final String variantUserForm, final String variantDnsForm) {
        return getInfoResponseExpectedXml(domainName, false, null, null, variantUserForm, variantDnsForm);
    }

    private static String getInfoResponseExpectedXml(final String domainName) {
        return getInfoResponseExpectedXml(domainName, false, null, null, null, null);
    }
}
