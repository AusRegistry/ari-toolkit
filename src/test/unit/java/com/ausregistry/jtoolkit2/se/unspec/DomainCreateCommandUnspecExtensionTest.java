package com.ausregistry.jtoolkit2.se.unspec;

import static com.ausregistry.jtoolkit2.se.unspec.WhoisType.LEGAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;


public class DomainCreateCommandUnspecExtensionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyUnspecExtensionWithExtContact() {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setExtContactId("abc123");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=abc123");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenNullUnspecValueProvidedWithExtContact() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec(null);
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenEmptyUnspecValueProvidedWithExtContact() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setExtContactId("");

        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateWhenNoUnspecValuesProvidedWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec(null);
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForTypeButNullForPublishWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setWhoisType(LEGAL);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("WhoisType=Legal");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForPublishButNullForTypeWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setPublish(true);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("Publish=Y");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForPublishAndExtContact() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setPublish(true);
        ext.setExtContactId("abc123");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=abc123 Publish=Y");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForWhoisTypeAndExtContact() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setWhoisType(WhoisType.NATURAL);
        ext.setExtContactId("abc123");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=abc123 WhoisType=Natural");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForTypeAndPublishWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setWhoisType(LEGAL);
        ext.setPublish(false);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("WhoisType=Legal Publish=N");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForRservationDomain() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setReservationDomain(true);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("ReservationDomain=Yes");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesNoProvidedForBackorder() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setReservationDomain(false);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec(null);
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenResellerDetailsAreProvided() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setResellerName("Reseller Name");
        ext.setResellerUrl("www.reseller.com.au");
        ext.setResellerPhone("+611234567891");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec(
                    "ResellerName=Reseller+Name ResellerUrl=www.reseller.com.au ResellerPhone=+611234567891");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenOnlyResellerNameisProvided() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setResellerName("ResellerName");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec(
                    "ResellerName=ResellerName");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldAddUinWhenProvided() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setUin("457228455201017341");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("UIN=457228455201017341");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenTravelAckIsTrue() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setTravelIndustryAcknowledgement(true);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("TravelIndustry=Y");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenTravelAckIsFalse() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext = new DomainCreateCommandUnspecExtension();
        ext.setTravelIndustryAcknowledgement(false);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("TravelIndustry=N");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    private String getCommandXmlWithUnspec(String unspec) {
        String element;
        if (unspec == null) {
            element = "<unspec/>";
        } else {
            element = "<unspec>" + unspec + "</unspec>";
        }
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                + "<extension>"
                + "<extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                + element
                + "</extension>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
    }

}
