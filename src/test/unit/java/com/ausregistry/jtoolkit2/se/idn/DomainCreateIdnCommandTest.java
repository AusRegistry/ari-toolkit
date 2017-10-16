package com.ausregistry.jtoolkit2.se.idn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainCreateIdnCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainCreateIdnCommandWithLanguage() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIdnCommandExtension idnExt = new DomainCreateIdnCommandExtension("test");
        cmd.appendExtension(idnExt);
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ar:params:xml:ns:idn-1.0\"><languageTag>test</languageTag></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldOutputXmlWithNamespacePrefixWhenConfigured() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIdnCommandExtension idnExt = new DomainCreateIdnCommandExtension("test");
        cmd.appendExtension(idnExt);
        try {
            String xml = cmd.toXML(XmlOutputConfig.prefixAllNamespaceConfig());
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><e:epp xmlns:e=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><e:command><e:create><domain:create xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>jtkutest.com.au</domain:name><domain:authInfo><domain:pw>jtkUT3st</domain:pw></domain:authInfo></domain:create></e:create><e:extension><idn:create xmlns:idn=\"urn:ar:params:xml:ns:idn-1.0\"><idn:languageTag>test</idn:languageTag></idn:create></e:extension><e:clTRID>JTKUTEST.20070101.010101.0</e:clTRID></e:command></e:epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}

