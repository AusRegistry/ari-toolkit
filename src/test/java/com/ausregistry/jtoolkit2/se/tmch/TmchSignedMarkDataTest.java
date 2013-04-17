package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.util.Date;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class TmchSignedMarkDataTest extends TmchAbstractTest {
    private TmchSignedMarkData tmchSignedMarkData;

    private Date notBefore;
    private Date notAfter;

    @Before
    public void setUp() throws Exception {
        tmchSignedMarkData = new TmchSignedMarkData();

        tmchSignedMarkData.fromXML(new XMLDocument((Element) xmlDocument.getElement("/smd:signedMark")));

        notBefore = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        notAfter = DatatypeConverter.parseDate("2010-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(tmchSignedMarkData.getId(), "1-2");
        assertNotNull(tmchSignedMarkData.getTmchSmdIssuerInfo());
        assertEquals(tmchSignedMarkData.getNotBefore(), notBefore);
        assertEquals(tmchSignedMarkData.getNotAfter(), notAfter);
        assertNotNull(tmchSignedMarkData.getTmchMark());
    }

    @Test
    public void shouldValidateBeforeDate() throws ParseException {
        Date validBeforeDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        Date invalidBeforeDate = DatatypeConverter.parseDate("2009-08-16T08:59:59.9Z").getTime();
        assertEquals(true, tmchSignedMarkData.isValid(validBeforeDate));
        assertEquals(false, tmchSignedMarkData.isValid(invalidBeforeDate));

    }

    @Test
    public void shouldValidateAfterDate() throws ParseException {
        Date validAfterDate = DatatypeConverter.parseDate("2010-08-16T09:00:00.0Z").getTime();
        Date invalidAfterDate = DatatypeConverter.parseDate("2010-08-16T09:00:00.1Z").getTime();
        assertEquals(true, tmchSignedMarkData.isValid(validAfterDate));
        assertEquals(false, tmchSignedMarkData.isValid(invalidAfterDate));
    }
}
