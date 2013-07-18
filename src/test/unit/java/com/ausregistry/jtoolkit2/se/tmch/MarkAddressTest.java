package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class MarkAddressTest extends MarkAbstractTest {

    private MarkAddress markAddress;

    @Before
    public void setUp() throws Exception {
        markAddress = new MarkAddress();
        markAddress.fromXML(new XMLDocument(
                        (Element) xmlDocument.getElement(
                                "/smd:signedMark/mark:mark/mark:trademark/mark:holder/mark:addr")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(markAddress.getStreets().get(0), "123 Example Dr.");
        assertEquals(markAddress.getStreets().get(1), "Suite 100");
        assertEquals(markAddress.getCity(), "Reston");
        assertEquals(markAddress.getSp(), "VA");
        assertEquals(markAddress.getPc(), "20190");
        assertEquals(markAddress.getCc(), "US");
    }
}
