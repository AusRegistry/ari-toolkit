package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class MarkCourtTest extends MarkAbstractTest {

    private CourtValidatedMark courtValidatedMark;

    private Date proDate;

    @Before
    public void setUp() throws Exception {
        courtValidatedMark = new CourtValidatedMark();

        courtValidatedMark.fromXML(new XMLDocument(
                (Element) xmlDocument.getElement(
                        "/smd:signedMark/mark:mark/mark:court")));

        proDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(courtValidatedMark.getId(), "1234-2");
        assertEquals(courtValidatedMark.getMarkName(), "Example One");
        assertEquals(courtValidatedMark.getHolders().size(), 1);
        assertEquals(courtValidatedMark.getContacts().size(), 0);
        assertEquals(courtValidatedMark.getRegions().size(), 2);
        assertEquals(courtValidatedMark.getRegions().get(0), "r1");
        assertEquals(courtValidatedMark.getRegions().get(1), "r3");
        assertEquals(courtValidatedMark.getLabels().get(0), "example-one");
        assertEquals(courtValidatedMark.getLabels().get(1), "exampleone");
        assertEquals(courtValidatedMark.getGoodsAndServices(),
            "Dirigendas et eiusmodi featuring infringo in airfare et cartam servicia.");
        assertEquals(courtValidatedMark.getRefNum(), "234235-A");
        assertEquals(courtValidatedMark.getProDate(), proDate);
        assertEquals(courtValidatedMark.getCc(), "cc");
        assertEquals(courtValidatedMark.getCourtName(), "courtName");
    }
}

