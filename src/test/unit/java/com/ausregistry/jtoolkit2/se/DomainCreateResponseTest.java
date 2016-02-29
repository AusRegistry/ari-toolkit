package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCreateResponseTest {

    private static final XMLParser PARSER = new XMLParser();


    @Test
    public void testGetName() throws Exception {
        final DomainCreateResponse response = new DomainCreateResponse();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml("example.com"));
        response.fromXML(doc);
        assertEquals("example.com", response.getName());
    }

    @Test
    public void testGetExpiryDate() throws Exception {
        final DomainCreateResponse response = new DomainCreateResponse();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml("example.com"));
        response.fromXML(doc);
        assertEquals(EPPDateFormatter.fromXSDateTime("2001-04-03T22:00:00.0Z"), response.getExpiryDate());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        final DomainCreateResponse response = new DomainCreateResponse();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml("example.com"));
        response.fromXML(doc);
        assertEquals(EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"), response.getCreateDate());
    }

    @Test
    public void testGetCLTRID() throws Exception {
        final DomainCreateResponse response = new DomainCreateResponse();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml("example.com"));
        response.fromXML(doc);
        assertEquals("ABC-12345", response.getCLTRID());
    }

    @Test
    public void testGetIdnName() throws Exception {
        final String userForm = "\u0257\u018c\u0661.com";
        final String dnsForm = "xn--xha91b83h.com";
        final String canonicalForm = "\u0257\u018c1.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainIdnaResponseExtension idnaExtension =
                new DomainIdnaResponseExtension(ResponseExtension.CREATE);
        final XMLDocument doc =
                PARSER.parse(getCreateResponseExpectedXml(dnsForm, true, userForm, canonicalForm,
                        null, null));
        response.registerExtension(idnaExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertEquals(userForm, idnaExtension.getUserFormName());
        assertEquals(canonicalForm, idnaExtension.getCanonicalForm());
        assertEquals("test", idnaExtension.getLanguage());
    }

    @Test
    public void testGetNoIdn() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainIdnaResponseExtension re =
            new DomainIdnaResponseExtension(ResponseExtension.CREATE);

        final XMLDocument doc =
            PARSER.parse(getCreateResponseExpectedXml(domainName));
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("IDN extension should not have been initialised", re.isInitialised());
    }

    @Test
    public void testGetVariants() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final String variantUserForm = "\u0257\u015c\u0661.com";
        final String variantDnsForm = "xn--lga31c50h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainVariantResponseExtension variantsExtension =
                new DomainVariantResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc =
                PARSER.parse(getCreateResponseExpectedXml(dnsForm, variantUserForm, variantDnsForm));

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
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainVariantResponseExtension variantsExtension =
                new DomainVariantResponseExtension(ResponseExtension.CREATE);
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(domainName));

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Variants should not have been initialised", variantsExtension.isInitialised());
    }

    @Test
    public void testLdhOnlyGetName() throws Exception {
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainIdnaResponseExtension re = new DomainIdnaResponseExtension(ResponseExtension.CREATE);
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml("example.com", true, "example.com", "example.com", null, null));
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals("example.com", response.getName());
        assertEquals("example.com", re.getUserFormName());
        assertEquals("example.com", re.getCanonicalForm());
    }

    private static String getCreateResponseExpectedXml(final String domainName,
            final boolean isIdn, final String userForm, final String canonicalForm,
            final String variantUserForm, final String variantDnsForm) {
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
        result.append(            "<domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append(                "<domain:name>" + domainName + "</domain:name>");
        result.append(                "<domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate>");
        result.append(                "<domain:exDate>2001-04-03T22:00:00.0Z</domain:exDate>");
        result.append(            "</domain:creData>");
        result.append(        "</resData>");

        if (isIdn || variantDnsForm != null) {
            result.append("<extension>");

            if (isIdn) {
                result.append("<creData xmlns=\"urn:X-ar:params:xml:ns:idnadomain-1.0\"");
                result.append(" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:idnadomain-1.0 idnadomain-1.0.xsd\">");
                result.append("<userForm language=\"test\">" + userForm + "</userForm>");
                result.append("<canonicalForm>" + canonicalForm + "</canonicalForm>");
                result.append("</creData>");
            }

            if (variantDnsForm != null && variantUserForm != null) {
                result.append("<infData xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"");
                result.append(" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:variant-1.0 variant-1.0.xsd\">");
                result.append("<variant userForm=\"" + variantUserForm + "\">" + variantDnsForm + "</variant>");
                result.append("</infData>");
            }

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

    private static String getCreateResponseExpectedXml(final String domainName) {
        return getCreateResponseExpectedXml(domainName, domainName, false);
    }

    private static String getCreateResponseExpectedXml(final String domainName, final String dnsForm,
            final boolean isIdn) {
        return getCreateResponseExpectedXml(dnsForm, isIdn, domainName, null, null, null);
    }

    private static String getCreateResponseExpectedXml(final String domainName,
            final String variantUserForm, final String variantDnsForm) {
        return getCreateResponseExpectedXml(domainName, false, null, null, variantUserForm,
                variantDnsForm);
    }
}
