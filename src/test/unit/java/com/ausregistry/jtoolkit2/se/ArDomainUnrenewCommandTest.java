package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ArDomainUnrenewCommandTest {
    private static GregorianCalendar date =
        EPPDateFormatter.fromXSDateTime("2007-01-01T00:00:00.0Z");

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainUnrenewCommand() {
        Command cmd = new ArDomainUnrenewCommand("jtkutest.com.au", date);
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<extension><command xmlns=\"urn:X-ar:params:xml:ns:arext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd\">"
                    + "<unrenew><unrenew xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><curExpDate>2007-01-01</curExpDate></unrenew></unrenew>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException e) {
            fail(e.getMessage());
        }
    }
}
