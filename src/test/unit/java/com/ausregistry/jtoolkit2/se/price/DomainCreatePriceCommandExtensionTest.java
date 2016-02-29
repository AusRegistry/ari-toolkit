package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;

public class DomainCreatePriceCommandExtensionTest {

    private static final String DOMAIN_CREATE_ACK_NO_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack/>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_CREATE_ACK_CREATE_PRICE_ONLY_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack>"
            + "<price>100.00</price>"
            + "</ack>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_CREATE_ACK_RENEW_PRICE_ONLY_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack>"
            + "<renewalPrice>95.90</renewalPrice>"
            + "</ack>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_CREATE_ACK_BOTH_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<create>"
            + "<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</create>"
            + "</create>"
            + "<extension>"
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack>"
            + "<price>100.00</price>"
            + "<renewalPrice>90.95</renewalPrice>"
            + "</ack>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainCreateCommand domainCreateCommand;
    private DomainCreatePriceCommandExtension domainCreatePriceCommandExtension;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainCreateCommand = new DomainCreateCommand("premium.example", "2fooBAR");
        domainCreatePriceCommandExtension = new DomainCreatePriceCommandExtension();
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndNoPrices() throws Exception {
        domainCreateCommand.appendExtension(domainCreatePriceCommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_NO_PRICE_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndCreatePrice() throws Exception {
        domainCreatePriceCommandExtension.setPrice(BigDecimal.valueOf(10000, 2));
        domainCreateCommand.appendExtension(domainCreatePriceCommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_CREATE_PRICE_ONLY_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndRenewalPrice() throws Exception {
        domainCreatePriceCommandExtension.setRenewalPrice(BigDecimal.valueOf(9590, 2));
        domainCreateCommand.appendExtension(domainCreatePriceCommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_RENEW_PRICE_ONLY_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndBothPrices() throws Exception {
        domainCreatePriceCommandExtension.setPrice(BigDecimal.valueOf(10000, 2));
        domainCreatePriceCommandExtension.setRenewalPrice(BigDecimal.valueOf(9095, 2));
        domainCreateCommand.appendExtension(domainCreatePriceCommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_BOTH_PRICE_XML, domainCreateCommand.toXML());
    }
}
