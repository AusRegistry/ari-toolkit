package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;

public class SecDnsDomainUpdateCommandExtensionChangeTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testSecDNSAllFieldsUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        ChgType chgData = new ChgType();
        chgData.setMaxSigLife(maxSigLife);
        ext.setChgData(chgData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><chg><maxSigLife>604800</maxSigLife></chg></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }
}
