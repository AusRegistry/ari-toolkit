package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.DomainCheckResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

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

        List<FeeCheckData> feeDomains = feeCheckExtension.getFeeDomains();
        assertThat(feeDomains, hasSize(1));

        FeeCheckData fee = feeDomains.get(0);
        assertThat(fee.getName(), is(DOMAIN_NAME));
        assertThat(fee.getCurrency(), is("USD"));
        assertThat(fee.getCommand().getName(), is("create"));
        assertThat(fee.getCommand().getPhase(), is("sunrise"));
        assertThat(fee.getPeriod().getPeriod(), is(1));
        assertThat(fee.getPeriod().getUnit().toString(), is("y"));
        assertThat(fee.getFees(), hasSize(2));
        assertThat(fee.getFees().get(0).getDescription(), is("Application Fee"));
        assertThat(fee.getFees().get(0).getFee(), is(new BigDecimal("5.00")));
        assertThat(fee.getFees().get(0).getRefundable(), is(false));
        assertThat(fee.getFees().get(1).getDescription(), is("Registration Fee"));
        assertThat(fee.getFees().get(1).getFee(), is(new BigDecimal("5.00")));
        assertThat(fee.getFees().get(1).getRefundable(), is(true));
        assertThat(fee.getFeeClass(), is("premium-tier1"));

    }

    private String generateExpectedXml(String fee) {
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
                fee +
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

    private String getCreateResponseExpectedXml() {
        String fee = "<fee description=\"Application Fee\" refundable=\"0\">5.00</fee>" +
                "<fee description=\"Registration Fee\" refundable=\"1\">5.00</fee>";
        return generateExpectedXml(fee);
    }

    private String getCreateResponseExpectedXmlWithoutFee() {
        return generateExpectedXml("");
    }

    @Test
    public void shouldGetDomainCheckFeeExtensionWhenFeeIsNotAvailable() throws Exception {

        final DomainCheckResponse response = new DomainCheckResponse();
        final DomainCheckFeeResponseExtension feeCheckExtension =  new DomainCheckFeeResponseExtension();
        final XMLDocument doc = PARSER.parse(getCreateResponseExpectedXmlWithoutFee());
        response.registerExtension(feeCheckExtension);
        response.fromXML(doc);

        List<FeeCheckData> feeDomains = feeCheckExtension.getFeeDomains();
        assertThat(feeDomains, hasSize(1));
        FeeCheckData feeCheckData = feeDomains.get(0);
        assertThat(feeCheckData.getFees(), hasSize(0));
    }

}