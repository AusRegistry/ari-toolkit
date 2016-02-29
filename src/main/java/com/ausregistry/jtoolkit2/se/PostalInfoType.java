package com.ausregistry.jtoolkit2.se;

/**
 * Enumeration of PostalInfo types supported by EPP.  The only difference
 * between the two types is the allowed character encoding; international
 * postal info is restricted to US ASCII, whereas local postal info element
 * content may be represented in unrestricted UTF8.
 */
public enum PostalInfoType {
    INTERNATIONAL("int"),
    LOCAL("loc");

    private String type;

    PostalInfoType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}

