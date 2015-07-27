package com.ausregistry.jtoolkit2.se.rgp;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import org.junit.Before;
import org.junit.Test;

public class DomainRestoreRequestCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainRestoreRequest() throws Exception {
        final DomainRestoreRequestCommand cmd = new DomainRestoreRequestCommand("jtkutest.com.au");
        assertEquals(cmd.toXML(),
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" "
              +      "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
              +      "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
              +   "<command>"
              +     "<update>"
              +       "<update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
              +            "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 "
              +            "domain-1.0.xsd\">"
              +          "<name>jtkutest.com.au</name>"
              +          "<chg/>"
              +       "</update>"
              +     "</update>"
              +     "<extension>"
              +       "<update xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\">"
              +         "<restore op=\"request\"/>"
              +       "</update>"
              +     "</extension>"
              +     "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"
              +   "</command>"
              + "</epp>");
    }

}
