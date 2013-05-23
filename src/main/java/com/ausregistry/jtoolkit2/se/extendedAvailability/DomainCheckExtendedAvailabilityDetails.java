package com.ausregistry.jtoolkit2.se.extendedAvailability;

import java.util.GregorianCalendar;

/**
 * Contains the extended availability details of a domain returned in a check response extension.
 */
public class DomainCheckExtendedAvailabilityDetails {
    private String state;
    private String reason;
    private GregorianCalendar date;
    private String phase;
    private String variantPrimaryDomainName;

    /**
     * @param state the state of the domain label
     * @param reason the reason for the state
     * @param date the available date of the domain label
     * @param phase the name of the phase in which the domain label is available as an application
     * @param variantPrimaryDomainName the primary domain name for the variant domain label
     */
    public DomainCheckExtendedAvailabilityDetails(String state, String reason, GregorianCalendar date,
                                                  String phase, String variantPrimaryDomainName) {
        this.state = state;
        this.reason = reason;
        this.date = date;
        this.phase = phase;
        this.variantPrimaryDomainName = variantPrimaryDomainName;
    }

    /**
     * @return the state of the domain label
     */
    public String getState() {
        return state;
    }

    /**
     * @return the reason for the state
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return the available date of the domain label
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * @return the name of the phase in which the domain label is available as an application
     */
    public String getPhase() {
        return phase;
    }

    /**
     * @return the primary domain name for the variant domain label
     */
    public String getVariantPrimaryDomainName() {
        return variantPrimaryDomainName;
    }
}
