package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchTreatyOrStatuteTest extends TmchAbstractTest {
    private TmchTreatyOrStatute tmchTreatyOrStatute;

    private Date proDate;
    private Date execDate;

    @Before
    public void setUp() throws Exception {
        tmchTreatyOrStatute = new TmchTreatyOrStatute();

        tmchTreatyOrStatute.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:treatyOrStatute")));

        proDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        execDate = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchTreatyOrStatute.getId(), "1234-2");
        assertEquals(tmchTreatyOrStatute.getMarkName(), "Example One");
        assertEquals(tmchTreatyOrStatute.getHolders().size(), 1);
        assertEquals(tmchTreatyOrStatute.getContacts().size(), 0);
        assertEquals(tmchTreatyOrStatute.getTmchProtections().size(), 1);
        assertEquals(tmchTreatyOrStatute.getLabels().get(0), "example-one");
        assertEquals(tmchTreatyOrStatute.getLabels().get(1), "exampleone");
        assertEquals(tmchTreatyOrStatute.getGoodsAndServices()
                , "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(tmchTreatyOrStatute.getRefNum().longValue(), 234235);
        assertEquals(tmchTreatyOrStatute.getProDate(), proDate);
        assertEquals(tmchTreatyOrStatute.getTitle(), "title");
        assertEquals(tmchTreatyOrStatute.getExecDate(), execDate);
    }
}
