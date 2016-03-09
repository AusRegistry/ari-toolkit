package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class LoginCommandTest {
    private static String[] objURIs, extURIs;

    static {
        objURIs = new String[] {
                "urn:ietf:params:xml:ns:domain-1.0",
                "urn:ietf:params:xml:ns:host-1.0",
                "urn:ietf:params:xml:ns:contact-1.0"
        };
        extURIs = new String[] {
                "urn:au:params:xml:ns:auext-1.0"
        };
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
    public void testLoginCommandStringString() {
        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK");
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw>" +
                    "<options><version>1.0</version><lang>en</lang></options>" +
                    "<svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI></svcs></login>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testLoginCommandStringStringStringArrayStringArray() {
        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK",
                objURIs, extURIs);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw>" +
                    "<options><version>1.0</version><lang>en</lang></options>" +
                    "<svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI>" +
                    "<svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testLoginCommandStringStringStringStringStringArrayStringArray() {
        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK", "1.0", "fr",
                objURIs, extURIs);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw>" +
                    "<options><version>1.0</version><lang>fr</lang></options>" +
                    "<svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI>" +
                    "<svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testLoginCommandStringStringStringStringStringStringArrayStringArray() {
        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK",
                "n(-w18PW*", "1.0", "fr", objURIs, extURIs);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw><newPW>n(-w18PW*</newPW>" +
                    "<options><version>1.0</version><lang>fr</lang></options>" +
                    "<svcs><objURI>urn:ietf:params:xml:ns:domain-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI>" +
                    "<svcExtension><extURI>urn:au:params:xml:ns:auext-1.0</extURI></svcExtension></svcs></login>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testLoginCommandSecDnsExtension() {

        final String[] secDnsExtURIs = new String[] {
                "urn:ietf:params:xml:ns:secDNS-1.1"
        };

        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK",
                objURIs, secDnsExtURIs);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command><login><clID>JTKUTEST</clID><pw>1234abcd!@#$JTK</pw>" +
                    "<options><version>1.0</version><lang>en</lang></options><svcs>" +
                    "<objURI>urn:ietf:params:xml:ns:domain-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                    "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI>" +
                    "<svcExtension><extURI>urn:ietf:params:xml:ns:secDNS-1.1</extURI></svcExtension></svcs></login>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testLoginCommandSecDnsExtensionNotProvided() {

        Command cmd = new LoginCommand("JTKUTEST", "1234abcd!@#$JTK",
                objURIs, extURIs);
        try {
            String xml = cmd.toXML();
            assertFalse(xml.contains("urn:ietf:params:xml:ns:secDNS-1.1"));
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void emptyArrayOfEstUrisShouldBeIgnored() throws SAXException {
        Command cmd = new LoginCommand("JTKUTEST", "password", objURIs, new String[0]);

        String xml = cmd.toXML();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                "<command><login><clID>JTKUTEST</clID><pw>password</pw>" +
                "<options><version>1.0</version><lang>en</lang></options><svcs>" +
                "<objURI>urn:ietf:params:xml:ns:domain-1.0</objURI><objURI>urn:ietf:params:xml:ns:host-1.0</objURI>" +
                "<objURI>urn:ietf:params:xml:ns:contact-1.0</objURI></svcs></login>" +
                "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
    }

}

