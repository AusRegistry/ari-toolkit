package com.ausregistry.jtoolkit2.se.extendedAvailability;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainCheckExtendedAvailabilityCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGenerateCorrectXmlWhenExtendedAvailabilityExtensionProvidedWithDomainCheckCommand()
            throws SAXException {
        String domainName = "domainOne.zone";
        DomainCheckCommand checkCommand = new DomainCheckCommand(domainName);
        DomainCheckExtendedAvailabilityCommandExtension ext = new DomainCheckExtendedAvailabilityCommandExtension();

        checkCommand.appendExtension(ext);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command>"
                + "<check>"
                + "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>" + domainName + "</name>"
                + "</check>"
                + "</check>"
                + "<extension>"
                + "<check xmlns=\"urn:ar:params:xml:ns:exAvail-1.0\"/>"
                + "</extension>"
                + "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command>"
                + "</epp>", checkCommand.toXML());
    }
}
