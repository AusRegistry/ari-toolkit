package com.ausregistry.jtoolkit2.se.generic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

public class DomainInfoKVResponseExtensionTest {

    private static XMLParser xmlParser;

    @BeforeClass
    public static void setUp() throws Exception {
        xmlParser = new XMLParser();
    }

    @Test
    public void testNonExistentListName() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoKVResponseExtension kvExtension = new DomainInfoKVResponseExtension();
        response.registerExtension(kvExtension);

        final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"kvListTwo\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

        final XMLDocument doc = xmlParser.parse(xml);
        response.fromXML(doc);

        assertTrue(kvExtension.isInitialised());
        assertNull(kvExtension.getItem("nonExistentListName", "registrantIDValue"));
    }

    @Test
    public void testNonExistentItem() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoKVResponseExtension kvExtension = new DomainInfoKVResponseExtension();
        response.registerExtension(kvExtension);

        final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"kvListTwo\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";

        final XMLDocument doc = xmlParser.parse(xml);
        response.fromXML(doc);

        assertTrue(kvExtension.isInitialised());
        assertNull(kvExtension.getItem("kvListTwo", "nonExistentItem"));
    }

    @Test
    public void testNotInitialisedWhenNoKVExtensionPresent() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoKVResponseExtension kvExtension = new DomainInfoKVResponseExtension();
        response.registerExtension(kvExtension);

        final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg lang=\"en\">Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>viewdomain.registrar.au</domain:name><domain:roid>D604E5509E82DF9F3105B7E182BB4DF28-AR</domain:roid><domain:status s=\"inactive\"/><domain:registrant>CON-1</domain:registrant><domain:contact type=\"tech\">CON-1</domain:contact><domain:clID>EPP</domain:clID><domain:crID>EPP</domain:crID><domain:crDate>2010-08-12T00:33:20.0Z</domain:crDate><domain:exDate>2012-08-12T00:33:21.0Z</domain:exDate><domain:authInfo><domain:pw>123paSSword</domain:pw></domain:authInfo></domain:infData></resData><trID><clTRID>TESTER1.20100812.003321.1</clTRID><svTRID>9223372036854778924</svTRID></trID></response></epp>";

        final XMLDocument doc = xmlParser.parse(xml);
        response.fromXML(doc);

        assertFalse(kvExtension.isInitialised());
    }


    @Test
    public void testSingleKVList() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoKVResponseExtension kvExtension = new DomainInfoKVResponseExtension();
        response.registerExtension(kvExtension);

        final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"kvList\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";
        parseXMLIntoResponse(response, xml);

        checkKeyValueList(kvExtension, "kvList");
    }

    @Test
    public void testMultipleKVList() throws Exception {
        final DomainInfoResponse response = new DomainInfoResponse();
        final DomainInfoKVResponseExtension kvExtension = new DomainInfoKVResponseExtension();
        response.registerExtension(kvExtension);

        final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone='no'?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1000\"><msg>Command completed successfully</msg></result><resData><domain:infData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>example.com.ae</domain:name><domain:roid>D0000003-AR</domain:roid><domain:status s=\"ok\" lang=\"en\"/><domain:registrant>EXAMPLE</domain:registrant><domain:contact type=\"tech\">EXAMPLE</domain:contact><domain:ns><domain:hostObj>ns1.example.com.ae</domain:hostObj><domain:hostObj>ns2.example.com.ae</domain:hostObj></domain:ns><domain:host>ns1.example.com.ae</domain:host><domain:host>ns2.exmaple.com.ae</domain:host><domain:clID>Registrar</domain:clID><domain:crID>Registrar</domain:crID><domain:crDate>2006-02-09T15:44:58.0Z</domain:crDate><domain:exDate>2008-02-10T00:00:00.0Z</domain:exDate><domain:authInfo><domain:pw>0192pqow</domain:pw></domain:authInfo></domain:infData></resData><extension><kv:infData xmlns:kv=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kv:kvlist name=\"kvListOne\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist><kv:kvlist name=\"kvListTwo\"><kv:item key = \"registrantName\">RegistrantName Pty. Ltd.</kv:item><kv:item key=\"registrantIDType\">Trade License</kv:item><kv:item key=\"registrantIDValue\">123456789</kv:item><kv:item key=\"eligibilityType\">Trademark</kv:item><kv:item key=\"eligibilityName\">Registrant Eligi</kv:item><kv:item key=\"eligibilityIDType\">Trademark</kv:item><kv:item key=\"eligibilityIDValue\">987654321</kv:item><kv:item key=\"policyReason\">2</kv:item></kv:kvlist></kv:infData></extension><trID><clTRID>ABC-12345</clTRID><svTRID>805</svTRID></trID></response></epp>";
        parseXMLIntoResponse(response, xml);

        checkKeyValueList(kvExtension, "kvListOne");
        checkKeyValueList(kvExtension, "kvListTwo");
    }

    private void parseXMLIntoResponse(final DomainInfoResponse response, final String xml)
            throws ParsingException {
        final XMLDocument doc = xmlParser.parse(xml);
        response.fromXML(doc);
    }

    private void checkKeyValueList(final DomainInfoKVResponseExtension kvExtension, String listName) {
        assertEquals("123456789", kvExtension.getItem(listName, "registrantIDValue"));
        assertEquals("RegistrantName Pty. Ltd.", kvExtension.getItem(listName, "registrantName"));
        assertEquals("Trade License", kvExtension.getItem(listName, "registrantIDType"));
        assertEquals("Trademark", kvExtension.getItem(listName, "eligibilityType"));
        assertEquals("Registrant Eligi", kvExtension.getItem(listName, "eligibilityName"));
        assertEquals("987654321", kvExtension.getItem(listName, "eligibilityIDValue"));
        assertEquals("Trademark", kvExtension.getItem(listName, "eligibilityIDType"));
        assertEquals(2, Integer.parseInt(kvExtension.getItem(listName, "policyReason")));
    }
}
