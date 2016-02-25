package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DomainCreateApplicationLaunchResponseExtensionTest {
    private static final String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">" +
                    "  <response>" +
                    "    <result code=\"1000\">" +
                    "      <msg lang=\"en\">Command completed successfully</msg>" +
                    "    </result>" +
                    "    <resData>" +
                    "      <domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\">" +
                    "        <domain:name>domdomdom-one4.apple</domain:name>" +
                    "        <domain:crDate>2013-04-01T21:51:27Z</domain:crDate>" +
                    "      </domain:creData>" +
                    "    </resData>" +
                    "    <extension>" +
                    "<launch:creData xmlns:launch=\"urn:ietf:params:xml:ns:launch-1.0\">" +
                    "                <launch:phase>claims</launch:phase>" +
                    "        <launch:applicationID>ACF7B10EBC3284E34B4A7C350DA27EA7B-ARI</launch:applicationID>" +
                    "      </launch:creData>" +
                    "    </extension>" +
                    "    <trID>" +
                    "      <clTRID>ABC-12345</clTRID>" +
                    "      <svTRID>1a1f7fa4-c898-4ad0-8f9b-2c9d40f0d96a</svTRID>" +
                    "    </trID>" +
                    "  </response>" +
                    "</epp>";

    private static final String feeXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\">" +
                    "  <response>" +
                    "    <result code=\"1000\">" +
                    "      <msg lang=\"en\">Command completed successfully</msg>" +
                    "    </result>" +
                    "    <resData>" +
                    "      <domain:creData xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\">" +
                    "        <domain:name>domdomdom-one4.apple</domain:name>" +
                    "        <domain:crDate>2013-04-01T21:51:27Z</domain:crDate>" +
                    "      </domain:creData>" +
                    "    </resData>" +
                    "    <extension>" +
                    "<launch:creData xmlns:launch=\"urn:ietf:params:xml:ns:launch-1.0\">" +
                    "                <launch:phase>claims</launch:phase>" +
                    "        <launch:applicationID>ACF7B10EBC3284E34B4A7C350DA27EA7B-ARI</launch:applicationID>" +
                    "      </launch:creData>" +
                    "      <fee:creData xmlns:fee=\"urn:ietf:params:xml:ns:fee-0.6\">" +
                    "      <fee:currency>" + "USD" + "</fee:currency>" +
                    "      <fee:fee>" + "10.00" + "</fee:fee>" +
                    "      </fee:creData>" +
                    "    </extension>" +
                    "    <trID>" +
                    "      <clTRID>ABC-12345</clTRID>" +
                    "      <svTRID>1a1f7fa4-c898-4ad0-8f9b-2c9d40f0d96a</svTRID>" +
                    "    </trID>" +
                    "  </response>" +
                    "</epp>";

    private XMLParser xmlParser;
    private DomainCreateApplicationLaunchResponseExtension responseExtension;
    private DomainCreateResponse domainResponse;



    @Before
    public void setUp() throws Exception {
        xmlParser = new XMLParser();
        responseExtension = new DomainCreateApplicationLaunchResponseExtension();
        domainResponse = new DomainCreateResponse();
        domainResponse.registerExtension(responseExtension);
    }

    @Test
    public void shouldGetId() {
        parseXml(xml);
        assertTrue(responseExtension.isInitialised());
        assertEquals(responseExtension.getId(), "ACF7B10EBC3284E34B4A7C350DA27EA7B-ARI");
    }

    @Test
    public void shouldGetPhase() {
        parseXml(xml);
        assertTrue(responseExtension.isInitialised());
        assertEquals(responseExtension.getPhase(), "claims");

    }

    @Test
    public void shouldGetFee() {
        parseXml(feeXml);
        assertTrue(responseExtension.isFeeInitialised());
        assertEquals(responseExtension.getCurrency(), "USD");
        assertEquals("10.00", responseExtension.getFee().toPlainString());
    }

    private void parseXml(String xml){
        XMLParser parser = new XMLParser();
        XMLDocument doc = null;

        try {
            doc = parser.parse(xml);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        domainResponse.fromXML(doc);
    }



}