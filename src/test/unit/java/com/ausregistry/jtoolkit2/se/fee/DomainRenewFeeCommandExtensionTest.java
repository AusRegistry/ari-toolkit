package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class DomainRenewFeeCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtension() throws SAXException {

        Command cmd = new DomainRenewCommand("jtkutest.com.au",
                EPPDateFormatter.fromXSDateTime("2016-02-14T00:00:02.0Z"),
                new Period(1));

        final DomainRenewFeeCommandExtension ext =
                new DomainRenewFeeCommandExtension(BigDecimal.valueOf(10.00), "USD");

        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command>"
                    + "<renew>"
                    + "<renew xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name>"
                    + "<curExpDate>2016-02-14</curExpDate>"
                    + "<period unit=\"y\">1</period>"
                    + "</renew>"
                    + "</renew>"
                    + "<extension>"
                    + "<renew xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">"
                    +  "<currency>USD</currency>"
                    + "<fee>10.00</fee>"
                    + "</renew>"
                    + "</extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                    + "</command>"
                    + "</epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}