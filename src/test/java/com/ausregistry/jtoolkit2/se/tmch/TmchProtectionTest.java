package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchProtectionTest extends TmchAbstractTest {
    private TmchProtection tmchProtection;

    @Before
    public void setUp() throws Exception {
        tmchProtection = new TmchProtection();

        tmchProtection.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:treatyOrStatute/mark:protection")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchProtection.getRuling(), "US");
        assertEquals(tmchProtection.getCc(), "US");
        assertEquals(tmchProtection.getRegion(), "region");
    }
}
