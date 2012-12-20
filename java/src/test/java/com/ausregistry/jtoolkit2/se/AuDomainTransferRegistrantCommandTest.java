package com.ausregistry.jtoolkit2.se;

import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AuDomainTransferRegistrantCommandTest {
    private static String registrantName = "AusRegistry";
    private static String registrantID = "01241326211";
    private static String registrantIDType = "ACN";
    private static String eligibilityType = "Company";
    private static int policyReason = 1;
    private static String eligibilityName = "Blah";
    private static String eligibilityID = "1231239523";
    private static String eligibilityIDType = "OTHER";
    private GregorianCalendar curExpDate = EPPDateFormatter.fromXSDateTime("2007-01-01T01:01:01.0Z");

    @Before
    public void setUp() throws Exception {
		Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testAuDomainTransferRegistrantCommandStringStringString() {
        Command cmd = new AuDomainTransferRegistrantCommand("jtkutest.com.au",
				curExpDate, registrantName, "Other", 1, "testing");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><registrantTransfer><registrantTransfer xmlns=\"urn:X-au:params:xml:ns:audomain-1.0\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd\"><name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate><auProperties><registrantName>AusRegistry</registrantName><eligibilityType>Other</eligibilityType><policyReason>1</policyReason></auProperties><explanation>testing</explanation></registrantTransfer></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testAuDomainTransferRegistrantCommandStringStringIntStringStringStringStringStringStringString() {
        Command cmd = new AuDomainTransferRegistrantCommand("jtkutest.com.au",
				curExpDate, new Period(PeriodUnit.YEARS, 2), eligibilityType, policyReason, registrantName,
				registrantID, registrantIDType, eligibilityName, eligibilityID,
				eligibilityIDType, "testing");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-au:params:xml:ns:auext-1.1\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd\"><registrantTransfer><registrantTransfer xmlns=\"urn:X-au:params:xml:ns:audomain-1.0\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd\"><name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate><period unit=\"y\">2</period><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties><explanation>testing</explanation></registrantTransfer></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

