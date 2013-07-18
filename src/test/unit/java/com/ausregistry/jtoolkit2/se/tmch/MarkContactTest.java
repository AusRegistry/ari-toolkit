package com.ausregistry.jtoolkit2.se.tmch;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class MarkContactTest extends MarkAbstractTest {

    private MarkContact markContact;

    @Before
    public void setUp() throws Exception {
        markContact = new MarkContact();

        markContact.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:trademark/mark:contact")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(markContact.getName(), "contactName");
        assertEquals(markContact.getEmail(), "123@123.com");
        assertEquals(markContact.getType().toString(), "owner");
        assertEquals(markContact.getOrg(), "Example Inc.");
        assertNotNull(markContact.getAddress());
        assertEquals(markContact.getVoice(), "+1.7035555555");
        assertEquals(markContact.getVoiceExt(), "1234");
        assertEquals(markContact.getFax(), "+1.7035555555");
        assertEquals(markContact.getFaxExt(), "1234");
    }
}
