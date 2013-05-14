package com.ausregistry.jtoolkit2.se.extendedAvailability;

import java.util.GregorianCalendar;

public class DomainCheckExtendedAvailabilityDetails {
    private String state;
    private String reason;
    private GregorianCalendar date;
    private String phase;
    private String variantPrimaryDomainName;

    public DomainCheckExtendedAvailabilityDetails(String state, String reason, GregorianCalendar date,
                                                  String phase, String variantPrimaryDomainName) {
        this.date = date;
        this.phase = phase;
        this.variantPrimaryDomainName = variantPrimaryDomainName;
        this.reason = reason;
        this.state = state;
    }

    public DomainCheckExtendedAvailabilityDetails() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getVariantPrimaryDomainName() {
        return variantPrimaryDomainName;
    }

    public void setVariantPrimaryDomainName(String variantPrimaryDomainName) {
        this.variantPrimaryDomainName = variantPrimaryDomainName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainCheckExtendedAvailabilityDetails)) return false;

        DomainCheckExtendedAvailabilityDetails that = (DomainCheckExtendedAvailabilityDetails) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (phase != null ? !phase.equals(that.phase) : that.phase != null) return false;
        if (variantPrimaryDomainName != null ? !variantPrimaryDomainName.equals(that.variantPrimaryDomainName)
                : that.variantPrimaryDomainName != null)
            return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (!state.equals(that.state)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        result = 31 * result + (variantPrimaryDomainName != null ? variantPrimaryDomainName.hashCode() : 0);
        return result;
    }
}
