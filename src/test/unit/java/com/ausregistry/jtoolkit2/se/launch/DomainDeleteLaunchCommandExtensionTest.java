package com.ausregistry.jtoolkit2.se.launch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainDeleteCommand;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DomainDeleteLaunchCommandExtensionTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyLaunchExtension() throws SAXException {

        final Command cmd = new DomainDeleteCommand("jtkutest.com.au");
        final DomainDeleteLaunchCommandExtension ext = new DomainDeleteLaunchCommandExtension();
        ext.setPhaseName("sunrise-fcfs");
        ext.setPhaseType(PhaseType.SUNRISE);
        ext.setApplicationId("myApplication");
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<command><delete><delete xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<name>jtkutest.com.au</name></delete></delete>"
                + "<extension><delete xmlns=\"urn:ietf:params:xml:ns:launch-1.0\">"
                + "<phase name=\"sunrise-fcfs\">sunrise</phase>"
                + "<applicationID>myApplication</applicationID></delete>"
                + "</extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

    }

}