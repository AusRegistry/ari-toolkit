package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TreatyOrStatuteTest extends MarkAbstractTest {
    private TreatyOrStatute treatyOrStatute;

    private Date proDate;
    private Date execDate;

    @Before
    public void setUp() throws Exception {
        treatyOrStatute = new TreatyOrStatute();

        treatyOrStatute.fromXML(new XMLDocument((Element) xmlDocument.getElement(
                "/smd:signedMark/mark:mark/mark:treatyOrStatute")));

        proDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        execDate = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(treatyOrStatute.getId(), "1234-2");
        assertEquals(treatyOrStatute.getMarkName(), "Example One");
        assertEquals(treatyOrStatute.getHolders().size(), 1);
        assertEquals(treatyOrStatute.getContacts().size(), 0);
        assertEquals(treatyOrStatute.getTreatyOrStatuteProtections().size(), 1);
        assertEquals(treatyOrStatute.getLabels().get(0), "example-one");
        assertEquals(treatyOrStatute.getLabels().get(1), "exampleone");
        assertEquals(treatyOrStatute.getGoodsAndServices(),
            "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(treatyOrStatute.getRefNum(), "234235-A");
        assertEquals(treatyOrStatute.getProDate(), proDate);
        assertEquals(treatyOrStatute.getTitle(), "title");
        assertEquals(treatyOrStatute.getExecDate(), execDate);
    }
}
