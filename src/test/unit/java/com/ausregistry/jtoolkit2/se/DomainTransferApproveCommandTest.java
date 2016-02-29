package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;

public class DomainTransferApproveCommandTest {
    private static final String CL_ID = "JTKUTEST";
    private static final String NAME_1 = "approve1.com.au";
    private static final String PW_1 = "app1evo";
    private static final String NAME_2 = "approve2.com.au";
    private static final String CON_1_PW = "con1pw";
    private static final String CON_1_ROID = "C2006120801-AR";

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID(CL_ID);
    }

    @Test
    public void testDomainTransferApproveCommandWithNameOnly() throws SAXException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><transfer op=\"approve\">"
                + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>approve1.com.au</name>"
                + "</transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

        String actual = new DomainTransferApproveCommand(NAME_1).toXML();
        assertEquals(expected, actual);
    }

    @Test
    public void testDomainTransferApproveCommandWithNameAndPassword() throws SAXException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><transfer op=\"approve\">"
                + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>approve1.com.au</name>"
                + "<authInfo><pw>app1evo</pw></authInfo>"
                + "</transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

        String actual = new DomainTransferApproveCommand(NAME_1, PW_1).toXML();
        assertEquals(expected, actual);
    }

    @Test
    public void testDomainTransferApproveCommandWithNameRoidAndPassword() throws SAXException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><transfer op=\"approve\">"
                + "<transfer xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>approve2.com.au</name>"
                + "<authInfo><pw roid=\"C2006120801-AR\">con1pw</pw></authInfo>"
                + "</transfer></transfer><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>";

        String actual = new DomainTransferApproveCommand(NAME_2, CON_1_ROID, CON_1_PW).toXML();
        assertEquals(expected, actual);
    }
}

