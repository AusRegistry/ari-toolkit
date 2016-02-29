package com.ausregistry.jtoolkit2.se;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import com.ausregistry.jtoolkit2.Timer;
/**
 * Test the only published feature of the AeDomainCreateCommand class, which
 * is to build a valid EPP create domain XML command given specified parameters
 * for the command.
 *
 * @author anthony (anthony@ausregistry.com.au)
 */
public class AeDomainCreateCommandTest {
    private static String registrantName = "AusRegistry";
    private static String registrantID = "01241326211";
    private static String registrantIDType = "Trade License";
    private static String eligibilityType = "Trademark";
    private static int policyReason = 1;
    private static String eligibilityName = "Blah";
    private static String eligibilityID = "1231239523";
    private static String eligibilityIDType = "Trademark";
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
    public void testAeDomainCreateCommandMinimalConstructor() {
        Command cmd = new AeDomainCreateCommand("jtkutest.com.ae", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, eligibilityType, policyReason,
                registrantName);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.ae</name><registrant>JTKCON</registrant>"
                    + "<contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create>"
                    + "</create><extension><create xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\"><aeProperties>"
                    + "<registrantName>AusRegistry</registrantName><eligibilityType>Trademark</eligibilityType>"
                    + "<policyReason>1</policyReason></aeProperties></create></extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
    /**
     * Test that the XML string generated for a sample create domain command
     * specified with all available parameters matches the expected XML for
     * an EPP create domain command with those parameters.
     */
    @Test
    public void testAeDomainCreateCommandFullConstructor() {
        Command cmd = new AeDomainCreateCommand("jtkutest.com.ae", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, new String[] {"JTKCON", "JTKCON2"}, null,
                new String[] {"ns1.ausregistry.net", "ns2.ausregistry.net"}, new Period(5), eligibilityType,
                policyReason, registrantName, registrantID, registrantIDType, eligibilityName,
                eligibilityID, eligibilityIDType);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                    + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.ae</name><period unit=\"y\">5</period><ns>"
                    + "<hostObj>ns1.ausregistry.net</hostObj><hostObj>ns2.ausregistry.net</hostObj></ns>"
                    + "<registrant>JTKCON</registrant><contact type=\"admin\">JTKCON</contact>"
                    + "<contact type=\"admin\">JTKCON2</contact><contact type=\"tech\">JTKCON2</contact>"
                    + "<authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension>"
                    + "<create xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\">"
                    + "<aeProperties><registrantName>AusRegistry</registrantName>"
                    + "<registrantID type=\"Trade License\">01241326211</registrantID>"
                    + "<eligibilityType>Trademark</eligibilityType><eligibilityName>Blah</eligibilityName>"
                    + "<eligibilityID type=\"Trademark\">1231239523</eligibilityID><policyReason>1</policyReason>"
                    + "</aeProperties></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAeDomainCreateCommandNullEligibilityType() throws SAXException {
        Command cmd = new AeDomainCreateCommand("jtkutest.com.ae", "jtkUT3st",
                "JTKCON", new String[] {"JTKCON2"}, null, policyReason,
                registrantName);
        cmd.toXML();
    }
}