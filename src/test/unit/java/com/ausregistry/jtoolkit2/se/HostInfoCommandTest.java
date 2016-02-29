package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;

public final class HostInfoCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHostInfoCommand() throws Exception {
        final Command cmd = new HostInfoCommand("ns1.jtkutest.com.au");
        assertEquals(getInfoCommandExpectedXml("ns1.jtkutest.com.au"), cmd.toXML());
    }

    @Test
    public void testHostInfoDnsForm() throws Exception {
        final Command cmd = new HostInfoCommand("xn--ns16-kdba.jtkutest.com.au");
        assertEquals(getInfoCommandExpectedXml("xn--ns16-kdba.jtkutest.com.au"), cmd.toXML());
    }

    @Test
    public void testHostInfoUserForm() throws Exception {
        final Command cmd = new HostInfoCommand("\u0257\u018c.jtkutest.com.au");
        assertEquals(getInfoCommandExpectedXml("\u0257\u018c.jtkutest.com.au"), cmd.toXML());
    }

    private static String getInfoCommandExpectedXml(final String hostName) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
            result.append("<command>");
                result.append("<info>");
                    result.append("<info xmlns=\"urn:ietf:params:xml:ns:host-1.0\"");
                    result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd\">");
                    result.append("<name>" + hostName + "</name>");
                    result.append("</info>");
                result.append("</info>");
                result.append("<clTRID>JTKUTEST.20070101.010101.0</clTRID>");
            result.append("</command>");
        result.append("</epp>");
        return result.toString();
    }

}

