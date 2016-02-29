package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainRenewCommand;

public class DomainRenewPriceV1_2CommandExtensionTest {

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
            + "<renew xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
            + "<ack/>"
            + "</renew>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_RENEW_ACK_WITH_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
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
            + "<renew xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
            + "<ack>"
            + "<price>150.00</price>"
            + "</ack>"
            + "</renew>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainRenewCommand domainRenewCommand;
    private DomainRenewPriceV1_2CommandExtension domainRenewPriceV1_2CommandExtension;

    @Before
    public void setUp() {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainRenewCommand = new DomainRenewCommand("premium.example",
                EPPDateFormatter.fromXSDateTime("2013-04-29T00:00:00.0Z"));
        domainRenewPriceV1_2CommandExtension = new DomainRenewPriceV1_2CommandExtension();
    }

    @Test
    public void shouldCreateDomainRenewXmlWithPriceAck() throws Exception {
        domainRenewCommand.appendExtension(domainRenewPriceV1_2CommandExtension);

        assertEquals(DOMAIN_RENEW_ACK_NO_PRICE_XML, domainRenewCommand.toXML());
    }

    @Test
    public void shouldCreateDomainRenewXmlWithPriceAckIncludingRenewalPrices() throws Exception {

        domainRenewPriceV1_2CommandExtension.setPrice(BigDecimal.valueOf(15000, 2));
        domainRenewCommand.appendExtension(domainRenewPriceV1_2CommandExtension);

        assertEquals(DOMAIN_RENEW_ACK_WITH_PRICE_XML, domainRenewCommand.toXML());
    }
}
