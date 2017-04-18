package com.ausregistry.jtoolkit2.se.unspec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;


public class DomainUpdateCommandUnspecExtensionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyUnspecExtension() {

        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension("abc123");

        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=abc123");
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenExtContactDoesNotProvided() {

        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension(null);

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
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension("");
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("extContact=");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateXmlWhenNullUnspecValuesProvidedWithWhoisType() {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension(null, null);
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
        final Command cmd = new  DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension(WhoisType.LEGAL, null);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("WhoisType=LEGAL");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateXmlWhenUnspecValuesProvidedForPublishButNullForTypeWithWhoisType() {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension(null, true);
        try {
            cmd.appendExtension(ext);
            String expectedXml = getCommandXmlWithUnspec("Publish=Y");
            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenUnspecValuesProvidedForTypeAndPublishWithWhoisType() {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        final DomainUpdateCommandUnspecExtension ext =
                new DomainUpdateCommandUnspecExtension(WhoisType.LEGAL, false);
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
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name></update></update>"
                + "<extension>"
                + "<extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                + element
                + "</extension>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
    }
}
