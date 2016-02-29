package com.ausregistry.jtoolkit2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Provides time-related services, such as calculation of time intervals in various precisions and configuration of
 * timing to use either real world or simulated time, to be set by the user. The simulated time features are primarily
 * for supporting repeatable testing.
 *
 * Uses the debug, support and user level loggers.
 */
public final class Timer {
    private static String pname;
    private static boolean useRealTime = true;
    private static long fixedTime;

    static {
        pname = Timer.class.getPackage().getName();
    }

    private Timer() {
    }

    /**
     * Resets the Toolkit to use the current milliseconds rather than a simulated time.
     */
    public static void useRealTime() {
        useRealTime = true;
    }

    /**
     * Calculates the current time based on either the number of milliseconds since the UNIX epoch or a fixed time when
     * using a simulated time.
     *
     * @return the time
     */
    public static long now() {
        if (useRealTime) {
            return System.currentTimeMillis();
        } else {
            return fixedTime;
        }
    }

    /**
     * Calculates the difference in time between a given time and now..
     *
     * @param compareTime
     *            the compare time
     * @return the difference
     */
    public static long msDiff(long compareTime) {
        return (Timer.now() - compareTime);
    }

    /**
     * Sets the simulated time and forces the Toolkit to use a simulated fixed time.
     *
     * @param dateString
     *            a date represented in the format "yyyyMMdd.HHmmss".
     */
    public static void setTime(String dateString) {
        useRealTime = false;

        DateFormat df = new SimpleDateFormat("yyyyMMdd.HHmmss");
        try {
            Date time = df.parse(dateString);
            fixedTime = time.getTime();
        } catch (java.text.ParseException pe) {
            Logger.getLogger(pname + ".user").warning(pe.getMessage());
            Logger.getLogger(pname + ".user").warning(ErrorPkg.getMessage("Timer.setTime.0"));
        }
    }
}
