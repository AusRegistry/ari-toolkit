package com.ausregistry.jtoolkit2.se.block;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainCreateBlockResponseExtensionTest {
    private XMLParser parser = new XMLParser();

    @Test
    public void testResponse() throws Exception {
        String id = "BD-001";
        String blockCreateXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                                "<domain:name>example.com</domain:name>" +
                                "<domain:crDate>1999-04-03T22:00:00.0Z</domain:crDate>" +
                                "<domain:exDate>2001-04-03T22:00:00.0Z</domain:exDate>" +
                            "</domain:creData>" +
                        "</resData>" +
                        "<extension>" +
                            "<block:creData xmlns:block=\"urn:ar:params:xml:ns:block-1.0\" " +
                                "xsi:schemaLocation=\"urn:ar:params:xml:ns:block-1.0 block-1.0.xsd\">" +
                            "<block:id>" + id + "</block:id>" +
                            "</block:creData>" +
                        "</extension>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        final DomainCreateBlockResponseExtension blockExtension = new DomainCreateBlockResponseExtension();
        DomainCreateResponse response = new DomainCreateResponse();
        response.registerExtension(blockExtension);
        response.fromXML(parser.parse(blockCreateXml));

        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
        assertThat(blockExtension.getId(), is(id));
    }
}