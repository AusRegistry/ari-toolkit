package com.ausregistry.jtoolkit2;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Provides utility methods to parse and generate XML date/dateTime strings.
 * An XMLGregorianCalendar is used as the object representation of the XML date
 * strings, since it provides compliance with the XML date and dateTime
 * data types.  It also supports getting an instance of
 * <code>java.util.Calendar</code> representing the
 * <code>XMLGregorianCalendar</code> object returned.
 *
 * Uses the maintenance and user level loggers.
 */
public final class EPPDateFormatter {
    private static String pname;
    private static DatatypeFactory dtFactory;
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private EPPDateFormatter() {
        // intentionally do nothing, make checkstyle happy
    }

    static {
        pname = EPPDateFormatter.class.getPackage().getName();

        try {
            dtFactory = DatatypeFactory.newInstance();
        } catch (javax.xml.datatype.DatatypeConfigurationException dce) {
            Logger.getLogger(pname + ".maint").severe(dce.getMessage());
            Logger.getLogger(pname + ".user").severe(
                    ErrorPkg.getMessage("xml.datatype.config.fail.msg"));
        }
    }

    /**
     * Generate a UTC normalised representation of the given date.
     * Normalisation is performed to prevent loss of time zone information when
     * truncating a dateTime input to a date lexical representation.
     *
     * @param date the date to be converted to XSD format
     * @return the formatted string
     */
    public static String toXSDate(GregorianCalendar date) {
        return toXSDateTime(date).substring(0, 10);
    }

    /**
     * Generate a UTC normalised lexical XML string representation of the given date and time.
     *
     * @param date the date to be converted to XSD format
     * @return the formatted string
     */
    public static String toXSDateTime(GregorianCalendar date) {
        XMLGregorianCalendar calendar = dtFactory.newXMLGregorianCalendar(date);
        return calendar.normalize().toXMLFormat();
    }

    /**
     * Parse a lexical representation of an XML dateTime into a GregorianCalendar object
     * representation, taking into account time zone.
     *
     * @param dateTime the date to be converted to XSD format
     * @return the formatted string
     */
    public static GregorianCalendar fromXSDateTime(String dateTime) {
        GregorianCalendar cal = null;

        if (dateTime == null || dateTime.length() == 0) {
            Logger.getLogger(pname + ".maint").info(
                    ErrorPkg.getMessage("xml.dateTime.null"));
        } else {
            Logger.getLogger(pname + ".debug").finer(dateTime);
            cal = dtFactory.newXMLGregorianCalendar(dateTime).toGregorianCalendar();
            cal.setTimeZone(UTC);
        }

        return cal;
    }
}

