package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.DSOrKeyType;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainUpdateCommandExtension;

public class DomainUpdateCommandTest {
    private static DomainAdd add;
    private static DomainRem rem1, rem2;

    static {
        add =
                new DomainAdd(new String[] { "ns1.jtkutest.com.au", "ns2.jtkutest.com.au" },
                        new String[] { "JTKCON" }, new String[] { "JTKCON2" },
                        new String[] { "JTKCON3" }, new Status[] { new Status("clientHold",
                                "non-payment") });

        rem1 =
                new DomainRem(new String[] { "ns3.jtkutest.com.au", "ns4.jtkutest.com.au" },
                        new String[] { "JTKCON2" }, new String[] { "JTKCON" }, null,
                        new Status[] { new Status("clientDeleteProhibited") });

        rem2 =
                new DomainRem(null, new String[] { "JTKCON2" }, new String[] { "JTKCON" }, null,
                        new Status[] { new Status("clientDeleteProhibited") });
    }

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainUpdateCommandString() {
        Command cmd = new DomainUpdateCommand("jtkutest.com.au");
        try {
            String xml = cmd.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainUpdateCommandStringStringDomainAddDomainRemString() {
        Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", add, rem1, "JTKCON");
        try {
            String xml = cmd.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><add><ns><hostObj>ns1.jtkutest.com.au</hostObj><hostObj>ns2.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"billing\">JTKCON3</contact><status s=\"clientHold\">non-payment</status></add><rem><ns><hostObj>ns3.jtkutest.com.au</hostObj><hostObj>ns4.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON2</contact><contact type=\"admin\">JTKCON</contact><status s=\"clientDeleteProhibited\"/></rem><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainUpdateRemoveWithoutNameservers() {
        Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", add, rem2, "JTKCON");
        try {
            String xml = cmd.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><add><ns><hostObj>ns1.jtkutest.com.au</hostObj><hostObj>ns2.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"billing\">JTKCON3</contact><status s=\"clientHold\">non-payment</status></add><rem><contact type=\"tech\">JTKCON2</contact><contact type=\"admin\">JTKCON</contact><status s=\"clientDeleteProhibited\"/></rem><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainUpdateRemoveWithNameserversWithSecDNS() throws Exception {
        final Command cmd =
                new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", add, rem1, "JTKCON");

        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");
        
        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><add><ns><hostObj>ns1.jtkutest.com.au</hostObj><hostObj>ns2.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON</contact><contact type=\"admin\">JTKCON2</contact><contact type=\"billing\">JTKCON3</contact><status s=\"clientHold\">non-payment</status></add><rem><ns><hostObj>ns3.jtkutest.com.au</hostObj><hostObj>ns4.jtkutest.com.au</hostObj></ns><contact type=\"tech\">JTKCON2</contact><contact type=\"admin\">JTKCON</contact><status s=\"clientDeleteProhibited\"/></rem><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }
}
