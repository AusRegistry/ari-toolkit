package com.ausregistry.jtoolkit2.se.idn.ietf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;


public class DomainInfoIetfIdnResponseExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetIdnName() throws Exception {
        final String domainName = "test.com";
        final String uname = "espa&#xF1;ol.example.com";
        final String table = "es";

        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoIetfIdnResponseExtension re = new DomainInfoIetfIdnResponseExtension();

        final XMLDocument doc =
                PARSER.parse(getInfoIdnResponseExpectedXml(domainName, table, uname));
        response.registerExtension(re);
        response.fromXML(doc);

        assertTrue("IDN extension should have been initialised", re.isInitialised());
        assertEquals(domainName, response.getName());
        assertEquals(table, re.getTable());
        assertEquals("español.example.com", re.getUname());
    }


    private static String getInfoIdnResponseExpectedXml(final String domainName, final String table,
                                                        final String uname) {
        final StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"")
                .append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
                .append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">")
                .append("<response>")
                .append("<result code=\"1000\">")
                .append("<msg>Command completed successfully</msg>")
                .append("</result>")
                .append("<resData>")
                .append("<infData xmlns=\"urn:ietf:params:xml:ns:domain-1.0\"")
                .append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">")
                .append("<name>").append(domainName).append("</name>")
                .append("<roid>D0000003-AR</roid>")
                .append("<status s=\"ok\" lang=\"en\"/>")
                .append("<registrant>EXAMPLE</registrant>")
                .append("<contact type=\"tech\">EXAMPLE</contact>")
                .append("<ns>")
                .append("<hostObj>ns1.example.com.au</hostObj>")
                .append("<hostObj>ns2.example.com.au</hostObj>")
                .append("</ns>")
                .append("<host>ns1.example.com.au</host>")
                .append("<host>ns2.exmaple.com.au</host>")
                .append("<clID>Registrar</clID>")
                .append("<crID>Registrar</crID>")
                .append("<crDate>2006-02-09T15:44:58.0Z</crDate>")
                .append("<exDate>2008-02-10T00:00:00.0Z</exDate>")
                .append("<authInfo>")
                .append("<pw>0192pqow</pw>")
                .append("</authInfo>")
                .append("</infData>")
                .append("</resData>");

        result.append("<extension>")
                .append("<data xmlns=\"urn:ietf:params:xml:ns:idn-1.0\">")
                .append("<table>").append(table).append("</table>")
                .append("<uname>").append(uname).append("</uname>")
                .append("</data>")
                .append("</extension>");

        result.append("<trID>")
                .append("<clTRID>ABC-12345</clTRID>")
                .append("<svTRID>54321-XYZ</svTRID>")
                .append("</trID>")
                .append("</response>")
                .append("</epp>");
        return result.toString();
    }
}