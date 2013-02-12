package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.DSOrKeyType;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainUpdateCommandExtension;
import com.ausregistry.jtoolkit2.se.sync.SyncDomainUpdateCommandExtension;
import com.ausregistry.jtoolkit2.se.variant.DomainUpdateVariantCommandExtension;

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

    @Test
    public void testDomainUpdateSyncExpiryDate() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final SyncDomainUpdateCommandExtension extension =
                new SyncDomainUpdateCommandExtension(
                        EPPDateFormatter.fromXSDateTime("2005-04-03T22:00:00.0Z"));
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:sync-1.0\"><exDate>2005-04-03T22:00:00.000Z</exDate></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());
    }

    @Test
    public void testDomainUpdateSyncNullExpiryDate() throws Exception {
        try {
            new SyncDomainUpdateCommandExtension(null);
            fail("Null exDate should have generated an exception");
        } catch (IllegalArgumentException iae) {
            assertEquals(ErrorPkg.getMessage("se.domain.update.sync.exDate.missing"),
                    iae.getMessage());
        }
    }

    @Test
    public void testDomainUpdateAddSingleIdnaVariant() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final DomainUpdateVariantCommandExtension extension =
                new DomainUpdateVariantCommandExtension();
        extension.addVariant(new IdnaDomainVariant("dnsform", "userform"));
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"><add><variant userForm=\"userform\">dnsform</variant></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());
    }

    @Test
    public void testDomainUpdateAddMultipleIdnaVariants() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();
        final IdnaDomainVariant[] variants =
                { new IdnaDomainVariant("dnsform1", "userform1"),
                        new IdnaDomainVariant("dnsform2", "userform2") };
        extension.addVariant(variants);
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"><add><variant userForm=\"userform1\">dnsform1</variant><variant userForm=\"userform2\">dnsform2</variant></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());
    }
    
    @Test
    public void testDomainUpdateRemoveSingleIdnaVariant() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final DomainUpdateVariantCommandExtension extension =
                new DomainUpdateVariantCommandExtension();

        extension.remVariant(new IdnaDomainVariant("dnsform", "userform"));
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"><rem><variant userForm=\"userform\">dnsform</variant></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());        
    }
    
    @Test
    public void testDomainUpdateRemoveMultipleIdnaVariants() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();
        final IdnaDomainVariant[] variants =
                { new IdnaDomainVariant("dnsform1", "userform1"),
                        new IdnaDomainVariant("dnsform2", "userform2") };
        extension.remVariant(variants);
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"><rem><variant userForm=\"userform1\">dnsform1</variant><variant userForm=\"userform2\">dnsform2</variant></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());
    }
    
    @Test
    public void testDomainUpdateAddAndRemoveIdnaVariants() throws Exception {
        final Command command = new DomainUpdateCommand("jtkutest.com.au");

        final DomainUpdateVariantCommandExtension extension =
                new DomainUpdateVariantCommandExtension();
        extension.addVariant(new IdnaDomainVariant("adddnsform", "adduserform"));
        extension.remVariant(new IdnaDomainVariant("remdnsform", "remuserform"));
        command.appendExtension(extension);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:variant-1.0\"><add><variant userForm=\"adduserform\">adddnsform</variant></add><rem><variant userForm=\"remuserform\">remdnsform</variant></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                command.toXML());        
    }
    
    @Test
    public void testDomainUpdateAddNullIdnaVariant() throws Exception {
        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();
        
        try {
            extension.addVariant((IdnaDomainVariant)null);
            fail("Should throw an IllegalArgumentException for null variant");
        } catch (IllegalArgumentException e) {
            assertEquals("Unexpected exception message",
                    ErrorPkg.getMessage("se.domain.update.idna.variant.missing"), e.getMessage());
        }
    }

    @Test
    public void testDomainUpdateRemoveNullIdnaVariant() throws Exception {
        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();
        
        try {
            extension.remVariant((IdnaDomainVariant)null);
            fail("Should throw an IllegalArgumentException for null variant");
        } catch (final IllegalArgumentException e) {
            assertEquals("Unexpected exception message",
                    ErrorPkg.getMessage("se.domain.update.idna.variant.missing"), e.getMessage());
        }
    }
    
    @Test
    public void testDomainUpdateAddIdnaVariantWithNullUserForm() throws Exception {
        assertIdnaVariantForAdd(new IdnaDomainVariant("dnsForm", null),
                "se.domain.update.idna.variant.userForm.missing");
    }

    @Test
    public void testDomainUpdateAddIdnaVariantWithNullDnsForm() throws Exception {
        assertIdnaVariantForAdd(new IdnaDomainVariant(null, "userForm"),
                "se.domain.update.idna.variant.name.missing");
    }

    @Test
    public void testDomainUpdateRemIdnaVariantWithNullUserForm() throws Exception {
        assertIdnaVariantForRem(new IdnaDomainVariant("dnsForm", null),
                "se.domain.update.idna.variant.userForm.missing");
    }

    @Test
    public void testDomainUpdateRemIdnaVariantWithNullDnsForm() throws Exception {
        assertIdnaVariantForRem(new IdnaDomainVariant(null, "userForm"),
                "se.domain.update.idna.variant.name.missing");
    }

    private void assertIdnaVariantForAdd(final IdnaDomainVariant variant,
            final String errorMessageProperty) {
        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();

        try {
            extension.addVariant(variant);
            fail("Should throw an IllegalArgumentException for null value");
        } catch (final IllegalArgumentException e) {
            assertEquals("Unexpected exception message", ErrorPkg.getMessage(errorMessageProperty),
                    e.getMessage());
        }
    }

    private void assertIdnaVariantForRem(final IdnaDomainVariant variant,
            final String errorMessageProperty) {
        final DomainUpdateVariantCommandExtension extension = new DomainUpdateVariantCommandExtension();

        try {
            extension.remVariant(variant);
            fail("Should throw an IllegalArgumentException for null value");
        } catch (final IllegalArgumentException e) {
            assertEquals("Unexpected exception message", ErrorPkg.getMessage(errorMessageProperty),
                    e.getMessage());
        }
    }
}
