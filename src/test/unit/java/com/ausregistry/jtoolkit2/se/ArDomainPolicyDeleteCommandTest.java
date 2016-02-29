package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ArDomainPolicyDeleteCommandTest {
    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainDeleteCommand() {
        Command cmd = new ArDomainPolicyDeleteCommand("jtkutest.com.au", "jtkutest");
        try {
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<extension><command xmlns=\"urn:X-ar:params:xml:ns:arext-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd\">"
                    + "<policyDelete><policyDelete xmlns=\"urn:X-ar:params:xml:ns:ardomain-1.0\" "
                    + "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><reason>jtkutest</reason></policyDelete></policyDelete>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></extension></epp>";
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException e) {
            fail(e.getMessage());
        }
    }
}
