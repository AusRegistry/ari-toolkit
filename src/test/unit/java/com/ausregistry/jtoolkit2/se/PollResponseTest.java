package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.app.DomainInfoApplicationResponseExtension;
import com.ausregistry.jtoolkit2.se.generic.DomainInfoKVResponseExtension;
import com.ausregistry.jtoolkit2.se.idn.DomainInfoIdnResponseExtension;
import com.ausregistry.jtoolkit2.se.rgp.DomainInfoRgpResponseExtension;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainInfoResponseExtension;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class PollResponseTest {
    private static final String XML_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg>Transfer requested.</msg></msgQ><resData><domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:trStatus>pending</domain:trStatus><domain:reID>ClientX</domain:reID><domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate><domain:acID>ClientY</domain:acID><domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate><domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate></domain:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String XML_2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg lang=\"en\">Transfer requested.</msg></msgQ><resData><contact:trnData xmlns:contact=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><contact:id>JTKUTEST</contact:id><contact:trStatus>pending</contact:trStatus><contact:reID>ClientX</contact:reID><contact:reDate>2000-06-08T22:00:00.0Z</contact:reDate><contact:acID>ClientY</contact:acID><contact:acDate>2000-06-13T22:00:00.0Z</contact:acDate></contact:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String XML_3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg lang=\"en\">Host updated by TRO.</msg></msgQ><resData><host:infData xmlns:host=\"urn:ietf:params:xml:ns:host-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\"><host:name>ns1.example.com</host:name><host:roid>NS1_EXAMPLE1-REP</host:roid><host:status s=\"linked\"/><host:status s=\"clientUpdateProhibited\"/><host:addr ip=\"v4\">192.0.2.2</host:addr><host:addr ip=\"v4\">192.0.2.29</host:addr><host:addr ip=\"v6\">1080:0:0:0:8:800:200C:417A</host:addr><host:clID>ClientY</host:clID><host:crID>ClientX</host:crID><host:crDate>1999-04-03T22:00:00.0Z</host:crDate><host:upID>ClientX</host:upID><host:upDate>1999-12-03T09:00:00.0Z</host:upDate><host:trDate>2000-04-08T09:00:00.0Z</host:trDate></host:infData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String XML_4 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"><response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"><domain:name>xn--hgbc.idnzone</domain:name><domain:roid>D2269C59040734D258CA62095E7AF925B-ARI</domain:roid><domain:status s=\"inactive\"/><domain:registrant>DOMAINCONTACT1</domain:registrant><domain:contact type=\"tech\">DOMAINCONTACT1</domain:contact><domain:clID>eppIdOne</domain:clID><domain:crID>eppIdOne</domain:crID><domain:crDate>2013-05-09T03:38:16Z</domain:crDate><domain:upID>eppIdOne</domain:upID><domain:upDate>2013-05-09T03:38:17Z</domain:upDate><domain:exDate>2014-05-09T03:38:16Z</domain:exDate><domain:authInfo><domain:pw>P@55w0rD</domain:pw></domain:authInfo></domain:infData></resData><extension><infData xmlns=\"urn:ar:params:xml:ns:idn-1.0\"><languageTag>ar</languageTag></infData><rgp:infData xmlns:rgp=\"urn:ietf:params:xml:ns:rgp-1.0\"><rgp:rgpStatus s=\"addPeriod\">2013-05-14T03:38:16Z</rgp:rgpStatus></rgp:infData><infData xmlns=\"urn:ar:params:xml:ns:variant-1.1\"><variant>xn--igbe.idnzone</variant></infData><secDNS:infData xmlns:secDNS=\"urn:ietf:params:xml:ns:secDNS-1.1\"><secDNS:keyData><secDNS:flags>256</secDNS:flags><secDNS:protocol>3</secDNS:protocol><secDNS:alg>8</secDNS:alg><secDNS:pubKey>AwEAAc3d4rj+vs3ZSuaqokDCcs2Yh63JWRYshK4YlQtxGaxvMJfv7ubJLv18eVjGgyBlHVDL/JeXV5QtJ282cPFt2Zfg4wqNmiMgBtfbIHAtQ/ANVYKUwFwNAaBUJoBTtqNJZlgjUKQCYnspDeyppSVK0X/N1y5Lx8oV0FgOxQsyVqlk9q65j449pG8AL4nCRRKdZoEadpO5Dd1dFVDGY+KIOoky6c7XW9zbyXVXHpQetBAOLly12pUi8croXjQbqSmajrDYpT0INYtxtO8kaWW7zDdOWySPOynW0hU/9AyYyK7y0u5R8ups+bgSxjpWW0BgGWmlSiHLuQTNS3yOm3Kt8EjUImLnyt1wBOgCkrY4mti45bTS9IFy4Pqc/DwBHuuuNeX5At4ZD9bLiSpcjexvBIOy9bDg/IavMKuo7m3SxeWU5eg6/t6qV/uxGJWbdNJC6mI80JQIf5mUxajbU5WHX3m44r/p9HyhgypmZLWJlH7GKgNw+wpJkkqsAFZXXF0C+65PqpVepBtDpckBWVhZUjUySOBbi1/ALaHIPWKklwkfbeZ46DNql/XePyCMJSHHq5+PeSW2MY0SuSlEx1rxON2LJa7QvPxCeof/AP+SuZxY+Umgf3uddHLtGQyrLtB3wTDHiRn3layZwE9pFfLdQUBz4aH1jGl69n6KgwHFYCer</secDNS:pubKey></secDNS:keyData><secDNS:keyData><secDNS:flags>258</secDNS:flags><secDNS:protocol>3</secDNS:protocol><secDNS:alg>8</secDNS:alg><secDNS:pubKey>AQPJ////4Q==</secDNS:pubKey></secDNS:keyData></secDNS:infData><infData xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\"><kvlist name=\"abc\"><item key=\"keyName1\">updatedKeyValue1</item><item key=\"keyName2\">keyValue2</item></kvlist><kvlist name=\"def\"><item key=\"keyName1\">keyValue1</item><item key=\"keyName2\">keyValue2</item></kvlist></infData></extension><trID><clTRID>.20130509.133817.3</clTRID><svTRID>serverTxnId-1368070697723</svTRID></trID></response></epp>";
    private static final String XML_5 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>domain.zone</name><roid>D0000003-AR</roid><status s=\"ok\" lang=\"en\"/><registrant>EXAMPLE</registrant><contact type=\"tech\">EXAMPLE</contact><ns><hostObj>ns1.example.com.au</hostObj><hostObj>ns2.example.com.au</hostObj></ns><host>ns1.example.com.au</host><host>ns2.exmaple.com.au</host><clID>Registrar</clID><crID>Registrar</crID><crDate>2008-02-10T00:00:00.0Z</crDate><upDate>2008-02-10T00:00:00.0Z</upDate><exDate>2008-02-10T00:00:00.0Z</exDate><authInfo><pw>0192pqow</pw></authInfo></infData></resData><extension><app:infData xmlns:app=\"urn:ar:params:xml:ns:application-1.0\" xsi:schemaLocation=\"urn:ar:params:xml:ns:application-1.0 application-1.0.xsd\"><app:id>applicationId</app:id><app:phase>phase</app:phase><app:status s=\"applied\" /></app:infData><tmch:infData xmlns:tmch=\"urn:ar:params:xml:ns:tmch-1.0\"><tmch:smd>ZW5jb2RlZFNpZ25lZE1hcmtEYXRh</tmch:smd></tmch:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";
    private static final String XML_6 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1301\"><msg>Command completed successfully; ack to dequeue</msg></result><msgQ count=\"5\" id=\"12345\"><qDate>2000-06-08T22:00:00.0Z</qDate><msg>Transfer requested.</msg></msgQ><resData>\n <domain:trnData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com</domain:name><domain:trStatus>pending</domain:trStatus><domain:reID>ClientX</domain:reID><domain:reDate>2000-06-08T22:00:00.0Z</domain:reDate><domain:acID>ClientY</domain:acID><domain:acDate>2000-06-13T22:00:00.0Z</domain:acDate><domain:exDate>2002-09-08T22:00:00.0Z</domain:exDate></domain:trnData></resData><trID><clTRID>ABC-12345</clTRID><svTRID>54321-XYZ</svTRID></trID></response></epp>";

    private PollResponse xmlPollResponse(String xml) {
        final XMLParser parser = new XMLParser();
        PollResponse pollResponse = new PollResponse();
        XMLDocument doc;
        try {
            doc = parser.parse(xml);
            pollResponse.fromXML(doc);
            return pollResponse;
        } catch (ParsingException e) {
            fail("Cannot parse given XML to a PollResponse!");
            throw new IllegalStateException();
        }
    }

    @Test
    public void testGetContactTransferResponse() {
        final ContactTransferResponse ctr = xmlPollResponse(XML_2).getContactTransferResponse();
        assertNotNull(ctr);
        assertEquals("JTKUTEST", ctr.getID());
        assertEquals("pending", ctr.getTransferStatus());
        assertEquals("ClientX", ctr.getRequestingClID());
        assertEquals("ClientY", ctr.getActioningClID());
    }

    @Test
    public void testGetDomainTransferResponse() {
        final DomainTransferResponse dtr = xmlPollResponse(XML_1).getDomainTransferResponse();
        assertNotNull(dtr);
        assertEquals("example.com", dtr.getName());
        assertEquals("pending", dtr.getTransferStatus());
        assertEquals("ClientX", dtr.getRequestingClID());
        assertEquals("ClientY", dtr.getActioningClID());
    }

    @Test
    public void shouldGetIdnExtensionFromInfoResponse() {
        final DomainInfoIdnResponseExtension domainInfoIdnResponseExtension = xmlPollResponse(XML_4)
                .getIdnDomainInfoResponseExtension();
        assertThat(domainInfoIdnResponseExtension.getLanguageTag(), is("ar"));
        assertThat(domainInfoIdnResponseExtension.isInitialised(), is(true));

    }

    @Test
    public void shouldGetSecDnsExtensionFromInfoResponse() {
        final SecDnsDomainInfoResponseExtension secDnsDomainInfoResponseExtension = xmlPollResponse(XML_4)
                .getSecDnsDomainInfoResponseExtension();
        assertThat(secDnsDomainInfoResponseExtension.getInfData().getKeyDataList().size(), is(2));
        assertThat(secDnsDomainInfoResponseExtension.getInfData().getKeyDataList().get(0).getAlg(), is(8));
    }

    @Test
    public void shouldGetVariantResponseExtension() {
        final DomainVariantResponseExtensionV1_1 variantResponseExtension = xmlPollResponse(XML_4)
                .getVariantDomainInfoResponseExtensionV1_1();
        assertThat(variantResponseExtension.getVariants().size(), is(1));
        assertThat(variantResponseExtension.getVariants().get(0).getName(), is("xn--igbe.idnzone"));
    }


    @Test
    public void shouldGetRgpResponseExtension() {
        final DomainInfoRgpResponseExtension rgpDomainInfoResponseExtension = xmlPollResponse(XML_4)
                .getRgpDomainInfoResponseExtension();

        assertThat(rgpDomainInfoResponseExtension.getRgpStatuses().size(), is(1));
        assertThat(rgpDomainInfoResponseExtension.getRgpStatuses().get(0).getStatus(), is("addPeriod"));
        assertThat(rgpDomainInfoResponseExtension.getRgpStatuses().get(0).getMessage(), is("2013-05-14T03:38:16Z"));
    }

    @Test
    public void shouldGetKvResponseExtension() {
        final DomainInfoKVResponseExtension kvDomainInfoResponseExtension = xmlPollResponse(XML_4)
                .getKvDomainInfoResponseExtension();
        assertThat(kvDomainInfoResponseExtension.getItem("abc", "keyName1"), is("updatedKeyValue1"));
    }

    @Test
    public void shouldGetDomainInfoApplicationResponseExtension() {
        final DomainInfoApplicationResponseExtension domainInfoApplicationResponseExtension = xmlPollResponse(XML_5)
                .getDomainInfoApplicationResponseExtension();
        assertThat(domainInfoApplicationResponseExtension.getApplicationId(), is("applicationId"));
        assertThat(domainInfoApplicationResponseExtension.getPhase(), is("phase"));
        assertThat(domainInfoApplicationResponseExtension.getStatuses().get(0), is("applied"));
    }

    @Test
    public void testResDataFollowedByWhitespace() {
        final DomainTransferResponse dtr = xmlPollResponse(XML_6).getDomainTransferResponse();
        assertNotNull(dtr);
        assertEquals("example.com", dtr.getName());
    }

    @Test
    public void testGetResults() {
        final Result[] results1 = xmlPollResponse(XML_1).getResults();
        final Result[] results2 = xmlPollResponse(XML_2).getResults();
        final Result[] results3 = xmlPollResponse(XML_3).getResults();
        assertEquals(1, results1.length);
        assertEquals(1, results2.length);
        assertEquals(1, results3.length);
        assertEquals(1301, results1[0].getResultCode());
        assertEquals(1301, results2[0].getResultCode());
        assertEquals(1301, results3[0].getResultCode());
        assertEquals("Command completed successfully; ack to dequeue", results1[0].getResultMessage());
        assertEquals("Command completed successfully; ack to dequeue", results2[0].getResultMessage());
        assertEquals("Command completed successfully; ack to dequeue", results3[0].getResultMessage());
    }

    @Test
    public void testGetCLTRID() {
        assertEquals("ABC-12345", xmlPollResponse(XML_1).getCLTRID());
        assertEquals("ABC-12345", xmlPollResponse(XML_2).getCLTRID());
        assertEquals("ABC-12345", xmlPollResponse(XML_3).getCLTRID());
    }

    @Test
    public void testGetMessageEnqueueDate() {
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), xmlPollResponse(XML_1).getMessageEnqueueDate());
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), xmlPollResponse(XML_2).getMessageEnqueueDate());
        assertEquals(EPPDateFormatter.fromXSDateTime("2000-06-08T22:00:00.0Z"), xmlPollResponse(XML_3).getMessageEnqueueDate());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Transfer requested.", xmlPollResponse(XML_1).getMessage());
        assertEquals("Transfer requested.", xmlPollResponse(XML_2).getMessage());
        assertEquals("Host updated by TRO.", xmlPollResponse(XML_3).getMessage());
    }

    @Test
    public void testGetMessageLanguage() {
        assertEquals("en", xmlPollResponse(XML_2).getMessageLanguage());
    }

    @Test
    public void testGetMsgCount() {
        assertEquals("Require 5 messages", 5, xmlPollResponse(XML_1).getMsgCount());
        assertEquals("Require 5 messages", 5, xmlPollResponse(XML_2).getMsgCount());
        assertEquals("Require 5 messages", 5, xmlPollResponse(XML_3).getMsgCount());
    }

    @Test
    public void testGetMsgID() {
        assertEquals("12345", xmlPollResponse(XML_1).getMsgID());
        assertEquals("12345", xmlPollResponse(XML_2).getMsgID());
        assertEquals("12345", xmlPollResponse(XML_3).getMsgID());
    }

    @Test
    public void testGetHostUpdateResponse() {
        final HostInfoResponse hostInfoResponse = xmlPollResponse(XML_3).getHostInfoResponse();
        assertNotNull(hostInfoResponse);
        assertEquals("ns1.example.com", hostInfoResponse.getName());
        assertEquals("NS1_EXAMPLE1-REP", hostInfoResponse.getROID());
        assertEquals("linked", hostInfoResponse.getStatuses()[0].toString());
        assertEquals("clientUpdateProhibited", hostInfoResponse.getStatuses()[1].toString());
        assertEquals("v4", hostInfoResponse.getAddresses()[0].getVersion());
        assertEquals("192.0.2.2", hostInfoResponse.getAddresses()[0].getTextRep());
        assertEquals("v4", hostInfoResponse.getAddresses()[1].getVersion());
        assertEquals("192.0.2.29", hostInfoResponse.getAddresses()[1].getTextRep());
        assertEquals("v6", hostInfoResponse.getAddresses()[2].getVersion());
        assertEquals("1080:0:0:0:8:800:200C:417A", hostInfoResponse.getAddresses()[2].getTextRep());
        assertEquals("ClientY", hostInfoResponse.getSponsorClient());
        assertEquals("ClientX", hostInfoResponse.getCreateClient());
        assertEquals("ClientX", hostInfoResponse.getUpdateClient());
    }
}
