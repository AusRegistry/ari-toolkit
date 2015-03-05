package com.ausregistry.jtoolkit2.se.block;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainInfoBlockResponseExtensionTest {
    private XMLParser parser = new XMLParser();

    @Test
    public void testResponse() throws Exception {
        String id = "BD-001";
        String blockInfoXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"" +
                                " xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">" +
                                "<name>example.com</name>" +
                                "<roid>D0000003-AR</roid>" +
                                "<registrant>EXAMPLE</registrant>" +
                                "<clID>Registrar</clID>" +
                                "<crDate>2006-02-09T15:44:58.0Z</crDate>" +
                                "<exDate>2008-02-10T00:00:00.0Z</exDate>" +
                            "</infData>" +
                        "</resData>" +
                        "<extension>" +
                            "<block:infData xmlns:block=\"urn:ar:params:xml:ns:block-1.0\" " +
                                "xsi:schemaLocation=\"urn:ar:params:xml:ns:block-1.0 block-1.0.xsd\">" +
                            "<block:id>" + id + "</block:id>" +
                            "</block:infData>" +
                        "</extension>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        final DomainInfoBlockResponseExtension blockExtension = new DomainInfoBlockResponseExtension();
        DomainInfoResponse response = new DomainInfoResponse();
        response.registerExtension(blockExtension);
        response.fromXML(parser.parse(blockInfoXml));

        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
        assertThat(blockExtension.getId(), is(id));
    }
}