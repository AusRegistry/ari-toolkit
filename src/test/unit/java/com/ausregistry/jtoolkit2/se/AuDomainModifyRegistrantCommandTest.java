package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.DSOrKeyType;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainUpdateCommandExtension;

public class AuDomainModifyRegistrantCommandTest {
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

    @Test
    public void testAuDomainModifyRegistrantCommand() throws Exception {
        final Command cmd = new AuDomainModifyRegistrantCommand("jtkutest.com.au",
                eligibilityType, policyReason, registrantName, registrantID,
                registrantIDType, eligibilityName, eligibilityID, eligibilityIDType,
                "testing");

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-au:params:xml:ns:auext-1.2\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\"><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties><explanation>testing</explanation></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testAuDomainModifyRegistrantCommandWithSecDNS() throws Exception {
        final Command cmd = new AuDomainModifyRegistrantCommand("jtkutest.com.au",
                eligibilityType, policyReason, registrantName, registrantID,
                registrantIDType, eligibilityName, eligibilityID, eligibilityIDType,
                "testing");

        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-au:params:xml:ns:auext-1.2\" xsi:schemaLocation=\"urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd\"><auProperties><registrantName>AusRegistry</registrantName><registrantID type=\"ACN\">01241326211</registrantID><eligibilityType>Company</eligibilityType><eligibilityName>Blah</eligibilityName><eligibilityID type=\"OTHER\">1231239523</eligibilityID><policyReason>1</policyReason></auProperties><explanation>testing</explanation></update><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }


}
