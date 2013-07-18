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

public class SignedMarkDataTest extends MarkAbstractTest {
    private SignedMarkData signedMarkData;

    private Date notBefore;
    private Date notAfter;

    @Before
    public void setUp() throws Exception {
        signedMarkData = new SignedMarkData();

        signedMarkData.fromXML(new XMLDocument((Element) xmlDocument.getElement("/smd:signedMark")));

        notBefore = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        notAfter = DatatypeConverter.parseDate("2010-08-16T09:00:00.0Z").getTime();
    }

    @Test
    public void shouldPopulateBeanFromXml() {
        assertEquals(signedMarkData.getId(), "1-2");
        assertNotNull(signedMarkData.getSmdIssuerInfo());
        assertEquals(signedMarkData.getNotBefore(), notBefore);
        assertEquals(signedMarkData.getNotAfter(), notAfter);
        assertNotNull(signedMarkData.getMarksList());
    }

    @Test
    public void shouldValidateBeforeDate() throws ParseException {
        Date validBeforeDate = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();
        Date invalidBeforeDate = DatatypeConverter.parseDate("2009-08-16T08:59:59.9Z").getTime();
        assertEquals(true, signedMarkData.isValid(validBeforeDate));
        assertEquals(false, signedMarkData.isValid(invalidBeforeDate));

    }

    @Test
    public void shouldValidateAfterDate() throws ParseException {
        Date validAfterDate = DatatypeConverter.parseDate("2010-08-16T09:00:00.0Z").getTime();
        Date invalidAfterDate = DatatypeConverter.parseDate("2010-08-16T09:00:00.1Z").getTime();
        assertEquals(true, signedMarkData.isValid(validAfterDate));
        assertEquals(false, signedMarkData.isValid(invalidAfterDate));
    }
}
