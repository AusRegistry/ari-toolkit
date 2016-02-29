package com.ausregistry.jtoolkit2.se.variant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.DomainVariantResponseExtensionV1_1;
import com.ausregistry.jtoolkit2.se.IdnDomainVariant;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import org.junit.Test;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCreateVariantV1_1ResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testGetVariantsV1_1() throws Exception {
        final String dnsForm = "xn--xha91b83h.com";
        final String variantDnsForm = "xn--lga31c50h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainVariantResponseExtensionV1_1 variantsExtension =
                new DomainVariantResponseExtensionV1_1(ResponseExtension.INFO);
        final XMLDocument doc =
                PARSER.parse(getCreateResponseExpectedXml(dnsForm, variantDnsForm));

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("Variant extension should have been initialised",
                variantsExtension.isInitialised());
        final ArrayList<IdnDomainVariant> variantList = variantsExtension.getVariants();
        assertEquals("Incorrect number of variants returned", 1, variantList.size());
        assertEquals(variantDnsForm, variantList.get(0).getName());
    }

    @Test
    public void testGetNoVariantsV1_1() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainCreateResponse response = new DomainCreateResponse();
        final DomainVariantResponseExtensionV1_1 variantsExtension =
                new DomainVariantResponseExtensionV1_1(ResponseExtension.CREATE);
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml(domainName, domainName));

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Variants should not have been initialised", variantsExtension.isInitialised());
    }

    private static String getCreateResponseExpectedXml(final String domainName, final String variantDnsForm) {
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

        if (variantDnsForm != null) {
            result.append("<extension>");
                result.append("<infData xmlns=\"urn:ar:params:xml:ns:variant-1.1\"");
                result.append(" xsi:schemaLocation=\"urn:ar:params:xml:ns:variant-1.1 variant-1.1.xsd\">");
                result.append("<variant>" + variantDnsForm + "</variant>");
                result.append("</infData>");
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
