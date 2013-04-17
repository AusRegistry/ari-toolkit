package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchCourtTest extends TmchAbstractTest {

    private TmchCourt tmchCourt;

    private Date proDate;

    @Before
    public void setUp() throws Exception {
        tmchCourt = new TmchCourt();

        tmchCourt.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:court")));

        proDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchCourt.getId(), "1234-2");
        assertEquals(tmchCourt.getMarkName(), "Example One");
        assertEquals(tmchCourt.getHolders().size(), 1);
        assertEquals(tmchCourt.getContacts().size(), 0);
        assertEquals(tmchCourt.getRegions().size(), 2);
        assertEquals(tmchCourt.getRegions().get(0), "r1");
        assertEquals(tmchCourt.getRegions().get(1), "r3");
        assertEquals(tmchCourt.getLabels().get(0), "example-one");
        assertEquals(tmchCourt.getLabels().get(1), "exampleone");
        assertEquals(tmchCourt.getGoodsAndServices()
                , "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(tmchCourt.getRefNum().longValue(), 234235);
        assertEquals(tmchCourt.getProDate(), proDate);
        assertEquals(tmchCourt.getCc(), "cc");
        assertEquals(tmchCourt.getCourtName(), "courtName");
    }
}

