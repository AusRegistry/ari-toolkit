package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.DomainCheckResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DomainCheckFeeResponseExtensionTest {
    private static final XMLParser PARSER = new XMLParser();
    private static final String DOMAIN_NAME = "jtkutest.com.au";

    @Test
    public void shouldGetDomainCheckFeeExtension() throws Exception {

        final DomainCheckResponse response = new DomainCheckResponse();
        final DomainCheckFeeResponseExtension feeCheckExtension =  new DomainCheckFeeResponseExtension();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXml());
        response.registerExtension(feeCheckExtension);
        response.fromXML(doc);

        Map<String, FeeCheckData> fee = feeCheckExtension.getFeeDomains();
        assertThat(fee.get(DOMAIN_NAME).getName(), is(DOMAIN_NAME));
        assertThat(fee.get(DOMAIN_NAME).getCurrency(), is("USD"));
        assertThat(fee.get(DOMAIN_NAME).getCommand().getName(), is("create"));
        assertThat(fee.get(DOMAIN_NAME).getCommand().getPhase(), is("sunrise"));
        assertThat(fee.get(DOMAIN_NAME).getPeriod().getPeriod(), is(1));
        assertThat(fee.get(DOMAIN_NAME).getPeriod().getUnit().toString(), is("y"));
        assertThat(fee.get(DOMAIN_NAME).getFees(), hasSize(2));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(0).getDescription(), is("Application Fee"));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(0).getFee(), is(new BigDecimal("5.00")));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(0).getRefundable(), is(false));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(1).getDescription(), is("Registration Fee"));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(1).getFee(), is(new BigDecimal("5.00")));
        assertThat(fee.get(DOMAIN_NAME).getFees().get(1).getRefundable(), is(true));
        assertThat(fee.get(DOMAIN_NAME).getFeeClass(), is("premium-tier1"));

    }

    private String getCreateResponseExpectedXml() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">" +
                "<response>" +
                "<result code=\"1000\">" +
                "<msg>Command completed successfully</msg>" +
                "</result>" +
                "<resData>" +
                "<chkData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\">" +
                "<cd>" +
                "<name avail=\"1\">" + DOMAIN_NAME + "</name>" +
                "</cd>" +
                "</chkData>" +
                "</resData>" +
                "<extension>" +
                "<chkData xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                "<cd>" +
                "<name>" + DOMAIN_NAME + "</name>" +
                "<currency>USD</currency>" +
                "<command phase=\"sunrise\">create</command>" +
                "<period unit=\"y\">1</period>" +
                "<fee description=\"Application Fee\" refundable=\"0\">5.00</fee>" +
                "<fee description=\"Registration Fee\" refundable=\"1\">5.00</fee>" +
                "<class>premium-tier1</class>" +
                "</cd>" +
                "</chkData>" +
                "</extension>" +
                "<trID>" +
                "<clTRID>ABC-12345</clTRID>" +
                "<svTRID>54322-XYZ</svTRID>" +
                "</trID>" +
                "</response>" +
                "</epp>";
    }


}