package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.Timer;

import java.text.SimpleDateFormat;
import java.text.FieldPosition;
import java.util.Date;

/**
 * Provides generated unique client transaction identifiers for use in EPP
 * commands as the value of the epp:clTRID element.  The class should first be
 * initialised by setting the client identifier.
 */
public final class CLTRID implements java.io.Serializable {
    private static final long serialVersionUID = 6808939605799601108L;
    private static final int BUFFER_SIZE = 24;
    private static final int MAX_VAL = 1000;
    private static int val = 0;
    private static String clID = "";

    private CLTRID() {
        // intentionally do nothing, make checkstyle happy
    }

    /**
     * Generate a unique client transaction identifier and return the value.
     */
    public static String nextVal() {
        StringBuffer buffer = new StringBuffer(BUFFER_SIZE);
        buffer.append(clID);
        Date date = new Date(Timer.now());
        SimpleDateFormat format = new SimpleDateFormat(".yyyyMMdd.HHmmss.");
        assert date != null;
        assert buffer != null;
        assert format != null;
        buffer = format.format(date, buffer, new FieldPosition(0));
        buffer.append(String.valueOf(val));
        CLTRID.inc();

        return new String(buffer);
    }

    /**
     * Set the client identifier for generating client transaction IDs.  This
     * constitutes the first part of the clTRID and helps to ensure uniqueness
     * of clTRIDs within a Registry system.
     */
    public static void setClID(String clID) {
        CLTRID.clID = clID;
        val = 0;
    }

    private static void inc() {
        if (val >= MAX_VAL) {
            val = 0;
        } else {
            val++;
        }
    }
}

