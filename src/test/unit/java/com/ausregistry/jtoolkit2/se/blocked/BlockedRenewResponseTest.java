package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BlockedRenewResponseTest {
    private BlockedRenewResponse response = new BlockedRenewResponse();
    private XMLParser parser = new XMLParser();

    @Test
    public void shouldReturnResponseDataForRenew() throws Exception {
        String id = "BD-001";
        String blockedDomainRenewXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<blocked:renData xmlns:blocked=\"urn:X-ar:params:xml:ns:blocked-1.0\" " +
                                "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\">" +
                                "<blocked:id>" + id + "</blocked:id>" +
                                "<blocked:exDate>2007-02-09T15:44:58.0Z</blocked:exDate>" +
                            "</blocked:renData>" +
                        "</resData>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        response.fromXML(parser.parse(blockedDomainRenewXml));

        assertThat(response.getId(), is(id));
        assertThat(response.getExDate(), is(EPPDateFormatter.fromXSDateTime("2007-02-09T15:44:58.0Z")));

        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
    }
}