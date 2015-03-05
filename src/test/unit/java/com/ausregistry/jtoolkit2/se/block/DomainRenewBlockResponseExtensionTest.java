package com.ausregistry.jtoolkit2.se.block;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ausregistry.jtoolkit2.se.DomainRenewResponse;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainRenewBlockResponseExtensionTest {
    private XMLParser parser = new XMLParser();

    @Test
    public void shouldReturnResponseDataForRenew() throws Exception {
        String id = "BD-001";
        String blockRenewXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<domain:renData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" " +
                                "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                                "<domain:name>example.com</domain:name>" +
                                "<domain:exDate>2005-04-03T22:00:00.0Z</domain:exDate>" +
                            "</domain:renData>" +
                        "</resData>" +
                        "<extension>" +
                            "<block:renData xmlns:block=\"urn:ar:params:xml:ns:block-1.0\" " +
                                "xsi:schemaLocation=\"urn:ar:params:xml:ns:block-1.0 block-1.0.xsd\">" +
                                "<block:id>" + id + "</block:id>" +
                            "</block:renData>" +
                        "</extension>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        DomainRenewBlockResponseExtension blockExtension = new DomainRenewBlockResponseExtension();
        DomainRenewResponse response = new DomainRenewResponse();
        response.registerExtension(blockExtension);
        response.fromXML(parser.parse(blockRenewXml));

        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
        assertThat(blockExtension.getId(), is(id));
    }
}