package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TrademarkTest extends MarkAbstractTest {
    private Trademark trademark;

    private Date regDate;
    private Date exDate;
    private Date apDate;

    @Before
    public void setUp() throws Exception {
        trademark = new Trademark();

        trademark.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:trademark")));

        regDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        exDate = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
        apDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(trademark.getId(), "1234-2");
        assertEquals(trademark.getMarkName(), "Example One");
        assertEquals(trademark.getHolders().size(), 1);
        assertEquals(trademark.getContacts().size(), 1);
        assertEquals(trademark.getJurisdiction(), "US");
        assertEquals(trademark.getClasses().get(0), "35");
        assertEquals(trademark.getClasses().get(1), "36");
        assertEquals(trademark.getLabels().get(0), "example-one");
        assertEquals(trademark.getLabels().get(1), "exampleone");
        assertEquals(trademark.getGoodsAndServices(),
            "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(trademark.getRegNum(), "234235-A");
        assertEquals(trademark.getRegDate(), regDate);
        assertEquals(trademark.getExDate(), exDate);
        assertEquals(trademark.getApId(), "SOMEAPID");
        assertEquals(trademark.getApDate(), apDate);
    }
}
