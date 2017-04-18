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
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension("abc123");
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
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension(null);
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
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension("");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldFailWhenNullUnspecValuesProvidedWithWhoisType() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'whoisType' is required.");
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension(null, null);
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForTypeButNullForPublishWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension(LEGAL, null);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("WhoisType=LEGAL");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }


    @Test
    public void shouldFailWhenUnspecValuesProvidedForPublishButNullForTypeWithWhoisType() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'whoisType' is required.");
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension(null, true);
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForTypeAndPublishWithWhoisType() {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateCommandUnspecExtension ext =
                new DomainCreateCommandUnspecExtension(LEGAL, false);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("WhoisType=LEGAL Publish=N");
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
