package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public final class SecDnsDomainInfoResponseTest {

    private static final XMLParser PARSER = new XMLParser();
    private static int signedShortLimit = Short.MAX_VALUE + 1;
    private static int signedByteLimit = Byte.MAX_VALUE + 1;

    @Test
    public void testSecDnsInfoExtensionAllFields() throws Exception {
        final String domainName = "test.com.au";
        final DomainInfoResponse response = new DomainInfoResponse();
        final SecDnsDomainInfoResponseExtension re = new SecDnsDomainInfoResponseExtension();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(domainName, false));
        response.registerExtension(re);
        response.fromXML(doc);
        assertTrue(re.isInitialised());
        assertEquals(domainName, response.getName());
        assertEquals(1, re.getInfData().getDsDataList().size());
        assertFirstDSData(re.getInfData().getDsDataList().get(0));
    }

    @Test
    public void testSecDnsInfoExtensionOnlyKeyData() throws Exception {
        final String domainName = "test.com.au";
        final DomainInfoResponse response = new DomainInfoResponse();
        final SecDnsDomainInfoResponseExtension re = new SecDnsDomainInfoResponseExtension();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(domainName, false, true, false));
        response.registerExtension(re);
        response.fromXML(doc);
        assertTrue(re.isInitialised());
        assertEquals(domainName, response.getName());
        assertEquals(1, re.getInfData().getKeyDataList().size());
        assertKeyData(re.getInfData().getKeyDataList().get(0));
    }

    @Test
    public void testSecDnsInfoExtensionMultipleDsRecords() throws Exception {
        final String domainName = "test.com.au";
        final DomainInfoResponse response = new DomainInfoResponse();
        final SecDnsDomainInfoResponseExtension re = new SecDnsDomainInfoResponseExtension();
        final XMLDocument doc = PARSER.parse(getInfoResponseExpectedXml(domainName, true));
        response.registerExtension(re);
        response.fromXML(doc);
        assertTrue(re.isInitialised());
        assertEquals(domainName, response.getName());
        assertEquals(2, re.getInfData().getDsDataList().size());
        assertFirstDSData(re.getInfData().getDsDataList().get(0));
        assertSecondDSData(re.getInfData().getDsDataList().get(1));
    }

    @Test
    public void testSecDnsInfoNoExtensionInitialised() throws Exception {
        final String domainName = "test.com.au";
        final DomainInfoResponse response = new DomainInfoResponse();
        final SecDnsDomainInfoResponseExtension re = new SecDnsDomainInfoResponseExtension();
        final XMLDocument doc = PARSER.parse(getInfoResponseNoExtensionExpectedXml(domainName));
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse(re.isInitialised());
    }


    private static void assertFirstDSData(final DSData dsData) {
        assertEquals("Incorrect ds keyTag", signedShortLimit, dsData.getKeyTag());
        assertEquals("Incorrect ds alg", signedByteLimit, dsData.getAlg());
        assertEquals("Incorrect ds digestType", 1, dsData.getDigestType());
        assertEquals("Incorrect ds digest", "49FD46E6C4B45C55D4AC", dsData.getDigest());

        assertKeyData(dsData.getKeyData());
    }

    private static void assertKeyData(final KeyData keyData) {
        assertEquals("Incorrect keyData flags", signedShortLimit, keyData.getFlags());
        assertEquals("Incorrect keyData protocol", 3, keyData.getProtocol());
        assertEquals("Incorrect keyData alg", signedByteLimit, keyData.getAlg());
        assertEquals("Incorrect keyData PubKey", "AQPJ////4Q==", keyData.getPubKey());
    }

    private static void assertSecondDSData(final DSData dsData) {
        assertEquals("Incorrect ds keyTag", 14321, dsData.getKeyTag());
        assertEquals("Incorrect ds alg", 2, dsData.getAlg());
        assertEquals("Incorrect ds digestType", 5, dsData.getDigestType());
        assertEquals("Incorrect ds digest", "39FD46E6C4B45C55D4AC", dsData.getDigest());
        assertNull("Incorrect keyData", dsData.getKeyData());
    }

    private static String getInfoResponseExpectedXml(final String domainName, final boolean isMulitpleDs) {
        final StringBuilder result = new StringBuilder();
        buildXmlResponseBeforeExtension(domainName, result);
        buildSecDNSXmlExtension(isMulitpleDs, result);
        buildXmlResponseAfterExtension(result);

        return result.toString();
    }


    private static String getInfoResponseExpectedXml(final String domainName, final boolean isMulitpleDs, final boolean isKeyData,
            final boolean isDsData) {
        final StringBuilder result = new StringBuilder();
        buildXmlResponseBeforeExtension(domainName, result);
        buildSecDNSXmlExtension(isMulitpleDs, isKeyData, isDsData, result);
        buildXmlResponseAfterExtension(result);

        return result.toString();
    }

    private static String getInfoResponseNoExtensionExpectedXml(final String domainName) {
        final StringBuilder result = new StringBuilder();
        buildXmlResponseBeforeExtension(domainName, result);
        buildXmlResponseAfterExtension(result);

        return result.toString();
    }

    private static void buildXmlResponseAfterExtension(final StringBuilder result) {
        result.append(        "<trID>");
        result.append(            "<clTRID>ABC-12345</clTRID>");
        result.append(            "<svTRID>54321-XYZ</svTRID>");
        result.append(        "</trID>");
        result.append(    "</response>");
        result.append("</epp>");
    }

    private static void buildSecDNSXmlExtension(final boolean isMulitpleDs, final StringBuilder result) {
        buildSecDNSXmlExtension(isMulitpleDs, false, true, result);
    }

    private static void buildSecDNSXmlExtension(final boolean isMulitpleDs, final boolean isKeyData,
            final boolean isDsData, final StringBuilder result) {
        result.append(        "<extension>");
        result.append(            "<secDNS:infData xmlns:secDNS=\"urn:ietf:params:xml:ns:secDNS-1.1\"");
        result.append(                 " xsi:schemaLocation=\"urn:ietf:params:xml:ns:secDNS-1.1 secDNS-1.1.xsd\">");
        result.append(                 "<secDNS:maxSigLife>604800</secDNS:maxSigLife>");
        if (isDsData) {
            result.append("<secDNS:dsData>");
            result.append("<secDNS:keyTag>" + signedShortLimit + "</secDNS:keyTag>");
            result.append("<secDNS:alg>" + signedByteLimit + "</secDNS:alg>");
            result.append("<secDNS:digestType>1</secDNS:digestType>");
            result.append("<secDNS:digest>49FD46E6C4B45C55D4AC</secDNS:digest>");
            result.append("<secDNS:keyData>");
            result.append("<secDNS:flags>" + signedShortLimit  + "</secDNS:flags>");
            result.append("<secDNS:protocol>3</secDNS:protocol>");
            result.append("<secDNS:alg>" + signedByteLimit + "</secDNS:alg>");
            result.append("<secDNS:pubKey>AQPJ////4Q==</secDNS:pubKey>");
            result.append("</secDNS:keyData>");
            result.append("</secDNS:dsData>");
            if (isMulitpleDs) {
                result.append(                 "<secDNS:dsData>");
                result.append(                     "<secDNS:keyTag>14321</secDNS:keyTag>");
                result.append(                     "<secDNS:alg>2</secDNS:alg>");
                result.append(                     "<secDNS:digestType>5</secDNS:digestType>");
                result.append(                     "<secDNS:digest>39FD46E6C4B45C55D4AC</secDNS:digest>");
                result.append(                 "</secDNS:dsData>");
            }
        }

        if (isKeyData) {
            result.append("<secDNS:keyData>");
            result.append("<secDNS:flags>" + signedShortLimit + "</secDNS:flags>");
            result.append("<secDNS:protocol>3</secDNS:protocol>");
            result.append("<secDNS:alg>" + signedByteLimit + "</secDNS:alg>");
            result.append("<secDNS:pubKey>AQPJ////4Q==</secDNS:pubKey>");
            result.append("</secDNS:keyData>");
        }

        result.append(             "</secDNS:infData>");
        result.append(        "</extension>");
    }

    private static void buildXmlResponseBeforeExtension(final String domainName,
            final StringBuilder result) {
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append(    "<response>");
        result.append(        "<result code=\"1000\">");
        result.append(            "<msg>Command completed successfully</msg>");
        result.append(        "</result>");
        result.append(        "<resData>");
        result.append(            "<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append(                "<name>" + domainName + "</name>");
        result.append(                "<roid>D0000003-AR</roid>");
        result.append(                "<status s=\"ok\" lang=\"en\"/>");
        result.append(                "<registrant>EXAMPLE</registrant>");
        result.append(                "<contact type=\"tech\">EXAMPLE</contact>");
        result.append(                "<ns>");
        result.append(                    "<hostObj>ns1.example.com.au</hostObj>");
        result.append(                    "<hostObj>ns2.example.com.au</hostObj>");
        result.append(                "</ns>");
        result.append(                "<host>ns1.example.com.au</host>");
        result.append(                "<host>ns2.exmaple.com.au</host>");
        result.append(                "<clID>Registrar</clID>");
        result.append(                "<crID>Registrar</crID>");
        result.append(                "<crDate>2006-02-09T15:44:58.0Z</crDate>");
        result.append(                "<upDate>2006-02-09T15:44:58.0Z</upDate>");
        result.append(                "<exDate>2008-02-10T00:00:00.0Z</exDate>");
        result.append(                "<authInfo>");
        result.append(                     "<pw>0192pqow</pw>");
        result.append(                "</authInfo>");
        result.append(            "</infData>");
        result.append(        "</resData>");
    }
}
