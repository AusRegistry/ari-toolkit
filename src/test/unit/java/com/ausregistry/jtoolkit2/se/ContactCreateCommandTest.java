package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ContactCreateCommandTest {
    private static IntPostalInfo commonPostalInfo1, commonPostalInfo2;
    private static String email;

    static {
        commonPostalInfo1 = new IntPostalInfo("JTK Unit Test", "Melbourne", "au");
        commonPostalInfo2 = new IntPostalInfo("JTK Unit Test", "AusRegistry",
                new String[] {"Level 8", "10 Queens Road"}, "Melbourne",
                "VIC", "3004", "au");
        email = "jtktest@ausregistry.com.au";
    }

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContactCreateCommandStringStringIntPostalInfoString1() {
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                commonPostalInfo1, email);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id><postalInfo type=\"int\"><name>JTK Unit Test</name><addr><city>Melbourne</city><cc>au</cc></addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testContactCreateCommandStringStringIntPostalInfoString2() {
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                commonPostalInfo2, email);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id><postalInfo type=\"int\"><name>JTK Unit Test</name><org>AusRegistry</org><addr><street>Level 8</street><street>10 Queens Road</street><city>Melbourne</city><sp>VIC</sp><pc>3004</pc><cc>au</cc></addr></postalInfo><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw></authInfo></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testContactCreateCommandStringStringIntPostalInfoLocalPostalInfoStringStringStringStringStringDisclose1() {
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                commonPostalInfo1, null, "+61.398663710", null, "+61.398661970", null,
                email, new Disclose(false));
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id><postalInfo type=\"int\"><name>JTK Unit Test</name><addr><city>Melbourne</city><cc>au</cc></addr></postalInfo><voice>+61.398663710</voice><fax>+61.398661970</fax><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw></authInfo><disclose flag=\"0\"><voice/></disclose></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testContactCreateCommandStringStringIntPostalInfoLocalPostalInfoStringStringStringStringStringDisclose2() {
        Disclose disclose = new Disclose(false);
        disclose.setAddrInt();
        disclose.setVoice();
        Command cmd = new ContactCreateCommand("JTKUTEST", "jtkUt3st",
                commonPostalInfo2, null, "+61.398663710", null, "+61.398661970", null,
                email, disclose);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKUTEST</id><postalInfo type=\"int\"><name>JTK Unit Test</name><org>AusRegistry</org><addr><street>Level 8</street><street>10 Queens Road</street><city>Melbourne</city><sp>VIC</sp><pc>3004</pc><cc>au</cc></addr></postalInfo><voice>+61.398663710</voice><fax>+61.398661970</fax><email>jtktest@ausregistry.com.au</email><authInfo><pw>jtkUt3st</pw></authInfo><disclose flag=\"0\"><addr type=\"int\"/><voice/></disclose></create></create><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}
