package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class SmdIssuerInfoTest extends MarkAbstractTest {

    private SmdIssuerInfo smdIssuerInfo;

    @Before
    public void setUp() throws Exception {
        smdIssuerInfo = new SmdIssuerInfo();

        smdIssuerInfo.fromXML(new XMLDocument((Element) xmlDocument.getElement("/smd:signedMark/smd:issuerInfo")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(smdIssuerInfo.getId(), "2");
        assertEquals(smdIssuerInfo.getOrg(), "Example Inc.");
        assertEquals(smdIssuerInfo.getEmail(), "support@example.tld");
        assertEquals(smdIssuerInfo.getUrl(), "http://www.example.tld");
        assertEquals(smdIssuerInfo.getVoice(), "+1.7035555555");
        assertEquals(smdIssuerInfo.getVoiceExt(), "1234");
    }
}
