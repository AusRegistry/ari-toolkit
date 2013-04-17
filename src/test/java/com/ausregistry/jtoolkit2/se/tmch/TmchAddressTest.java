package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchAddressTest extends TmchAbstractTest {

    private TmchAddress tmchAddress;

    @Before
    public void setUp() throws Exception {
        tmchAddress = new TmchAddress();
        tmchAddress.fromXML(new XMLDocument(
                        (Element) xmlDocument.getElement(
                                "/smd:signedMark/mark:mark/mark:trademark/mark:holder/mark:addr")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchAddress.getStreets().get(0), "123 Example Dr.");
        assertEquals(tmchAddress.getStreets().get(1), "Suite 100");
        assertEquals(tmchAddress.getCity(), "Reston");
        assertEquals(tmchAddress.getSp(), "VA");
        assertEquals(tmchAddress.getPc(), "20190");
        assertEquals(tmchAddress.getCc(), "US");
    }
}
