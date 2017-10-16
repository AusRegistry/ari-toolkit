package com.ausregistry.jtoolkit2.se.fee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;


public class DomainApplicationFeeCommandExtensionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtension() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainApplicationFeeCommandExtension ext =
                new DomainApplicationFeeCommandExtension(BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00),
                        BigDecimal.valueOf(30.00), "USD");

        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                    + "<extension><create xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">"
                    + "<currency>USD</currency>"
                    + "<fee description=\"Application Fee\">10.00</fee>"
                    + "<fee description=\"Allocation Fee\">15.00</fee>"
                    + "<fee description=\"Registration Fee\">30.00</fee>"
                    + "</create>"
                    + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }

    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtensionAndRequireXmlNamespacePrefix() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainApplicationFeeCommandExtension ext =
                new DomainApplicationFeeCommandExtension(BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00),
                        BigDecimal.valueOf(30.00), "USD");

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<e:epp xmlns:e=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<e:command>"
                + "<e:create>"
                + "<domain:create xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<domain:name>jtkutest.com.au</domain:name>"
                + "<domain:authInfo><domain:pw>jtkUT3st</domain:pw></domain:authInfo>"
                + "</domain:create>"
                + "</e:create>"
                + "<e:extension>"
                + "<fee:create xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">"
                + "<fee:currency>USD</fee:currency>"
                + "<fee:fee description=\"Application Fee\">10.00</fee:fee>"
                + "<fee:fee description=\"Allocation Fee\">15.00</fee:fee>"
                + "<fee:fee description=\"Registration Fee\">30.00</fee:fee>"
                + "</fee:create>"
                + "</e:extension>"
                + "<e:clTRID>JTKUTEST.20070101.010101.0</e:clTRID>"
                + "</e:command>"
                + "</e:epp>";

        try {
            cmd.appendExtension(ext);
            assertEquals(expectedXml, cmd.toXML(XmlOutputConfig.prefixAllNamespaceConfig()));
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldFailWhenApplicationFeeIsMissing() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'applicationFee' is required.");
        final DomainApplicationFeeCommandExtension ext =
                new DomainApplicationFeeCommandExtension(null, BigDecimal.valueOf(15.00),
                        BigDecimal.valueOf(30.00), "USD");

    }

    @Test
    public void shouldFailWhenAllocationFeeIsMissing() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'allocationFee' is required.");
        final DomainApplicationFeeCommandExtension ext =
                new DomainApplicationFeeCommandExtension(BigDecimal.valueOf(10.00), null,
                        BigDecimal.valueOf(30.00), "USD");

    }

    @Test
    public void shouldFailWhenRegistrationFeeIsMissing() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'registrationFee' is required.");
        final DomainApplicationFeeCommandExtension ext =
                new DomainApplicationFeeCommandExtension(BigDecimal.valueOf(10.00), BigDecimal.valueOf(15.00),
                        null, "USD");

    }
}
