package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class MarkHolderTest extends MarkAbstractTest {

    private MarkHolder markHolder;

    @Before
    public void setUp() throws Exception {
        markHolder = new MarkHolder();

        markHolder.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:trademark/mark:holder")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(markHolder.getName(), "holderName");
        assertEquals(markHolder.getEntitlement().toString(), "owner");
        assertEquals(markHolder.getOrg(), "Example Inc.");
        assertNotNull(markHolder.getAddress());
        assertEquals(markHolder.getVoice(), "+1.7035555555");
        assertEquals(markHolder.getVoiceExt(), "1234");
        assertEquals(markHolder.getFax(), "+1.7035555555");
        assertEquals(markHolder.getFaxExt(), "1234");
        assertEquals(markHolder.getEmail(), "support@example.tld");
    }
}
