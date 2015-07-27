package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class HelloTest {

    @Before
    public void setUp() throws Exception {
        CLTRID.setClID("JTKUTEST");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHello() {
        Hello hello = new Hello();
        try {
            String xml = hello.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><hello/></epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}

