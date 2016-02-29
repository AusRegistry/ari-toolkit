package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class EnumDomainInfoResponseTest {
    private static final String XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>3.8.0.0.6.9.2.3.6.1.4.4.e164.arpa</domain:name><domain:roid>EXAMPLE1-REP</domain:roid><domain:status s=\"ok\"/><domain:registrant>jd1234</domain:registrant><domain:contact type=\"admin\">sh8013</domain:contact><domain:contact type=\"tech\">sh8013</domain:contact><domain:ns><domain:hostObj>ns1.example.com</domain:hostObj><domain:hostObj>ns2.example.com</domain:hostObj></domain:ns><domain:host>ns1.example.com</domain:host><domain:host>ns2.example.com</domain:host><domain:clID>ClientX</domain:clID><domain:crID>ClientY</domain:crID><domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate><domain:upID>ClientX</domain:upID><domain:upDate>1999-12-03T09:00:00.0Z</domain:upDate><domain:exDate>2005-04-03T22:00:00.0Z</domain:exDate><domain:trDate>2000-04-08T09:00:00.0Z</domain:trDate><domain:authInfo><domain:pw>2fooBAR</domain:pw></domain:authInfo></domain:infData></resData><extension><e164:infData xmlns:e164=\"urn:ietf:params:xml:ns:e164epp-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:e164epp-1.0 e164epp-1.0.xsd\"><e164:naptr><e164:order>10</e164:order><e164:pref>100</e164:pref><e164:flags>u</e164:flags><e164:svc>E2U+sip</e164:svc><e164:regex>\"!^.*$!sip:info@example.com!\"</e164:regex></e164:naptr><e164:naptr><e164:order>10</e164:order><e164:pref>102</e164:pref><e164:flags>u</e164:flags><e164:svc>E2U+msg</e164:svc><e164:regex>\"!^.*$!mailto:info@example.com!\"</e164:regex></e164:naptr></e164:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID></response></epp>";
    private EnumDomainInfoResponse response;

    @Before
    public void setUp() throws Exception {
        response = new EnumDomainInfoResponse();
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parse(XML);
        response.fromXML(doc);
    }

    @Test
    public void testGetNAPTRs() {
        NAPTR[] naptrs = response.getNAPTRs();
        assertEquals(2, naptrs.length);
        assertEquals(10, naptrs[0].getOrder());
        assertEquals(100, naptrs[0].getPreference());
        assertEquals("u", naptrs[0].getFlags());
        assertEquals("E2U+sip", naptrs[0].getService());
        assertEquals("\"!^.*$!sip:info@example.com!\"", naptrs[0].getRegex());
        assertEquals(null, naptrs[0].getReplacement());
        assertEquals(10, naptrs[1].getOrder());
        assertEquals(102, naptrs[1].getPreference());
        assertEquals("u", naptrs[1].getFlags());
        assertEquals("E2U+msg", naptrs[1].getService());
        assertEquals("\"!^.*$!mailto:info@example.com!\"", naptrs[1].getRegex());
        assertEquals(null, naptrs[1].getReplacement());
    }

    @Test
    public void testGetStatuses() {
        assertArrayEquals(
                new Status[] {
                        new Status("ok")
                },
                response.getStatuses());
    }

    @Test
    public void testGetName() {
        assertEquals("3.8.0.0.6.9.2.3.6.1.4.4.e164.arpa", response.getName());
    }

    @Test
    public void testGetPW() {
        assertEquals("2fooBAR", response.getPW());
    }

    @Test
    public void testGetExpireDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("2005-04-03T22:00:00.0Z"),
                response.getExpireDate());
    }

    @Test
    public void testGetRegistrantID() {
        assertEquals("jd1234", response.getRegistrantID());
    }

    @Test
    public void testGetTechContacts() {
        String[] contacts = response.getTechContacts();
        assertEquals(1, contacts.length);
        assertEquals("sh8013", contacts[0]);
    }

    @Test
    public void testGetAdminContacts() {
        String[] contacts = response.getAdminContacts();
        assertEquals(1, contacts.length);
        assertEquals("sh8013", contacts[0]);
    }

    @Test
    public void testGetBillingContacts() {
        String[] contacts = response.getBillingContacts();
        assertNull(contacts);
    }

    @Test
    public void testGetNameservers() {
        String[] ns = response.getNameservers();
        assertEquals(2, ns.length);
        assertEquals("ns1.example.com", ns[0]);
        assertEquals("ns2.example.com", ns[1]);
    }

    @Test
    public void testGetSubordinateHosts() {
        String[] hosts = response.getSubordinateHosts();
        assertEquals(2, hosts.length);
        assertEquals("ns1.example.com", hosts[0]);
        assertEquals("ns2.example.com", hosts[1]);
    }

    @Test
    public void testGetROID() {
        assertEquals("EXAMPLE1-REP", response.getROID());
    }

    @Test
    public void testGetCreateDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("1999-04-03T22:00:00.0Z"),
                response.getCreateDate());
    }

    @Test
    public void testGetUpdateDate() {
        assertEquals(
                EPPDateFormatter.fromXSDateTime("1999-12-03T09:00:00.0Z"),
                response.getUpdateDate());
    }

    @Test
    public void testGetCreateClient() {
        assertEquals("ClientY", response.getCreateClient());
    }

    @Test
    public void testGetUpdateClient() {
        assertEquals("ClientX", response.getUpdateClient());
    }

    @Test
    public void testGetSponsorClient() {
        assertEquals("ClientX", response.getSponsorClient());
    }
}
