package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;

public class SecDnsDomainUpdateCommandExtensionAddTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testSecDNSAllFieldsUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        final KeyData kd = new KeyData(256, 3, 1, "AQPJ////4Q==");
        dsData.setKeyData(kd);

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        addData.setMaxSigLife(maxSigLife);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><add><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSMandatoryFieldsUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><add><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSMandatoryFieldsNotUrgent() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(false); // explicitly setting it to false for the sake of
                              // testing
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSMaxSigLife() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(true);
        final DSData dsData = new DSData(12345, 3, 1, "49FD46E6C4B45C55D4AC");

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        addData.setMaxSigLife(maxSigLife);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\" urgent=\"true\"><add><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSMultipleDSData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        DSData dsData = new DSData(12345, 3, 1, "38FD46E6C4B45C55D4AC");

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType addData = new DSOrKeyType();
        addData.setMaxSigLife(maxSigLife);

        KeyData kd = new KeyData(256, 3, 1, "AQPJ////4Q==");
        dsData.setKeyData(kd);
        addData.addToDsData(dsData);

        dsData = new DSData(6789, 2, 2, "49FD46E6C4B45C55D4AC");
        addData.addToDsData(dsData);

        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><maxSigLife>604800</maxSigLife><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>38FD46E6C4B45C55D4AC</digest><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData><dsData><keyTag>6789</keyTag><alg>2</alg><digestType>2</digestType><digest>49FD46E6C4B45C55D4AC</digest></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSKeyData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        KeyData kd = new KeyData(256, 3, 1, "AQPJ////4Q==");

        MaxSigLifeType maxSigLife = new MaxSigLifeType();
        maxSigLife.setMaxSigLife(604800);

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToKeyData(kd);
        addData.setMaxSigLife(maxSigLife);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><maxSigLife>604800</maxSigLife><keyData><flags>256</flags><protocol>3</protocol><alg>1</alg><pubKey>AQPJ////4Q==</pubKey></keyData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

    @Test
    public void testSecDNSKeyDataNotAllowedWithDSData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(false); // explicitly setting it to false for the sake of testing
        final KeyData kd = new KeyData(0, 3, 5, "AQPJ////4Q==");
        DSData dsData = new DSData(12345, 3, 1, "38FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToKeyData(kd);
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        try {
            cmd.toXML();
            Assert.assertTrue("Should trigger a SAXParserException", false);
        } catch (SAXParseException spe) {
            Assert.assertTrue("Should trigger a SAXParserException with message about keyData", spe.getMessage().contains("element 'keyData'"));
        }
    }

    @Test
    public void testSecDNSKeyDataAllowedInDSData() throws Exception {
        final Command cmd = new DomainUpdateCommand("jtkutest.com.au", "jtkUT3st", null, null, "JTKCON");
        final SecDnsDomainUpdateCommandExtension ext = new SecDnsDomainUpdateCommandExtension();
        ext.setUrgent(false); // explicitly setting it to false for the sake of testing
        final KeyData kd = new KeyData(0, 3, 5, "AQPJ////4Q==");
        DSData dsData = new DSData(12345, 3, 1, "38FD46E6C4B45C55D4AC");
        dsData.setKeyData(kd);

        DSOrKeyType addData = new DSOrKeyType();
        addData.addToDsData(dsData);
        ext.setAddData(addData);

        cmd.appendExtension(ext);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.au</name><chg><registrant>JTKCON</registrant><authInfo><pw>jtkUT3st</pw></authInfo></chg></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:secDNS-1.1\"><add><dsData><keyTag>12345</keyTag><alg>3</alg><digestType>1</digestType><digest>38FD46E6C4B45C55D4AC</digest><keyData><flags>0</flags><protocol>3</protocol><alg>5</alg><pubKey>AQPJ////4Q==</pubKey></keyData></dsData></add></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                cmd.toXML());
    }

}
