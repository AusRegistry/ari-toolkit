package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainTransferRequestCommand;

public class DomainTransferRequestPriceV1_2CommandExtensionTest {

    private static final String DOMAIN_TRANSFER_REQUEST_ACK_NO_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<transfer op=\"request\">"
            + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</transfer>"
            + "</transfer>"
            + "<extension>"
            + "<transfer xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
            + "<ack/>"
            + "</transfer>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_TRANSFER_REQUEST_ACK_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<transfer op=\"request\">"
            + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<authInfo>"
            + "<pw>2fooBAR</pw>"
            + "</authInfo>"
            + "</transfer>"
            + "</transfer>"
            + "<extension>"
            + "<transfer xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
            + "<ack>"
            + "<price>100.00</price>"
            + "</ack>"
            + "</transfer>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainTransferRequestCommand domainTransferRequestCommand;
    private DomainTransferRequestPriceV1_2CommandExtension domainTransferRequestPriceV1_2CommandExtension;

    @Before
    public void setUp() {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainTransferRequestCommand = new DomainTransferRequestCommand("premium.example", "2fooBAR");
        domainTransferRequestPriceV1_2CommandExtension = new DomainTransferRequestPriceV1_2CommandExtension();
    }

    @Test
    public void shouldCreateDomainTransferRequestXmlWithPriceAckAndNoPrice() throws Exception {
        domainTransferRequestCommand.appendExtension(domainTransferRequestPriceV1_2CommandExtension);

        assertEquals(DOMAIN_TRANSFER_REQUEST_ACK_NO_PRICE_XML, domainTransferRequestCommand.toXML());
    }

    @Test
    public void shouldCreateDomainTransferRequestXmlWithPriceAckIncludingPrices() throws Exception {

        domainTransferRequestPriceV1_2CommandExtension.setPrice(BigDecimal.valueOf(10000, 2));
        domainTransferRequestCommand.appendExtension(domainTransferRequestPriceV1_2CommandExtension);

        assertEquals(DOMAIN_TRANSFER_REQUEST_ACK_PRICE_XML, domainTransferRequestCommand.toXML());
    }
}
