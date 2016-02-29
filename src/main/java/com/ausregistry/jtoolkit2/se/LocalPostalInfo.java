package com.ausregistry.jtoolkit2.se;

/**
 * A character encoding-flexible subclass of PostalInfo which supports full
 * UTF-8 character encoding for all attribute values.
 */
public class LocalPostalInfo extends PostalInfo {
    private static final long serialVersionUID = 4775140595532169248L;

    public LocalPostalInfo(String name, String city, String countryCode) {
        super(PostalInfoType.LOCAL, name, city, countryCode);
    }

    public LocalPostalInfo(String name, String org,
            String[] street, String city, String stateProv, String postcode,
            String countryCode) {
        super(PostalInfoType.LOCAL, name, org, street, city,
                stateProv, postcode, countryCode);
    }
}

