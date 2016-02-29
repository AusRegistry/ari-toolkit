package com.ausregistry.jtoolkit2.se;

/**
 * A restricted subclass of PostalInfo which supports only US ASCII character
 * encoding as attribute values.
 */
public class IntPostalInfo extends PostalInfo {
    private static final long serialVersionUID = 6960166135482859331L;

    public IntPostalInfo(String name, String city, String countryCode) {
        super(PostalInfoType.INTERNATIONAL, name, city, countryCode);
    }

    public IntPostalInfo(String name, String org,
            String[] street, String city, String stateProv, String postcode,
            String countryCode) {
        super(PostalInfoType.INTERNATIONAL, name, org, street, city,
                stateProv, postcode, countryCode);
    }
}

