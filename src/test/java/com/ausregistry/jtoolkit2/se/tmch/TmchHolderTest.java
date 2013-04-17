package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchHolderTest extends TmchAbstractTest {

    private TmchHolder tmchHolder;

    @Before
    public void setUp() throws Exception {
        tmchHolder = new TmchHolder();

        tmchHolder.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:trademark/mark:holder")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchHolder.getName(), "holderName");
        assertEquals(tmchHolder.getEntitlement().toString(), "owner");
        assertEquals(tmchHolder.getOrg(), "Example Inc.");
        assertNotNull(tmchHolder.getAddress());
        assertEquals(tmchHolder.getVoice(), "+1.7035555555");
        assertEquals(tmchHolder.getVoiceExt(), "1234");
        assertEquals(tmchHolder.getFax(), "+1.7035555555");
        assertEquals(tmchHolder.getFaxExt(), "1234");
    }
}
