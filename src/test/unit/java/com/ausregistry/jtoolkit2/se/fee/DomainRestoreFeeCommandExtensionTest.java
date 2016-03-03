package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainRenewCommand;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.unspec.DomainRenewCommandUnspecExtension;
import com.ausregistry.jtoolkit2.se.unspec.RestoreReasonCode;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DomainRestoreFeeCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtensionWithRestoreFeeOnly() throws SAXException {
        String domainName = "domain.example";
        Command cmd = new DomainRenewCommand(domainName,
                EPPDateFormatter.fromXSDateTime("2016-02-14T00:00:02.0Z"),
                new Period(1));

        DomainRenewCommandUnspecExtension restoreExt =
                new DomainRenewCommandUnspecExtension(RestoreReasonCode.RegistrantError,
                        "_My_Comment_", true, true);

        final DomainRestoreFeeCommandExtension feeExt =
                new DomainRestoreFeeCommandExtension(null, BigDecimal.valueOf(60.00), "USD");

        try {
            cmd.appendExtension(restoreExt);
            cmd.appendExtension(feeExt);
            String expectedXml = getRestoreResponseExpectedXml(domainName, true, true, false);
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtensionWithRenewFeeOnly() throws SAXException {
        String domainName = "domain.example";
        Command cmd = new DomainRenewCommand(domainName,
                EPPDateFormatter.fromXSDateTime("2016-02-14T00:00:02.0Z"),
                new Period(1));

        DomainRenewCommandUnspecExtension restoreExt =
                new DomainRenewCommandUnspecExtension(RestoreReasonCode.RegistrantError,
                        "_My_Comment_", true, true);

        final DomainRestoreFeeCommandExtension feeExt =
                new DomainRestoreFeeCommandExtension(BigDecimal.valueOf(10.00), null, "USD");

        try {
            cmd.appendExtension(restoreExt);
            cmd.appendExtension(feeExt);
            String expectedXml = getRestoreResponseExpectedXml(domainName, true, false, true);
            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtensionWithRestoreAndRenewFees() throws SAXException {
        String domainName = "domain.example";
        Command cmd = new DomainRenewCommand(domainName,
                EPPDateFormatter.fromXSDateTime("2016-02-14T00:00:02.0Z"),
                new Period(1));

        DomainRenewCommandUnspecExtension restoreExt =
                new DomainRenewCommandUnspecExtension(RestoreReasonCode.RegistrantError,
                        "_My_Comment_", true, true);

        final DomainRestoreFeeCommandExtension feeExt =
                new DomainRestoreFeeCommandExtension(BigDecimal.valueOf(10.00), BigDecimal.valueOf(60.00), "USD");

        try {
            cmd.appendExtension(restoreExt);
            cmd.appendExtension(feeExt);
            String expectedXml = getRestoreResponseExpectedXml(domainName, true, true, true);

            assertEquals(expectedXml, cmd.toXML());
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    private static String getRestoreResponseExpectedXml(final String domainName,
                                                        boolean feeExtension,
                                                        boolean withRestore,
                                                        boolean withRenew) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"");
        result.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">");
        result.append("<command>");
        result.append("<renew>");
        result.append("<renew xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"");
        result.append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">");
        result.append("<name>" + domainName + "</name>");
        result.append("<curExpDate>2016-02-14</curExpDate>");
        result.append("<period unit=\"y\">1</period>");
        result.append("</renew>");
        result.append("</renew>");
        result.append("<extension>");
        result.append("<extension xmlns=\"urn:ietf:params:xml:ns:neulevel-1.0\">");
        result.append("<unspec>");
        result.append("RestoreReasonCode=1 RestoreComment=_My_Comment_ TrueData=Y ValidUse=Y");
        result.append("</unspec>");
        result.append("</extension>");
        if (feeExtension) {
            result.append("<renew xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">");
            result.append("<currency>USD</currency>");
            if (withRestore) {
                result.append("<fee description=\"Restore Fee\">60.00</fee>");
            }
            if (withRenew) {
                result.append("<fee description=\"Renewal Fee\">10.00</fee>");
            }
            result.append("</renew>");
        }
        result.append("</extension>");
        result.append("<clTRID>JTKUTEST.20070101.010101.0</clTRID>");
        result.append("</command>");
        result.append("</epp>");
        return result.toString();
    }

}