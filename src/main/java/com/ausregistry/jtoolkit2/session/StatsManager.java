package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * Extend the capabilities of a StatsViewer by supporting modification of the available statistics.
 */
public interface StatsManager extends StatsViewer {

    /**
     * Increment the result count for the given code. This is reflected in the return value of
     * {@link com.ausregistry.jtoolkit2.session.StatsViewer#getResultCodeCount}.
     */
    void incResultCounter(int resultCode);

    /**
     * Increment the command count for the given command type. This is reflected in the return value of
     * {@link com.ausregistry.jtoolkit2.session.StatsViewer#getCommandCount}.
     */
    void incCommandCounter(CommandType type);

    /**
     * Record the time interval (in milliseconds) which elapsed between sending a command and receiving a response to
     * that command - that is, the response time of a transaction.
     */
    void recordResponseTime(CommandType type, long responseTime);
}
