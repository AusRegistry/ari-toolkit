package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class MarksListTest extends MarkAbstractTest {
    private MarksList marksList;

    @Before
    public void setUp() throws Exception {
        marksList = new MarksList();

        marksList.fromXML(new XMLDocument((Element) xmlDocument.getElement("/smd:signedMark/mark:mark")));
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(marksList.getMarks().size(), 3);
    }
}
