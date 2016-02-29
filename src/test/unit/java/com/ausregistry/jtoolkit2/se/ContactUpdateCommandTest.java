package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ContactUpdateCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContactUpdateCommandStringString() {
        Command cmd = new ContactUpdateCommand("JTKCON", "jtkUT3st");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON</id><chg><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testContactUpdateCommandStringStringStatusArrayStringArrayIntPostalInfoLocalPostalInfoStringStringStringStringStringDisclose() {
        Command cmd = new ContactUpdateCommand("JTKCON", "jtkUT3st",
                new Status[] {new Status("clientUpdateProhibited", "testing")},
                new String[] {"clientDeleteProhibited"},
                new IntPostalInfo("AusRegistry", "Melbourne", "au"), null,
                "+61.398663710", null, "+61.398661970", null, "jtk@ausregistry.com.au", null);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:contact-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd\"><id>JTKCON</id><add><status s=\"clientUpdateProhibited\">testing</status></add><rem><status s=\"clientDeleteProhibited\"/></rem><chg><postalInfo type=\"int\"><name>AusRegistry</name><addr><city>Melbourne</city><cc>au</cc></addr></postalInfo><voice>+61.398663710</voice><fax>+61.398661970</fax><email>jtk@ausregistry.com.au</email><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

