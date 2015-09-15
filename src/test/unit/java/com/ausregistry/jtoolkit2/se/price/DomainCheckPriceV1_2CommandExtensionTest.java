package com.ausregistry.jtoolkit2.se.price;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.PeriodUnit;

public class DomainCheckPriceV1_2CommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGetValidXmlWhenPricingExtensionIsAppliedWithNoPeriod() throws SAXException {
        Command cmd = new DomainCheckCommand(new String[]{"domain.zone"});
        DomainCheckPriceV1_2CommandExtension extension = new DomainCheckPriceV1_2CommandExtension();

        cmd.appendExtension(extension);

        assertEquals(cmd.toXML(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<check>"
                + "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain.zone</name>"
                + "</check>"
                + "</check>"
                + "<extension>"
                + "<check xmlns=\"urn:ar:params:xml:ns:price-1.2\"/>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>");
    }

    @Test
    public void shouldGetValidXmlWhenPricingExtensionIsAppliedWithPeriod() throws SAXException {
        Command cmd = new DomainCheckCommand(new String[]{"domain.zone"});
        DomainCheckPriceV1_2CommandExtension extension = new DomainCheckPriceV1_2CommandExtension();
        extension.setPeriod(new Period(PeriodUnit.YEARS, 2));

        cmd.appendExtension(extension);

        assertEquals(cmd.toXML(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<check>"
                + "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>domain.zone</name>"
                + "</check>"
                + "</check>"
                + "<extension>"
                + "<check xmlns=\"urn:ar:params:xml:ns:price-1.2\">"
                + "<period unit=\"y\">2</period>"
                + "</check>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>");
    }
}
