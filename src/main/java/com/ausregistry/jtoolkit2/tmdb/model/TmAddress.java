package com.ausregistry.jtoolkit2.tmdb.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents an "addr" element in the 'urn:ietf:params:xml:ns:tmNotice-1.0' namespace,
 *  defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmAddress {
    private List<String> streets = new ArrayList<String>();
    private String city;
    private String stateOrProvince;
    private String postalCode;
    private String countryCode;

    public void addStreet(String street) {
        streets.add(street);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<String> getStreets() {
        return streets;
    }

    public String getCity() {
        return city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
