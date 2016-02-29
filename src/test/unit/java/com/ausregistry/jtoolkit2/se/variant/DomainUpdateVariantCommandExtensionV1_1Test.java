package com.ausregistry.jtoolkit2.se.variant;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.se.IdnDomainVariant;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;

public class DomainUpdateVariantCommandExtensionV1_1Test {

    private static final String DOMAIN_UPDATE_VARIANT_ADD_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<update>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>example.com</name>"
            + "</update>"
            + "</update>"
            + "<extension>"
            + "<update xmlns=\"urn:ar:params:xml:ns:variant-1.1\">"
            + "<add>"
            + "<variant>xn--4xal.example</variant>"
            + "</add>"
            + "</update>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_UPDATE_VARIANT_REM_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<update>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>example.com</name>"
            + "</update>"
            + "</update>"
            + "<extension>"
            + "<update xmlns=\"urn:ar:params:xml:ns:variant-1.1\">"
            + "<rem>"
            + "<variant>xn--4xal.example</variant>"
            + "</rem>"
            + "</update>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_UPDATE_VARIANT_COMPO_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<update>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>example.com</name>"
            + "</update>"
            + "</update>"
            + "<extension>"
            + "<update xmlns=\"urn:ar:params:xml:ns:variant-1.1\">"
            + "<rem>"
            + "<variant>xn--3dfh6.example</variant>"
            + "<variant>xn--ii96.example</variant>"
            + "</rem>"
            + "<add>"
            + "<variant>xn--4xal.example</variant>"
            + "<variant>xn--3uyi3.example</variant>"
            + "<variant>xn--3xxr5.example</variant>"
            + "</add>"
            + "</update>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";
    private DomainUpdateCommand domainUpdateCommand;
    private DomainUpdateVariantCommandExtensionV1_1 domainUpdateVariantCommandExtension;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainUpdateCommand = new DomainUpdateCommand("example.com");
        domainUpdateVariantCommandExtension = new DomainUpdateVariantCommandExtensionV1_1();
    }

    @Test
    public void shouldGenerateXmlWithAddVariantExtension() throws Exception {
        domainUpdateVariantCommandExtension.addVariant(new IdnDomainVariant("xn--4xal.example"));
        domainUpdateCommand.appendExtension(domainUpdateVariantCommandExtension);

        assertEquals(DOMAIN_UPDATE_VARIANT_ADD_XML, domainUpdateCommand.toXML());
    }

    @Test
    public void shouldGenerateXmlWithRemVariantExtension() throws Exception {
        domainUpdateVariantCommandExtension.remVariant(new IdnDomainVariant("xn--4xal.example"));
        domainUpdateCommand.appendExtension(domainUpdateVariantCommandExtension);

        assertEquals(DOMAIN_UPDATE_VARIANT_REM_XML, domainUpdateCommand.toXML());
    }

    @Test
    public void shouldGenerateXmlWithMultipleAddAndRemVariantExtension() throws Exception {
        domainUpdateVariantCommandExtension.addVariant(new IdnDomainVariant("xn--4xal.example"),
                new IdnDomainVariant("xn--3uyi3.example"), new IdnDomainVariant("xn--3xxr5.example"));
        domainUpdateVariantCommandExtension.remVariant(new IdnDomainVariant("xn--3dfh6.example"),
                new IdnDomainVariant("xn--ii96.example"));
        domainUpdateCommand.appendExtension(domainUpdateVariantCommandExtension);

        assertEquals(DOMAIN_UPDATE_VARIANT_COMPO_XML, domainUpdateCommand.toXML());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAddingNullVariantExtension() {
        domainUpdateVariantCommandExtension.addVariant(new IdnDomainVariant("something.com"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRemovingNullVariantExtension() {
        domainUpdateVariantCommandExtension.remVariant(new IdnDomainVariant("something.com"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAddingVariantWithNullName() {
        domainUpdateVariantCommandExtension.addVariant(new IdnDomainVariant(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenRemovingVariantWithNullName() {
        domainUpdateVariantCommandExtension.remVariant(new IdnDomainVariant(null));
    }
}
