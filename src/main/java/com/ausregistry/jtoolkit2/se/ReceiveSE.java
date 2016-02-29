package com.ausregistry.jtoolkit2.se;

import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Handles basic processing of all EPP packets received by the client. Parsing of the received XML document is the only
 * responsibility of this class.
 *
 * Uses the maintenance, debug, support and user level loggers.
 */
public abstract class ReceiveSE implements java.io.Serializable {

    private static final long serialVersionUID = 3404913770026760200L;

    protected Logger maintLogger;
    protected Logger userLogger;
    protected Logger debugLogger;

    private String xml;
    private Logger supportLogger;

    protected ReceiveSE() {
        final String pname = getClass().getPackage().getName();
        maintLogger = Logger.getLogger(pname + ".maint");
        supportLogger = Logger.getLogger(pname + ".support");
        userLogger = Logger.getLogger(pname + ".user");
        debugLogger = Logger.getLogger(pname + ".debug");
    }

    /**
     * Set attribute values according to the given XML document.
     *
     * @param xmlDoc the Document to parse
     */
    protected abstract void fromXML(XMLDocument xmlDoc);

    /**
     * Replace the "IDX" string to retrieve the XPATH element of the XML.
     *
     * @param inputExpr the input expr
     * @param index the index
     * @return the string
     */
    public static String replaceIndex(final String inputExpr, final int index) {
        return inputExpr.replaceAll("IDX", String.valueOf(index));
    }

    /**
     * Convert an Array to string.
     *
     * @param arr the array to convert
     * @param separator the separator to be used between elements
     * @return the string
     */
    protected String arrayToString(final Object[] arr, final String separator) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        // allocate what is probably a bit too much space to reduce number of
        // memory allocation system calls.
        final String first = arr[0].toString();
        final StringBuilder buf = new StringBuilder(first.length() * arr.length * 2);

        buf.append(first);
        for (int i = 1; i < arr.length; i++) {
            buf.append(separator + arr[i].toString());
        }

        return buf.toString();
    }

    /**
     *
     * @return the received XML
     */
    public String toXML() {
        return xml;
    }

}
