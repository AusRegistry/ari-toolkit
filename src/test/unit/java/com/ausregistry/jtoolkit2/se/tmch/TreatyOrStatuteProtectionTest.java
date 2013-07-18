package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TreatyOrStatuteProtectionTest extends MarkAbstractTest {
    private TreatyOrStatuteProtection treatyOrStatuteProtection;

    @Before
    public void setUp() throws Exception {
        treatyOrStatuteProtection = new TreatyOrStatuteProtection();

        treatyOrStatuteProtection.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:treatyOrStatute/mark:protection")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(treatyOrStatuteProtection.getRulings(), Arrays.asList("US", "CA"));
        assertEquals(treatyOrStatuteProtection.getCc(), "US");
        assertEquals(treatyOrStatuteProtection.getRegion(), "region");
    }
}
