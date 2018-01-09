package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResponseTest {
    private static final String XML1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"1500\"><msg>Command completed successfully; ending session</msg></result><trID><clTRID>TESTER1.20070101.010101.1</clTRID><svTRID>32161187</svTRID></trID></response></epp>";
    private static final String XML2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"2005\"><msg>Parameter value syntax error</msg><extValue><value><domain:name xmlns:domain='urn:ietf:params:xml:ns:domain-1.0'/></value><reason lang='en'>Datatype Error; Datatype error: Type:InvalidDatatypeValueException, Message:Value &apos;&apos; with length &apos;0&apos; is less than minimum length facet of &apos;1&apos; .</reason></extValue></result><trID><clTRID>TESTER1.20070101.010101.1</clTRID><svTRID>32161187</svTRID></trID></response></epp>";
    private static final String XML3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><response><result code=\"2005\"><msg>Parameter value syntax error</msg><value><domain:name xmlns:domain='urn:ietf:params:xml:ns:domain-1.0'/></value></result><trID><clTRID>TESTER1.20070101.010101.1</clTRID><svTRID>32161187</svTRID></trID></response></epp>";
    private XMLDocument xmlDoc;
    private Result[] results1;
    private Result[] results2;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        XMLParser parser = new XMLParser();
        xmlDoc = parser.parse(XML1);

        Response response = new Response();

        XMLDocument xmlDocTmp = parser.parse(XML2);
        response.fromXML(xmlDocTmp);
        results1 = response.getResults();

        xmlDocTmp = parser.parse(XML3);
        response.fromXML(xmlDocTmp);
        results2 = response.getResults();
    }

    @Test
    public final void testFromXML() {
        Response response = new Response();
        response.fromXML(xmlDoc);

        assertEquals(1500, response.getResults()[0].getResultCode());
    }

    @Test
    public void testResultCount() {
        assertEquals(1, results1.length);
    }

    @Test
    public void testGetResultCode() {
        int exp = 2005;

        assertEquals(exp, results1[0].getResultCode());
    }

    @Test
    public void testGetResultMessage() {
        String exp = "Parameter value syntax error";

        assertEquals(exp, results1[0].getResultMessage());
    }

    @Test
    public final void testGetResultExtValueValue() {
        int expCount = 1;
        String expURI = "urn:ietf:params:xml:ns:domain-1.0";
        String expName = "name";
        String expPrefix = "domain";

        Node[] valueNodes = results1[0].getResultExtValueValue();
        assertEquals(expCount, valueNodes.length);
        Node content = valueNodes[0].getFirstChild();

        String uri = content.getNamespaceURI();
        String name = content.getLocalName();
        String prefix = content.getPrefix();

        assertEquals(expURI, uri);
        assertEquals(expName, name);
        assertEquals(expPrefix, prefix);
    }

    @Test
    public final void testGetResultExtValueReason() {
        String exp = "Datatype Error; Datatype error: Type:InvalidDatatypeValueException, Message:Value '' with length '0' is less than minimum length facet of '1' .";

        assertEquals(exp, results1[0].getResultExtValueReason()[0]);
    }

    @Test
    public final void testSucceeded() {
        boolean exp = false;

        assertEquals(exp, results1[0].succeeded());
    }

    @Test
    public final void testGetValuesAsText1() {
        int expCount = 1;
        String expText = "<value><domain:name xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"/></value>";

        assertEquals("Value Count", expCount, results1[0].getValuesAsText().length);
        assertEquals("Value Text", expText, results1[0].getValuesAsText()[0]);
    }

    @Test
    public final void testGetValuesAsText2() {
        int expCount = 1;
        String expText = "<value><domain:name xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\"/></value>";

        assertEquals(expCount, results2[0].getValuesAsText().length);
        assertEquals(expText, results2[0].getValuesAsText()[0]);
    }

    @Test
    public final void testGetResultValue() {
        NodeList values = results2[0].getResultValue();

        int expCount = 1;
        String expURI = "urn:ietf:params:xml:ns:domain-1.0";
        String expName = "name";
        String expPrefix = "domain";

        assertEquals(expCount, values.getLength());
        Node valueNode = values.item(0);
        Node content = valueNode.getFirstChild();

        String uri = content.getNamespaceURI();
        String name = content.getLocalName();
        String prefix = content.getPrefix();

        assertEquals(expURI, uri);
        assertEquals(expName, name);
        assertEquals(expPrefix, prefix);
    }
}
