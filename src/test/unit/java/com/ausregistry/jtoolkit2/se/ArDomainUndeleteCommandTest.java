package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ArDomainUndeleteCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDomainUndeleteCommand() {
        Command cmd = new ArDomainUndeleteCommand("jtkutest.com.au");
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<extension><command xmlns=\"urn:X-ar:params:xml:ns:arext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd\">"
                    + "<undelete><undelete xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name></undelete></undelete>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command>"
                    + "</extension></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException e) {
            fail(e.getMessage());
        }

    }
}
