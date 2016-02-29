package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainRenewCommand;

public class DomainRenewPriceCommandExtensionTest {

    private static final String DOMAIN_RENEW_ACK_NO_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<renew>"
            + "<renew xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<curExpDate>2013-04-29</curExpDate>"
            + "</renew>"
            + "</renew>"
            + "<extension>"
            + "<renew xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack/>"
            + "</renew>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_RENEW_ACK_RENEW_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<renew>"
            + "<renew xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<curExpDate>2013-04-29</curExpDate>"
            + "</renew>"
            + "</renew>"
            + "<extension>"
            + "<renew xmlns=\"urn:ar:params:xml:ns:price-1.0\">"
            + "<ack>"
            + "<renewalPrice>150.00</renewalPrice>"
            + "</ack>"
            + "</renew>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainRenewCommand domainRenewCommand;
    private DomainRenewPriceCommandExtension domainRenewPriceCommandExtension;

    @Before
    public void setUp() {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainRenewCommand = new DomainRenewCommand("premium.example",
                EPPDateFormatter.fromXSDateTime("2013-04-29T00:00:00.0Z"));
        domainRenewPriceCommandExtension = new DomainRenewPriceCommandExtension();
    }

    @Test
    public void shouldCreateDomainRenewXmlWithPriceAckAndNoRenewalPrice() throws Exception {
        domainRenewCommand.appendExtension(domainRenewPriceCommandExtension);

        assertEquals(DOMAIN_RENEW_ACK_NO_PRICE_XML, domainRenewCommand.toXML());
    }

    @Test
    public void shouldCreateDomainRenewXmlWithPriceAckIncludingRenewalPrices() throws Exception {

        domainRenewPriceCommandExtension.setRenewalPrice(BigDecimal.valueOf(15000, 2));
        domainRenewCommand.appendExtension(domainRenewPriceCommandExtension);

        assertEquals(DOMAIN_RENEW_ACK_RENEW_PRICE_XML, domainRenewCommand.toXML());
    }
}
