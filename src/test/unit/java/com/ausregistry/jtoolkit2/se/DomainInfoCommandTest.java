package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.xml.Attribute;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainInfoCommandTest {
    private DomainInfoCommand cmd1;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
        cmd1 = new DomainInfoCommand("jtkutest.com.au");
    }

    @Test
    public void testDomainInfoCommand() {
        assertNotNull(cmd1);
    }

    @Test
    public void testGetCommandType() {
        assertEquals(cmd1.getCommandType().getCommandName(), "info");
    }

    @Test
    public void testGetObjectType() {
        assertEquals(cmd1.getObjectType().getName(), "domain");
    }

    @Test
    public void testToXML() {
        try {
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd1.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldSupportHostsAttributeInDomainInfoCommand() {
        DomainInfoCommand domainInfo = new DomainInfoCommand("domain.com", new Attribute("hosts", "del"));

        String outputXml = null;
        try {
            outputXml = domainInfo.toXML();
        } catch (SAXException e) {
            fail("Parsing failed with message: " + e.getMessage());
        }

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name hosts=\"del\">domain.com</name></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
        assertEquals("Should be expected output", expectedXml, outputXml);
    }

    @Test
    public void shouldSupportHostsAttributeInDomainInfoCommandWithPassword() throws Exception {

        DomainInfoCommand domainInfo = new DomainInfoCommand("domain.com", "password", new Attribute("hosts", "del"));

        String outputXml = null;
        try {
            outputXml = domainInfo.toXML();
        } catch (SAXException e) {
            fail("Parsing failed with message: " + e.getMessage());
        }

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name hosts=\"del\">domain.com</name><authInfo><pw>password</pw></authInfo></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
        assertEquals("Should be expected output", expectedXml, outputXml);
    }

    @Test
    public void shouldSupportHostsAttributeInDomainInfoCommandWithROIDAndPassword() throws Exception {

        DomainInfoCommand domainInfo = new DomainInfoCommand("domain.com", "D_123A-ARI", "password", new Attribute("hosts", "del"));

        String outputXml = null;
        try {
            outputXml = domainInfo.toXML();
        } catch (SAXException e) {
            fail("Parsing failed with message: " + e.getMessage());
        }

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><info><info xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name hosts=\"del\">domain.com</name><authInfo><pw roid=\"D_123A-ARI\">password</pw></authInfo></info></info><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";
        assertEquals("Should be expected output", expectedXml, outputXml);
    }

}

