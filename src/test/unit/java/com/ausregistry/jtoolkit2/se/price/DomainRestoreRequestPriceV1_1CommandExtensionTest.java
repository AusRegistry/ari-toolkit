package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.rgp.DomainRestoreRequestCommand;

public class DomainRestoreRequestPriceV1_1CommandExtensionTest {

    private static final String DOMAIN_RESTORE_REQUEST_ACK_NO_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<update>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            +            "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<chg/>"
            + "</update>"
            + "</update>"
            + "<extension>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\">"
            + "<restore op=\"request\"/>"
            + "</update>"
            + "<update xmlns=\"urn:ar:params:xml:ns:price-1.1\">"
            + "<ack/>"
            + "</update>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private static final String DOMAIN_RESTORE_REQUEST_ACK_PRICE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
            + "<command>"
            + "<update>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
            +            "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
            + "<name>premium.example</name>"
            + "<chg/>"
            + "</update>"
            + "</update>"
            + "<extension>"
            + "<update xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\">"
            + "<restore op=\"request\"/>"
            + "</update>"
            + "<update xmlns=\"urn:ar:params:xml:ns:price-1.1\">"
            + "<ack>"
            + "<price>100.00</price>"
            + "</ack>"
            + "</update>"
            + "</extension>"
            + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
            + "</command>"
            + "</epp>";

    private DomainRestoreRequestCommand domainRestoreRequestCommand;
    private DomainRestoreRequestPriceV1_1CommandExtension domainRestoreRequestPriceV1_1CommandExtension;

    @Before
    public void setUp() {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");

        domainRestoreRequestCommand = new DomainRestoreRequestCommand("premium.example");
        domainRestoreRequestPriceV1_1CommandExtension = new DomainRestoreRequestPriceV1_1CommandExtension();
    }

    @Test
    public void shouldCreateDomainRestoreRequestXmlWithPriceAckAndNoPrice() throws Exception {
        domainRestoreRequestCommand.appendExtension(domainRestoreRequestPriceV1_1CommandExtension);

        assertEquals(DOMAIN_RESTORE_REQUEST_ACK_NO_PRICE_XML, domainRestoreRequestCommand.toXML());
    }

    @Test
    public void shouldCreateDomainRestoreRequestXmlWithPriceAckIncludingPrices() throws Exception {

        domainRestoreRequestPriceV1_1CommandExtension.setPrice(BigDecimal.valueOf(10000, 2));
        domainRestoreRequestCommand.appendExtension(domainRestoreRequestPriceV1_1CommandExtension);

        assertEquals(DOMAIN_RESTORE_REQUEST_ACK_PRICE_XML, domainRestoreRequestCommand.toXML());
    }
}
