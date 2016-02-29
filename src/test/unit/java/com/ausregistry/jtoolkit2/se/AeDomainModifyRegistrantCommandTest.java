package com.ausregistry.jtoolkit2.se;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import com.ausregistry.jtoolkit2.Timer;
public class AeDomainModifyRegistrantCommandTest {
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
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testAeDomainModifyRegistrantCommandStringStringIntStringStringStringStringStringStringString() {
        Command cmd = new AeDomainModifyRegistrantCommand("jtkutest.com.ae",
                eligibilityType, policyReason, registrantName, registrantID,
                registrantIDType, eligibilityName, eligibilityID,
                eligibilityIDType, "testing");
        try {
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                    + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.ae</name></update></update><extension><update "
                    + "xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\"><aeProperties>"
                    + "<registrantName>AusRegistry</registrantName><registrantID type=\"Trade License\">"
                    + "01241326211</registrantID><eligibilityType>Trademark</eligibilityType>"
                    + "<eligibilityName>Blah</eligibilityName><eligibilityID type=\"Trademark\">1231239523"
                    + "</eligibilityID><policyReason>1</policyReason></aeProperties>"
                    + "<explanation>testing</explanation></update></extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}