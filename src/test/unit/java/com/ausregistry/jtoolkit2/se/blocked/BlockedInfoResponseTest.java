package com.ausregistry.jtoolkit2.se.blocked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class BlockedInfoResponseTest {
    private BlockedInfoResponse response = new BlockedInfoResponse();
    private XMLParser parser = new XMLParser();

    @Test
    public void testResponse() throws Exception {
        String id = UUID.randomUUID().toString();
        String blockedDomainInfoXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                    "<response>" +
                        "<result code=\"1000\">" +
                            "<msg>Command completed successfully</msg>" +
                        "</result>" +
                        "<resData>" +
                            "<blocked:infData xmlns:blocked=\"urn:X-ar:params:xml:ns:blocked-1.0\" " +
                                "xsi:schemaLocation=\"urn:X-ar:params:xml:ns:blocked-1.0 blocked-1.0.xsd\">" +
                                "<blocked:id>" + id + "</blocked:id>" +
                                "<blocked:name>example.com.au</blocked:name>" +
                                "<blocked:registrant>EXAMPLE</blocked:registrant>" +
                                "<blocked:clID>Registrar</blocked:clID>" +
                                "<blocked:crDate>2006-02-09T15:44:58.0Z</blocked:crDate>" +
                                "<blocked:exDate>2007-02-09T15:44:58.0Z</blocked:exDate>" +
                            "</blocked:infData>" +
                        "</resData>" +
                        "<trID><clTRID>ABC-12345</clTRID><svTRID>54322-XYZ</svTRID></trID>" +
                    "</response>" +
                "</epp>";
        response.fromXML(parser.parse(blockedDomainInfoXml));
        final BlockedDomain blockedDomain = response.getBlockedDomain();
        assertThat(blockedDomain.getId(), is(id));
        assertThat(blockedDomain.getDomainName(), is("example.com.au"));
        assertThat(blockedDomain.getRegistrantContactId(), is("EXAMPLE"));
        assertThat(blockedDomain.getClID(), is("Registrar"));
        assertThat(blockedDomain.getCrDate(), is(EPPDateFormatter.fromXSDateTime("2006-02-09T15:44:58.0Z")));
        assertThat(blockedDomain.getExDate(), is(EPPDateFormatter.fromXSDateTime("2007-02-09T15:44:58.0Z")));

        assertThat(response.getResults()[0].getResultCode(), is(1000));
        assertThat(response.getResults()[0].getResultMessage(), is("Command completed successfully"));
    }
}