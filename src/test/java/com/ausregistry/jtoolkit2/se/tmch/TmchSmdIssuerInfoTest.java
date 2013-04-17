package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchSmdIssuerInfoTest extends TmchAbstractTest {

    private TmchSmdIssuerInfo tmchSmdIssuerInfo;

    @Before
    public void setUp() throws Exception {
        tmchSmdIssuerInfo = new TmchSmdIssuerInfo();

        tmchSmdIssuerInfo.fromXML(new XMLDocument((Element) xmlDocument.getElement("/smd:signedMark/smd:issuerInfo")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchSmdIssuerInfo.getId(), "2");
        assertEquals(tmchSmdIssuerInfo.getOrg(), "Example Inc.");
        assertEquals(tmchSmdIssuerInfo.getEmail(), "support@example.tld");
        assertEquals(tmchSmdIssuerInfo.getUrl(), "http://www.example.tld");
        assertEquals(tmchSmdIssuerInfo.getVoice(), "+1.7035555555");
        assertEquals(tmchSmdIssuerInfo.getVoiceExt(), "1234");
    }
}
