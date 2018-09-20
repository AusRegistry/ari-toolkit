package com.ausregistry.jtoolkit2.se;

import static com.ausregistry.jtoolkit2.se.DomainInfoResponseTest.DomainInfoResponseBuilder.infoResponseBuilder;
import static com.ausregistry.jtoolkit2.test.infrastructure.HostMatcher.matchHost;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

public class DomainInfoResponseTest {

    private static final XMLParser PARSER = new XMLParser();

    @Test
    public void testFromXML() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
    }

    @Test
    public void testGetPW() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        assertEquals("0192pqow", response.getPW());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        GregorianCalendar exp = EPPDateFormatter.fromXSDateTime("2006-02-09T15:44:58.0Z");
        GregorianCalendar act = response.getCreateDate();
        assertNotNull(act);
        assertEquals(exp, act);
    }

    @Test
    public void testGetExpireDate() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        GregorianCalendar exp = EPPDateFormatter.fromXSDateTime("2008-02-10T00:00:00.0Z");
        GregorianCalendar act = response.getExpireDate();
        assertEquals(exp, act);
    }

    @Test
    public void testGetRegistrantID() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        String exp = "EXAMPLE";
        String act = response.getRegistrantID();
        assertEquals(exp, act);
    }

    @Test
    public void testGetTechContacts() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        String[] act = response.getTechContacts();
        String[] exp = new String[] { "EXAMPLE" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetNameservers() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        String[] act = response.getNameservers();
        String[] exp = new String[] { "ns1.example.com.au", "ns2.example.com.au" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetSubordinateHosts() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        String[] act = response.getSubordinateHosts();
        String[] exp = new String[] { "ns1.example.com.au", "ns2.exmaple.com.au" };
        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetStatuses() throws Exception {
        DomainInfoResponse response = new DomainInfoResponse();
        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").build());
        response.fromXML(doc);
        Status[] act = response.getStatuses();
        Status[] exp = new Status[] { new Status("ok", null, "lang") };
        assertEquals(exp[0].toString(), act[0].toString());
    }

    @Test
    public void testGetIdnName() throws Exception {
        final String userForm = "\u0257\u018c\u0661.com";
        final String dnsForm = "xn--xha91b83h.com";
        final String canonicalForm = "\u0257\u018c1.com";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainIdnaResponseExtension re =
                new DomainIdnaResponseExtension(ResponseExtension.INFO);

        final XMLDocument doc =
                PARSER.parse(infoResponseBuilder(dnsForm).withIdn(userForm, canonicalForm).build());
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals(dnsForm, response.getName());
        assertTrue("IDN extension should have been initialised", re.isInitialised());
        assertEquals(userForm, re.getUserFormName());
        assertEquals(canonicalForm, re.getCanonicalForm());
        assertEquals("test", re.getLanguage());
    }

    @Test
    public void testGetNoIdn() throws Exception {
        final String domainName = "xn--xha91b83h.com";
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainIdnaResponseExtension re =
                new DomainIdnaResponseExtension(ResponseExtension.INFO);

        final XMLDocument doc =
                PARSER.parse(infoResponseBuilder(domainName).build());
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
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainVariantResponseExtension variantsExtension =
                new DomainVariantResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc =
                PARSER.parse(infoResponseBuilder(dnsForm).withVariant(variantUserForm, variantDnsForm).build());

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
        final XMLDocument doc = PARSER.parse(infoResponseBuilder(domainName).build());

        response.registerExtension(variantsExtension);
        response.fromXML(doc);
        assertEquals(domainName, response.getName());
        assertFalse("Variant extension should not have been initialised",
                variantsExtension.isInitialised());
    }

    @Test
    public void testLdhOnlyGetName() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainIdnaResponseExtension re =
                new DomainIdnaResponseExtension(ResponseExtension.INFO);
        final XMLDocument doc =
                PARSER.parse(infoResponseBuilder("example.com").withIdn("example.com", null).build());
        response.registerExtension(re);
        response.fromXML(doc);
        assertEquals("example.com", response.getName());
        assertTrue("IDN extension should have been initialised", re.isInitialised());
        assertEquals("example.com", re.getUserFormName());
    }

    @Test
    public void testGetNameserversHostAttribute() throws Exception {
        // Preparing host[] to compare with parsed response host array
        InetAddress[] inetAddresses = new InetAddress[2];
        inetAddresses[0] = new InetAddress("132.16.56.29");
        inetAddresses[1] = new InetAddress(IPVersion.IPv6, "FE80:CD00:0:CDE:1257:0:211E:729C");
        Host host1 = new Host("ns1.example.com.au", inetAddresses);
        Host host2 = new Host("ns2.example.com.au");

        XMLDocument doc = PARSER.parse(infoResponseBuilder("example.com.au").withHosts(host1, host2).build());

        DomainInfoResponse response = new DomainInfoResponse();
        response.fromXML(doc);

        assertNull("hostObj is not response so it should be null", response.getNameservers());
        Host[] hosts = response.getNameserverHosts();
        assertNotNull(hosts);
        assertThat(hosts, arrayContaining(matchHost(host1), matchHost(host2)));
    }

    static final class DomainInfoResponseBuilder {

        private final String domainName;
        private boolean isIdn = false;
        private String userForm;
        private String canonicalForm;
        private String variantUserForm;
        private String variantDnsForm;
        private Host[] nameservers;

        private DomainInfoResponseBuilder(String domainName) {
            this.domainName = domainName;
        }

        static DomainInfoResponseBuilder infoResponseBuilder(String domainName) {
            return new DomainInfoResponseBuilder(domainName);
        }

        private DomainInfoResponseBuilder withIdn(String userForm, String canonicalName) {
            this.userForm = userForm;
            this.canonicalForm = canonicalName;
            this.isIdn = true;
            return this;
        }

        private DomainInfoResponseBuilder withVariant(String variantUserForm, String variantDnsForm) {
            this.variantUserForm = variantUserForm;
            this.variantDnsForm = variantDnsForm;
            return this;
        }

        private DomainInfoResponseBuilder withHosts(Host... nameservers) {
            this.nameservers = nameservers;
            return this;
        }

        private String build() {
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
            if (nameservers != null && nameservers.length > 0) {
                for (Host nameserver : nameservers) {
                    result.append("<hostAttr><hostName>").append(nameserver.getName()).append("</hostName>");
                    for (InetAddress address : nameserver.getAddresses()) {
                        result.append("<hostAddr ip=\"").append(address.getVersion()).append("\">")
                                .append(address.getTextRep()).append("</hostAddr>");

                    }
                    result.append("</hostAttr>");
                }
            } else {
                result.append("<hostObj>ns1.example.com.au</hostObj>");
                result.append("<hostObj>ns2.example.com.au</hostObj>");
            }
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
            result.append("<extension>");
            result.append("<auext:infData xmlns:auext=\"urn:X-au:params:xml:ns:auext-1.2\"");
            result.append(" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2  auext-1.2.xsd\">");
            result.append("<auext:auProperties>");
            result.append("<auext:registrantName>RegistrantName Pty. Ltd.</auext:registrantName>");
            result.append("<auext:registrantID type=\"ACN\">123456789</auext:registrantID>");
            result.append("<auext:eligibilityType>Other</auext:eligibilityType>");
            result.append("<auext:eligibilityName>Registrant Eligi</auext:eligibilityName>");
            result.append("<auext:eligibilityID type=\"ABN\">987654321</auext:eligibilityID>");
            result.append("<auext:policyReason>2</auext:policyReason>");
            result.append("</auext:auProperties>");
            result.append("</auext:infData>");

            if (isIdn || variantDnsForm != null) {
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

            }

            result.append("</extension>");
            result.append("<trID>");
            result.append("<clTRID>ABC-12345</clTRID>");
            result.append("<svTRID>54321-XYZ</svTRID>");
            result.append("</trID>");
            result.append("</response>");
            result.append("</epp>");
            return result.toString();
        }
    }

}
