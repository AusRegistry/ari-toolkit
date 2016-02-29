package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;

public class AeDomainTransferRegistrantCommandTest {
    private static String registrantName = "AusRegistry";
    private static String registrantID = "01241326211";
    private static String registrantIDType = "Trade License";
    private static String eligibilityType = "Trademark";
    private static int policyReason = 1;
    private static String eligibilityName = "Blah";
    private static String eligibilityID = "1231239523";
    private static String eligibilityIDType = "Trademark";
    private GregorianCalendar curExpDate = EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z");

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testAeDomainTransferRegistrantCommandStringStringString() {
        Command cmd = new AeDomainTransferRegistrantCommand("jtkutest.com.ae",
                curExpDate, registrantName, eligibilityType, 1, "testing");
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<extension><command xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\">"
                    + "<registrantTransfer><registrantTransfer xmlns=\"urn:X-ae:params:xml:ns:aedomain-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aedomain-1.0 aedomain-1.0.xsd\">"
                    + "<name>jtkutest.com.ae</name><curExpDate>2007-01-01</curExpDate><aeProperties>"
                    + "<registrantName>AusRegistry</registrantName><eligibilityType>Trademark</eligibilityType>"
                    + "<policyReason>1</policyReason></aeProperties><explanation>testing</explanation>"
                    + "</registrantTransfer></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command></extension></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testAeDomainTransferRegistrantCommandStringStringIntStringStringStringStringStringStringString() {
        Command cmd = new AeDomainTransferRegistrantCommand("jtkutest.com.ae",
                curExpDate, new Period(PeriodUnit.YEARS, 2), eligibilityType,
                policyReason, registrantName, registrantID, registrantIDType,
                eligibilityName, eligibilityID, eligibilityIDType, "testing");
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<extension><command xmlns=\"urn:X-ae:params:xml:ns:aeext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd\">"
                    + "<registrantTransfer><registrantTransfer xmlns=\"urn:X-ae:params:xml:ns:aedomain-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ae:params:xml:ns:aedomain-1.0 aedomain-1.0.xsd\">"
                    + "<name>jtkutest.com.ae</name><curExpDate>2007-01-01</curExpDate><period unit=\"y\">2</period>"
                    + "<aeProperties><registrantName>AusRegistry</registrantName>"
                    + "<registrantID type=\"Trade License\">01241326211</registrantID>"
                    + "<eligibilityType>Trademark</eligibilityType><eligibilityName>Blah</eligibilityName>"
                    + "<eligibilityID type=\"Trademark\">1231239523</eligibilityID><policyReason>1</policyReason>"
                    + "</aeProperties><explanation>testing</explanation></registrantTransfer></registrantTransfer>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}
