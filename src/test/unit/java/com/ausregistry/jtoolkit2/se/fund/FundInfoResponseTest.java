package com.ausregistry.jtoolkit2.se.fund;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

public class FundInfoResponseTest {
    private FundInfoResponse response = new FundInfoResponse();
    private XMLParser parser = new XMLParser();


    @Test
    public void testEmptyFundList() throws Exception {
        String emptyFundListXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<fund:infData xmlns:fund=\"urn:ar:params:xml:ns:fund-1.0\" " +
                                        "xsi:schemaLocation=\"urn:ar:params:xml:ns:fund-1.0 fund-1.0.xsd\">" +
                            "</fund:infData>" +
                         "</resData>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        response.fromXML(parser.parse(emptyFundListXml));

        assertThat(response.getFunds().size(), is(0));
        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
    }

    @Test
    public void testAvailableAmounts() throws Exception {
        String emptyFundListXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<fund:infData xmlns:fund=\"urn:ar:params:xml:ns:fund-1.0\" " +
                                        "xsi:schemaLocation=\"urn:ar:params:xml:ns:fund-1.0 fund-1.0.xsd\">" +
                                "<fund:fund limit=\"true\">" +
                                    "<fund:id>r001</fund:id>" +
                                    "<fund:available>8000.00</fund:available>" +
                                "</fund:fund>" +
                                "<fund:fund limit=\"false\">" +
                                    "<fund:id>r002</fund:id>" +
                                "</fund:fund>" +
                                "<fund:fund limit=\"false\">" +
                                    "<fund:id>r003</fund:id>" +
                                    "<fund:available>-1000.00</fund:available>" +
                                "</fund:fund>" +
                            "</fund:infData>" +
                            "</resData>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        response.fromXML(parser.parse(emptyFundListXml));
        assertThat(response.getFunds().size(), is(3));

        assertThat(response.getFunds().get(0).getId(), is("r001"));
        assertThat(response.getFunds().get(0).getLimit(), is(true));
        assertThat(response.getFunds().get(0).getAvailable(), is(new BigDecimal("8000.00")));

        assertThat(response.getFunds().get(1).getId(), is("r002"));
        assertThat(response.getFunds().get(1).getLimit(), is(false));
        assertThat(response.getFunds().get(1).getAvailable(), is(nullValue()));

        assertThat(response.getFunds().get(2).getId(), is("r003"));
        assertThat(response.getFunds().get(2).getLimit(), is(false));
        assertThat(response.getFunds().get(2).getAvailable(), is(new BigDecimal("-1000.00")));


        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
    }

}
