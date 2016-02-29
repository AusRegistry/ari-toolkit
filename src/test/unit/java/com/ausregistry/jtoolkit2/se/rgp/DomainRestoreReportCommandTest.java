package com.ausregistry.jtoolkit2.se.rgp;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import org.junit.Before;
import org.junit.Test;

public class DomainRestoreReportCommandTest {

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainRestoreRequestWithMinimumParameters()
            throws Exception {
        String preData = "predata";
        String postData = "postdata";
        GregorianCalendar delTime = EPPDateFormatter
                .fromXSDateTime("2006-12-25T00:00:00.0Z");
        GregorianCalendar resTime = EPPDateFormatter
                .fromXSDateTime("2006-12-25T00:00:00.0Z");
        ReportTextElement resReason = new ReportTextElement("resreason", null);
        ReportTextElement statement = new ReportTextElement("resstatement",
                null);
        final DomainRestoreReportCommand cmd = new DomainRestoreReportCommand(
                "jtkutest.com.au", preData, postData, delTime, resTime,
                resReason, statement, null, null);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params"
                + ":xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-"
                + "1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest"
                + ".com.au</name><chg/></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\">"
                + "<restore op=\"report\"><report><preData>" + preData + "</preData><postData>" + postData
                + "</postData><delTime>" + EPPDateFormatter.toXSDateTime(delTime) + "</delTime><resTime>"
                + EPPDateFormatter.toXSDateTime(resTime) + "</resTime><resReason>" + resReason.getReportElement()
                + "</resReason><statement>" + statement.getReportElement() + "</statement></report>"
                + "</restore></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command></epp>", cmd.toXML());
    }

    @Test
    public void testDomainRestoreRequestWithAllParameters() throws Exception {
        String preData = "predata";
        String postData = "postdata";
        GregorianCalendar delTime = EPPDateFormatter
                .fromXSDateTime("2006-12-25T00:00:00.0Z");
        GregorianCalendar resTime = EPPDateFormatter
                .fromXSDateTime("2006-12-25T00:00:00.0Z");
        ReportTextElement resReason = new ReportTextElement("resreason", "lang");
        ReportTextElement statement = new ReportTextElement("resstatement",
                "lang");
        ReportTextElement secondStatement = new ReportTextElement(
                "resstatement2", "lang");
        String other = "other";
        final DomainRestoreReportCommand cmd = new DomainRestoreReportCommand(
                "jtkutest.com.au", preData, postData, delTime, resTime,
                resReason, statement, secondStatement, other);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params"
                + ":xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-"
                + "1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest"
                + ".com.au</name><chg/></update></update><extension><update xmlns=\"urn:ietf:params:xml:ns:rgp-1.0\">"
                + "<restore op=\"report\"><report><preData>" + preData + "</preData><postData>" + postData
                + "</postData><delTime>" + EPPDateFormatter.toXSDateTime(delTime) + "</delTime><resTime>"
                + EPPDateFormatter.toXSDateTime(resTime) + "</resTime><resReason lang=\"" + resReason.getLanguage()
                + "\">" + resReason.getReportElement() + "</resReason><statement lang=\"" + statement.getLanguage()
                + "\">" + statement.getReportElement() + "</statement><statement lang=\"" //
                + secondStatement.getLanguage()
                + "\">" + secondStatement.getReportElement() + "</statement><other>" + other + "</other></report>"
                + "</restore></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID>"
                + "</command></epp>", cmd.toXML());
    }
}
