package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainCreateIdnaCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainCreateCommandIdnaWithLanguage() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIdnaCommandExtension idnExt = new DomainCreateIdnaCommandExtension("jtkutest.com.au", "test");
        cmd.appendExtension(idnExt);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:idnadomain-1.0\"><userForm language=\"test\">jtkutest.com.au</userForm></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

