package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * Provides the means to view individual or aggregate Session statistics.
 */
public interface StatsViewer {

    /**
     * Get the number of responses received having the given result code.
     */
    long getResultCodeCount(int resultCode);

    /**
     * Get the number of commands of the given type that the Session(s)
     * associated with this viewer has/have processed since creation.
     */
    long getCommandCount();

    /**
     * Get the number of commands of the given type that the Session(s)
     * associated with this viewer has/have processed recently (default: 1
     * second).
     */
    int getRecentCommandCount();

    /**
     * Get the total number of commands processed by Sessions associated with
     * this StatsViewer.
     */
    long getCommandCount(CommandType type);

    /**
     * Get the total number of commands recently processed by Sessions
     * associated with this StatsViewer.
     */
    int getRecentCommandCount(CommandType type);

    /**
     * Get the average response time (in milliseconds) of all transactions.
     */
    long getAverageResponseTime();

    /**
     * Get the average response time (in milliseconds) of transactions of the
     * given command type.
     */
    long getAverageResponseTime(CommandType type);

    /**
     * Get the time interval since the most recent use of a Session associated
     * with this StatsViewer.
     */
    long getMruInterval();
}

