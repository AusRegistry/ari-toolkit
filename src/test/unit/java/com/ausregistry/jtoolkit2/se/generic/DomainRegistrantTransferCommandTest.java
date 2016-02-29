package com.ausregistry.jtoolkit2.se.generic;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.PeriodUnit;

public class DomainRegistrantTransferCommandTest {
    private static String registrantName = "AusRegistry";
    private static String registrantIDValue = "01241326211";
    private static String registrantIDType = "Trade License";
    private static String eligibilityType = "Trademark";
    private static int policyReason = 1;
    private static String eligibilityName = "Blah";
    private static String eligibilityIDValue = "1231239523";
    private static String eligibilityIDType = "Trademark";
    private static String kvListName = "au";
    private final GregorianCalendar curExpDate = EPPDateFormatter
            .fromXSDateTime("2007-01-01T01:01:01.0Z");

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testWithoutPeriod() throws Exception {
        DomainRegistrantTransferCommand command = new DomainRegistrantTransferCommand(
                "jtkutest.com.ae", curExpDate, kvListName, "testing");
        addSampleKVItems(command);

        String xml = command.toXML();
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:registrant-1.0\"><registrantTransfer><name>jtkutest.com.ae</name><curExpDate>" +
                EPPDateFormatter.toXSDate(curExpDate) +
                "</curExpDate><kvlist xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" name=\"au\"><item key=\"eligibilityIDType\">Trademark</item><item key=\"eligibilityIDValue\">1231239523</item><item key=\"eligibilityName\">Blah</item><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantIDType\">Trade License</item><item key=\"registrantIDValue\">01241326211</item><item key=\"registrantName\">AusRegistry</item></kvlist><explanation>testing</explanation></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>",
                xml);
    }

    @Test
    public void testWithPeriod() throws Exception {
        DomainRegistrantTransferCommand command = new DomainRegistrantTransferCommand(
                "jtkutest.com.ae", curExpDate, new Period(PeriodUnit.YEARS, 2), kvListName, "testing");
        addSampleKVItems(command);

        String xml = command.toXML();
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:registrant-1.0\"><registrantTransfer><name>jtkutest.com.ae</name><curExpDate>" +
                EPPDateFormatter.toXSDate(curExpDate) +
                "</curExpDate><period unit=\"y\">2</period><kvlist xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" name=\"au\"><item key=\"eligibilityIDType\">Trademark</item><item key=\"eligibilityIDValue\">1231239523</item><item key=\"eligibilityName\">Blah</item><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantIDType\">Trade License</item><item key=\"registrantIDValue\">01241326211</item><item key=\"registrantName\">AusRegistry</item></kvlist><explanation>testing</explanation></registrantTransfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>",
                xml);
    }

    private void addSampleKVItems(DomainRegistrantTransferCommand cmd) {
        cmd.addItem("policyReason", String.valueOf(policyReason));
        cmd.addItem("eligibilityIDType", eligibilityIDType);
        cmd.addItem("registrantIDType", registrantIDType);
        cmd.addItem("registrantIDValue", registrantIDValue);
        cmd.addItem("registrantName", registrantName);
        cmd.addItem("eligibilityIDValue", eligibilityIDValue);
        cmd.addItem("eligibilityName", eligibilityName);
        cmd.addItem("eligibilityType", eligibilityType);
    }
}
