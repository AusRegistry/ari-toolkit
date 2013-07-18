package com.ausregistry.jtoolkit2.tmdb.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents the "court" child element of "notExactMatch" element in the 'urn:ietf:params:xml:ns:tmNotice-1.0'
 *  namespace, defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmCourt {
    private Long referenceNumber;
    private String countryCode;
    private List<String> regions = new ArrayList<String>();
    private String courtName;

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void addRegion(String region) {
        regions.add(region);
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public List<String> getRegions() {
        return regions;
    }

    public String getCourtName() {
        return courtName;
    }
}
