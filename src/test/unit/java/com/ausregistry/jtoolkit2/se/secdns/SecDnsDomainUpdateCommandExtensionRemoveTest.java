package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

public class SecDnsDomainUpdateCommandExtensionRemoveTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }


    @Test
    public void testSecDNSRemoveMultipleNotUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(false); // explicitly setting it to false for the sake of testing

        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);

        RemType remData = new RemType();
        remData.addToDsData(dsData);
        ext.setRemData(remData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><rem><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSRemoveMultipleEvents() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(false); // explicitly setting it to false for the sake of testing

        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);

        RemType remData = new RemType();
        remData.addToDsData(dsData);
        ext.setRemData(remData);

        cmd.appendExtension(ext);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><rem><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSRemoveSingleUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        RemType remData = new RemType();
        remData.addToKeyData(kd);
        ext.setRemData(remData);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSRemoveAll() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        RemType remData = new RemType();
        remData.setRemoveAll(true);
        ext.setRemData(remData);
        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSRemoveAllWithKeyData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        RemType remData = new RemType();
        remData.setRemoveAll(true);
        remData.addToKeyData(kd);
        ext.setRemData(remData);
        cmd.appendExtension(ext);
        try {
            cmd.toXML();
            Assert.assertTrue("Should trigger a SAXParserException", false);
        } catch (SAXParseException spe) {
            Assert.assertTrue("Should trigger a SAXParserException", spe.getMessage().contains("element 'keyData'"));
        }
    }

    @Test
    public void testSecDNSRemoveKeyDataAndDsData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);
        RemType remData = new RemType();
        remData.addToKeyData(kd);
        remData.addToDsData(dsData);
        ext.setRemData(remData);
        cmd.appendExtension(ext);
        try {
            cmd.toXML();
            Assert.assertTrue("Should trigger a SAXParserException", false);
        } catch (SAXParseException spe) {
            Assert.assertTrue("Should trigger a SAXParserException with message about keyData", spe.getMessage().contains("element 'keyData'"));
        }
    }

    @Test
    public void testSecDNSRemoveAllWithDsData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);
        RemType remData = new RemType();
        remData.addToKeyData(kd);
        remData.addToDsData(dsData);
        remData.setRemoveAll(true);
        ext.setRemData(remData);
        cmd.appendExtension(ext);
        try {
            cmd.toXML();
            Assert.assertTrue("Should trigger a SAXParserException", false);
        } catch (SAXParseException spe) {
            Assert.assertTrue("Should trigger a SAXParserException with message about dsData", spe.getMessage().contains("element 'dsData'"));
        }
    }

    @Test
    public void testSecDNSRemoveAllAndAddDsData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);

        RemType remData = new RemType();
        remData.setRemoveAll(true);
        ext.setRemData(remData);

        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);

        assertEquals(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem><add><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSRemoveAllAndAddKeyData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st",
                null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);

        RemType remData = new RemType();
        remData.setRemoveAll(true);
        ext.setRemData(remData);

        final KeyData keyData = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        DSOrKeyType addData = new DSOrKeyType();
        addData.addToKeyData(keyData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);

        assertEquals(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><rem><all>true</all></rem><add><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }
}
