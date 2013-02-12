package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

public class DomainVariantInfoCommandTest {
    private DomainVariantInfoCommand domainVariantInfoCmd;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
        domainVariantInfoCmd = new DomainVariantInfoCommand("ا١٢٣-١.idn.allowed.ae", "ar");
    }

    @Test
    public void testDomainVariantInfoCommand() throws SAXException {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><extension><command xmlns=\"urn:X-ar:params:xml:ns:viext-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:viext-1.0 viext-1.0.xsd\"><variantInfo><variantInfo xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:variant-1.0 variant-1.0.xsd\"><name language=\"ar\">ا١٢٣-١.idn.allowed.ae</name></variantInfo></variantInfo><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>",
                domainVariantInfoCmd.toXML());
    }

    @Test
    public void testGetCommandType() {
        assertEquals(domainVariantInfoCmd.getCommandType().getCommandName(),
                "variantInfo");
    }

    @Test
    public void testGetObjectType() {
        assertEquals(ExtendedObjectType.VARIANT.getName(),
                domainVariantInfoCmd.getObjectType().getName());
    }
}
