package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.PeriodUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BlockedRenewCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXMLWithDefaultPeriod() {
        String id = "BD-001";
        String currentExpiry = "2006-12-25";
        Command cmd = new BlockedRenewCommand(id, EPPDateFormatter.fromXSDateTime(currentExpiry + "T00:00:00.0Z"));
        try {
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command>"
                    + "<renew>"
                    + "<renew xmlns=\"urn:X-ar:params:xml:ns:blocked-1.0\""
                        + " xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\">"
                    + "<id>" + id + "</id>"
                    + "<curExpDate>" + currentExpiry + "</curExpDate>"
                    + "</renew>"
                    + "</renew>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command>"
                    + "</epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXMLWithASpecifiedPeriod() {
        String id = "BD-001";
        String currentExpiry = "2006-12-25";
        Period period = new Period(PeriodUnit.YEARS, 2);
        Command cmd = new BlockedRenewCommand(id, EPPDateFormatter.fromXSDateTime(currentExpiry + "T00:00:00.0Z"),
                period);
        try {
            String xml = cmd.toXML();
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command>"
                    + "<renew>"
                    + "<renew xmlns=\"urn:X-ar:params:xml:ns:blocked-1.0\""
                        + " xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\">"
                    + "<id>" + id + "</id>"
                    + "<curExpDate>" + currentExpiry + "</curExpDate>"
                    + "<period unit=\"" + period.getUnit().toString() + "\">" + period.getPeriod() + "</period>"
                    + "</renew>"
                    + "</renew>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command>"
                    + "</epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

