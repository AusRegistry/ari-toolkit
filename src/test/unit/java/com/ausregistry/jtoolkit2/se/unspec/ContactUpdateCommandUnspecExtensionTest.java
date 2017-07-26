package com.ausregistry.jtoolkit2.se.unspec;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.ContactUpdateCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ContactUpdateCommandUnspecExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXmlWithExtContactYInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension(Boolean.TRUE, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                + "</update></update>"
                + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                + "<unspec> extContact=Y</unspec></extension></extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXmlWithoutExtContactNInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension(Boolean.FALSE, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                            + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                            + "</update></update>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec> extContact=N</unspec></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXmlWithNexusCategoryInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension(null, "ORG");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                            + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                            + "</update></update>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec> nexusCategory=ORG</unspec></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXmlWithoutNexusCategoryInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension(null, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                            + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                            + "</update></update>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec/></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXmlWithExtContactAndNexusCategoryInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension(Boolean.TRUE, "ORG");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                            + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                            + "</update></update>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec> extContact=Y nexusCategory=ORG</unspec></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldGenerateValidXmlWithAppPurposeAndNexusCategoryInUnspec() {
        Command cmd = new ContactUpdateCommand("JTKUTEST", "jtkUt3st");
        ContactUpdateCommandUnspecExtension ext = new ContactUpdateCommandUnspecExtension();
        ext.setAppPurpose("P5");
        ext.setNexusCategory("C32/US");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update>"
                            + "<update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<chg><authInfo><pw>jtkUt3st</pw></authInfo></chg>"
                            + "</update></update>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec> nexusCategory=C32/US appPurpose=P5</unspec></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

