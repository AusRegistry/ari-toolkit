package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Test;

public class DomainCreatePriceV1_1CommandExtensionTest {

    private final static String DOMAIN_CREATE_ACK_NO_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!--Produced with -->"
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
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.1\">"
            + "<ack/>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private final static String DOMAIN_CREATE_ACK_CREATE_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!--Produced with -->"
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
            + "<create xmlns=\"urn:ar:params:xml:ns:price-1.1\">"
            + "<ack>"
            + "<price>100.00</price>"
            + "</ack>"
            + "</create>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainCreateCommand domainCreateCommand;
    private DomainCreatePriceV1_1CommandExtension domainCreatePriceV1_1CommandExtension;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainCreateCommand = new DomainCreateCommand("premium.example", "2fooBAR");
        domainCreatePriceV1_1CommandExtension = new DomainCreatePriceV1_1CommandExtension();
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndNoPrices() throws Exception {
        domainCreateCommand.appendExtension(domainCreatePriceV1_1CommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_NO_PRICE_XML, domainCreateCommand.toXML());
    }

    @Test
    public void shouldCreateDomainCreateXmlWithPriceAckAndCreatePrice() throws Exception {
        domainCreatePriceV1_1CommandExtension.setPrice(BigDecimal.valueOf(10000, 2));
        domainCreateCommand.appendExtension(domainCreatePriceV1_1CommandExtension);

        assertEquals(DOMAIN_CREATE_ACK_CREATE_PRICE_XML, domainCreateCommand.toXML());
    }
}
