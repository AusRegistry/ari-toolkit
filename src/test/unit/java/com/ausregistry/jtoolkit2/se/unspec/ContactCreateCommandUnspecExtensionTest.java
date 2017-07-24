package com.ausregistry.jtoolkit2.se.unspec;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.ContactCreateCommand;
import com.ausregistry.jtoolkit2.se.IntPostalInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ContactCreateCommandUnspecExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateValidXmlWithExtContactYInUnspec() {
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension(Boolean.TRUE, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                    + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                    + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                    + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr><city>Melbourne</city><cc>au</cc>"
                    + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                    + "</authInfo></create></create>"
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
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension(Boolean.FALSE, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                        + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                        + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                        + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                        + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr><city>Melbourne</city><cc>au</cc>"
                        + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                        + "</authInfo></create></create>"
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
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension(null, "ORG");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                            + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr>"
                            + "<city>Melbourne</city><cc>au</cc>"
                            + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                            + "</authInfo></create></create>"
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
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension(null, null);
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                            + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr>"
                            + "<city>Melbourne</city><cc>au</cc>"
                            + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                            + "</authInfo></create></create>"
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
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension(Boolean.TRUE, "ORG");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                            + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr>"
                            + "<city>Melbourne</city><cc>au</cc>"
                            + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                            + "</authInfo></create></create>"
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
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                new IntPostalInfo("JTK Unit Test", "Melbourne", "au"), "jtktest@ausregistry.com.au");
        ContactCreateCommandUnspecExtension ext = new ContactCreateCommandUnspecExtension();
        ext.setAppPurpose("P1");
        ext.setNexusCategory("C31/AU");
        try {
            cmd.appendExtension(ext);
            String xml = cmd.toXML();
            String expectedXml =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create>"
                            + "<create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation="
                            + "\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id>"
                            + "<postalInfo type=\"int\"><name>JTK Unit Test</name><addr>"
                            + "<city>Melbourne</city><cc>au</cc>"
                            + "</addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw>"
                            + "</authInfo></create></create>"
                            + "<extension><extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">"
                            + "<unspec> nexusCategory=C31/AU appPurpose=P1</unspec></extension></extension>"
                            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
            assertEquals(expectedXml, xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

