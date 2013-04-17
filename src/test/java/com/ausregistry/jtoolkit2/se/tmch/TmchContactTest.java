package com.ausregistry.jtoolkit2.se.tmch;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchContactTest extends TmchAbstractTest {

    private TmchContact tmchContact;

    @Before
    public void setUp() throws Exception {
        tmchContact = new TmchContact();

        tmchContact.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:trademark/mark:contact")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchContact.getName(), "contactName");
        assertEquals(tmchContact.getEmail(), "123@123.com");
        assertEquals(tmchContact.getType().toString(), "owner");
        assertEquals(tmchContact.getOrg(), "Example Inc.");
        assertNotNull(tmchContact.getAddress());
        assertEquals(tmchContact.getVoice(), "+1.7035555555");
        assertEquals(tmchContact.getVoiceExt(), "1234");
        assertEquals(tmchContact.getFax(), "+1.7035555555");
        assertEquals(tmchContact.getFaxExt(), "1234");
    }
}
