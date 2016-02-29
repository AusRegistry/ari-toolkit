package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;

public class SecDnsDomainCreateCommandExtensionAddTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testSecDNSAllFields() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        final KeyData kd = new KeyData(256, 3, 1, "AQPJ////4Q==");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType createData = new DSOrKeyType();
        createData.addToDsData(dsData);
        createData.setMaxSigLife(maxSigLife);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                     cmd.toXML());
    }

    @Test
    public void testSecDNSMultipleDSData() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();
        DSData dsData = new DSData(12345, 3, 1, "38FD46E6C4B45C55D4AC");
        KeyData kd = new KeyData(256, 3, 1, "AQPJ////4Q==");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType createData = new DSOrKeyType();
        createData.setMaxSigLife(maxSigLife);
        createData.addToDsData(dsData);

        dsData = new DSData(6789, 2, 2, "49FD46E6C4B45C55D4AC");
        createData.addToDsData(dsData);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>38FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData><dsData><keyTag>6789</keyTag><alg>2</alg><digestType>2</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSAllFieldsMin() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();
        final DSData dsData = new DSData(0, 0, 0, "49FD46E6C4B45C55D4AC");

        final KeyData kd = new KeyData(0, 0, 0, "AQPJ////4Q==");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(1);

        DSOrKeyType createData = new DSOrKeyType();
        createData.addToDsData(dsData);
        createData.setMaxSigLife(maxSigLife);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>1</maxSigLife><dsData><keyTag>0</keyTag><alg>0</alg><digestType>0</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>0</flags><protocol>0</protocol><alg>0</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSAllFieldsMax() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");

        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(2147483647);

        DSOrKeyType createData = new DSOrKeyType();
        createData.addToDsData(dsData);
        createData.setMaxSigLife(maxSigLife);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>2147483647</maxSigLife><dsData><keyTag>65535</keyTag><alg>255</alg><digestType>255</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSJustKeyData() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();

        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(65535);

        DSOrKeyType createData = new DSOrKeyType();
        createData.addToKeyData(kd);
        createData.setMaxSigLife(maxSigLife);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><maxSigLife>65535</maxSigLife><keyData><flags>65535</flags><protocol>255</protocol><alg>255</alg><pubKey>AQPJ////4Q==</pubKey></keyData></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
            cmd.toXML());
    }

    @Test
    public void testSecDNSDsDataAndKeyData() throws Exception {
        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();

        final KeyData kd = new KeyData(65535, 255, 255, "AQPJ////4Q==");
        final DSData dsData = new DSData(65535, 255, 255, "49FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(65535);

        DSOrKeyType createData = new DSOrKeyType();
        createData.addToKeyData(kd);
        createData.addToDsData(dsData);
        createData.setMaxSigLife(maxSigLife);
        ext.setCreateData(createData);

        cmd.appendExtension(ext);
        try {
            cmd.toXML();
            Assert.assertTrue("Should trigger a SAXParserException", false);
        } catch (Exception spe) {
            Assert.assertTrue("Should trigger a SAXParserException with message about keyData", spe.getMessage().contains("element 'keyData'"));
        }
    }
}
