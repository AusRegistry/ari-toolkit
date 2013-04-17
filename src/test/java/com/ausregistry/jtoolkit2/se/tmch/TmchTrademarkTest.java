package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchTrademarkTest extends TmchAbstractTest {
    private TmchTrademark tmchTrademark;

    private Date regDate;
    private Date exDate;
    private Date apDate;

    @Before
    public void setUp() throws Exception {
        tmchTrademark = new TmchTrademark();

        tmchTrademark.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:trademark")));

        regDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        exDate = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
        apDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchTrademark.getId(), "1234-2");
        assertEquals(tmchTrademark.getMarkName(), "Example One");
        assertEquals(tmchTrademark.getHolders().size(), 1);
        assertEquals(tmchTrademark.getContacts().size(), 1);
        assertEquals(tmchTrademark.getJurisdiction(), "US");
        assertEquals(tmchTrademark.getClasses().get(0), "35");
        assertEquals(tmchTrademark.getClasses().get(1), "36");
        assertEquals(tmchTrademark.getLabels().get(0), "example-one");
        assertEquals(tmchTrademark.getLabels().get(1), "exampleone");
        assertEquals(tmchTrademark.getGoodsAndServices()
                , "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(tmchTrademark.getRegNum(), "234235");
        assertEquals(tmchTrademark.getRegDate(), regDate);
        assertEquals(tmchTrademark.getExDate(), exDate);
        assertEquals(tmchTrademark.getApId(), "1-1");
        assertEquals(tmchTrademark.getApDate(), apDate);
    }
}
