package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.DSOrKeyType;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainCreateCommandExtension;

/**
 * Test the only published feature of the AuDomainCreateCommand class, which
 * is to build a valid EPP create domain XML command given specified parameters
 * for the command.
 *
 * @author anthony (anthony@ausregistry.com.au)
 */
public class AuDomainCreateCommandTest {
    private static String registrantName = "AusRegistry";
    private static String registrantID = "01241326211";
    private static String registrantIDType = "ACN";
    private static String eligibilityType = "Company";
    private static int policyReason = 1;
    private static String eligibilityName = "Blah";
    private static String eligibilityID = "1231239523";
    private static String eligibilityIDType = "OTHER";

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    /**
     * Test that the XML string generated for a minimal create domain command
     * matches the expected XML for an EPP create domain command with those
     * parameters.
     */
    @Test
    public void testAuDomainCreateCommandMinimalConstructor() {
        Command cmd = new AuDomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, eligibilityType, policyReason,
                registrantName);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create>"
                    + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><registrant>JTKCON</registrant>"
                    + "<contact type=\"tech\">JTKCON2</contact>"
                    + "<authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                    + "<extension><create xmlns=\"urn:X-au:params:xml:ns:auext-1.2\""
                    + " xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\">"
                    + "<auProperties><registrantName>AusRegistry</registrantName>"
                    + "<eligibilityType>Company</eligibilityType>"
                    + "<policyReason>1</policyReason></auProperties></create></extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    /**
     * Test that the XML string generated for a sample create domain command
     * specified with all available parameters matches the expected XML for
     * an EPP create domain command with those parameters.
     *
     */
    @Test
    public void testAuDomainCreateCommandFullConstructor() {
        Command cmd = new AuDomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, new String[] {"JTKCON", "JTKCON2"},
                null, new String[] {"ns1.ausregistry.net", "ns2.ausregistry.net"},
                eligibilityType, policyReason,
                registrantName, registrantID,
                registrantIDType, eligibilityName,
                eligibilityID, eligibilityIDType);
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><ns><hostObj>ns1.ausregistry.net</hostObj>"
                    + "<hostObj>ns2.ausregistry.net</hostObj></ns><registrant>JTKCON</registrant>"
                    + "<contact type=\"admin\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact>"
                    + "<contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo>"
                    + "</create></create><extension><create xmlns=\"urn:X-au:params:xml:ns:auext-1.2\" "
                    + "xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\"><auProperties>"
                    + "<registrantName>AusRegistry</registrantName>"
                    + "<registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType>"
                    + "<eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID>"
                    + "<policyReason>1</policyReason></auProperties></create></extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testAuDomainCreateRegistrantCommandWithSecDNS() throws Exception {
        Command cmd = new AuDomainCreateCommand("jtkutest.com.au", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, new String[] {"JTKCON", "JTKCON2"},
                null, new String[] {"ns1.ausregistry.net", "ns2.ausregistry.net"},
                eligibilityType, policyReason,
                registrantName, registrantID,
                registrantIDType, eligibilityName,
                eligibilityID, eligibilityIDType);

        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");
        DSOrKeyType createData = new DSOrKeyType();
        createData.addToDsData(dsData);
        ext.setCreateData(createData);
        cmd.appendExtension(ext);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><ns><hostObj>ns1.ausregistry.net</hostObj>"
                + "<hostObj>ns2.ausregistry.net</hostObj></ns><registrant>JTKCON</registrant>"
                + "<contact type=\"admin\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact>"
                + "<contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create>"
                + "</create><extension><create xmlns=\"urn:X-au:params:xml:ns:auext-1.2\" "
                + "xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\"><auProperties>"
                + "<registrantName>AusRegistry</registrantName>"
                + "<registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType>"
                + "<eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID>"
                + "<policyReason>1</policyReason></auProperties></create>"
                + "<create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><dsData><keyTag>12345</keyTag><alg>3</alg>"
                + "<digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></create></extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
        assertEquals(expectedXml, cmd.toXML());
    }

}

