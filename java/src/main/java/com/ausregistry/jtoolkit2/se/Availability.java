package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;

/**
 * Contains the availability of an object returned in a check response.
 */
class Availability implements Serializable {
    private static final long serialVersionUID = -9006953716874439338L;
    private boolean avail;
    private String reason;

    /**
     * @param avail the availability of an object
     * @param reason a reason why the object is unavailable, or null
     */
    Availability(final boolean avail, final String reason) {
        this.avail = avail;
        this.reason = reason;
    }

    /**
     * @return true if the object is available, false otherwise
     */
    public boolean isAvail() {
        return avail;
    }

    /**
     * @return the reason why the object is unavailable, may be null
     */
    public String getReason() {
        return reason;
    }
}
