package com.ausregistry.jtoolkit2.se.premium;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;

import static junit.framework.Assert.assertEquals;

public class DomainCheckPremiumCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldGetValidXmlWhenPremiumExtensionIsApplied() throws SAXException {
        Command cmd = new DomainCheckCommand(new String[]{"domain.zone"});
        DomainCheckPremiumCommandExtension extension = new DomainCheckPremiumCommandExtension();

        cmd.appendExtension(extension);

        assertEquals(cmd.toXML(),"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3"+
                ".org/2001/XMLSchema-instance\" "+
                "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"+
                "<command>"+
                "<check>"+
                "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "+
                "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"+
                "<name>domain.zone</name>"+
                "</check>"+
                "</check>"+
                "<extension>"+
                "<check xmlns=\"urn:ar:params:xml:ns:premium-1.0\"/>"+
                "</extension>"+
                "<clTRID>JTKUTEST.20070101.010101.0</clTRID>"+
                "</command>"+
                "</epp>");
    }

}
