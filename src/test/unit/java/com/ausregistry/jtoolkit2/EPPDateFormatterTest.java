package com.ausregistry.jtoolkit2;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

public class EPPDateFormatterTest {
    private static GregorianCalendar dateTime;
    private static String utcNormalisedDateTimeString;
    private static String utcNormalisedDateString;

    @BeforeClass
    public static void setup() throws Exception {
        dateTime = new GregorianCalendar(2000, Calendar.MARCH, 23, 3, 0, 0);
        dateTime.setTimeZone(TimeZone.getTimeZone("GMT+10"));
        utcNormalisedDateTimeString = "2000-03-22T17:00:00.000Z";
        utcNormalisedDateString = "2000-03-22";
    }

    @Test
    public void testWriteDate() {
        assertEquals(utcNormalisedDateString, EPPDateFormatter.toXSDate(dateTime));
    }

    @Test
    public void testWriteDateTime() {
        assertEquals(utcNormalisedDateTimeString, EPPDateFormatter.toXSDateTime(dateTime));
    }
}

