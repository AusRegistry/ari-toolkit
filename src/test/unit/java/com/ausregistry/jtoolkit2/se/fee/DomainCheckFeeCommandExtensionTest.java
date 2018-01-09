package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class DomainCheckFeeCommandExtensionTest {

    public static final String DOMAIN_NAME = "jtkutest.com.au";
    public static final String CURRENCY = "AUD";
    public static final String COMMAND = "CREATE";
    public static final String PHASE = "claims";
    public static final int NUMBER_OF_YEARS = 1;
    public static final String SUBPHASE = "landrush";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtension() throws SAXException {

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        command.setSubphase(SUBPHASE);
        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, command);
        feeCheckData.setCurrency(CURRENCY);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));

        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));

        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command>" +
                    "<check>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                    "<name>jtkutest.com.au</name>" +
                    "</check>" +
                    "</check>" +
                    "<extension>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                    "<domain>" +
                    "<name>" + DOMAIN_NAME + "</name>" +
                    "<currency>" + CURRENCY + "</currency>" +
                    "<command phase=\"" + PHASE + "\" subphase=\"" + SUBPHASE + "\">" + COMMAND + "</command>" +
                    "<period unit=\"y\">" + NUMBER_OF_YEARS + "</period>" +
                    "</domain>" +
                    "</check>" +
                    "</extension>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID>" +
                    "</command>" +
                    "</epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenSubphaseNotProvided() throws SAXException {

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, command);
        feeCheckData.setCurrency(CURRENCY);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));

        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));

        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command>" +
                    "<check>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                    "<name>jtkutest.com.au</name>" +
                    "</check>" +
                    "</check>" +
                    "<extension>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                    "<domain>" +
                    "<name>" + DOMAIN_NAME + "</name>" +
                    "<currency>" + CURRENCY + "</currency>" +
                    "<command phase=\"" + PHASE + "\">" + COMMAND + "</command>" +
                    "<period unit=\"y\">" + NUMBER_OF_YEARS + "</period>" +
                    "</domain>" +
                    "</check>" +
                    "</extension>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID>" +
                    "</command>" +
                    "</epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenCurrencyProvided() throws SAXException {

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        command.setSubphase(SUBPHASE);
        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, command);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));

        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));
        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command>" +
                    "<check>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                    "<name>jtkutest.com.au</name>" +
                    "</check>" +
                    "</check>" +
                    "<extension>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                    "<domain>" +
                    "<name>" + DOMAIN_NAME + "</name>" +
                    "<command phase=\"" + PHASE + "\" subphase=\"" + SUBPHASE + "\">" + COMMAND + "</command>" +
                    "<period unit=\"y\">" + NUMBER_OF_YEARS + "</period>" +
                    "</domain>" +
                    "</check>" +
                    "</extension>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID>" +
                    "</command>" +
                    "</epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void failWhenNameNotProvided() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'name' is required.");

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        command.setSubphase(SUBPHASE);
        FeeCheckData feeCheckData = new FeeCheckData(null, command);
        feeCheckData.setCurrency(CURRENCY);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));
        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));

        cmd.appendExtension(ext);
        cmd.toXML();
    }

    @Test
    public void failWhenCommandNotProvided() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field 'command' is required.");

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, null);
        feeCheckData.setCurrency(CURRENCY);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));
        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));
        cmd.appendExtension(ext);
        cmd.toXML();
    }

    @Test
    public void shouldCreateValidXmlWhenNoPeriodSupplied() throws SAXException {

        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        command.setSubphase(SUBPHASE);
        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, command);
        feeCheckData.setCurrency(CURRENCY);
        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));
        try {
            cmd.appendExtension(ext);
            String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<command>" +
                    "<check>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                    " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                    "<name>jtkutest.com.au</name>" +
                    "</check>" +
                    "</check>" +
                    "<extension>" +
                    "<check xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                    "<domain>" +
                    "<name>" + DOMAIN_NAME + "</name>" +
                    "<currency>" + CURRENCY + "</currency>" +
                    "<command phase=\"" + PHASE + "\" subphase=\"" + SUBPHASE + "\">" + COMMAND + "</command>" +
                    "</domain>" +
                    "</check>" +
                    "</extension>" +
                    "<clTRID>JTKUTEST.20070101.010101.0</clTRID>" +
                    "</command>" +
                    "</epp>";

            assertEquals(expectedXml, cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenFeeExtensionSuppliedAndRequireNamespacePrefix() {
        final Command cmd = new DomainCheckCommand(DOMAIN_NAME);

        FeeCheckData.Command command = new FeeCheckData.Command(COMMAND);
        command.setPhase(PHASE);
        command.setSubphase(SUBPHASE);
        FeeCheckData feeCheckData = new FeeCheckData(DOMAIN_NAME, command);
        feeCheckData.setCurrency(CURRENCY);
        feeCheckData.setPeriod(new Period(NUMBER_OF_YEARS));

        final DomainCheckFeeCommandExtension ext =
                new DomainCheckFeeCommandExtension(Collections.singletonList(feeCheckData));

        final String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<e:epp xmlns:e=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                + "<e:command>"
                + "<e:check>"
                + "<domain:check xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\""
                + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                + "<domain:name>" + DOMAIN_NAME + "</domain:name>"
                + "</domain:check>"
                + "</e:check>"
                + "<e:extension>"
                + "<fee:check xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">"
                + "<fee:domain>"
                + "<fee:name>" + DOMAIN_NAME + "</fee:name>"
                + "<fee:currency>" + CURRENCY + "</fee:currency>"
                + "<fee:command phase=\"" + PHASE + "\" subphase=\"" + SUBPHASE + "\">" + COMMAND + "</fee:command>"
                + "<fee:period unit=\"y\">" + NUMBER_OF_YEARS + "</fee:period>"
                + "</fee:domain>"
                + "</fee:check>"
                + "</e:extension>"
                + "<e:clTRID>JTKUTEST.20070101.010101.0</e:clTRID>"
                + "</e:command>"
                + "</e:epp>";
        try {
            cmd.appendExtension(ext);
            assertEquals(expectedXml, cmd.toXML(XmlOutputConfig.prefixAllNamespaceConfig()));

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

}