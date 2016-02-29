package com.ausregistry.jtoolkit2.se;

/**
 * Enumeration of Internet Protocol versions supported by EPP.
 */
public enum IPVersion {
    IPv4("v4"), IPv6("v6");

    private String name;

    IPVersion(String versionName) {
        name = versionName;
    }

    public String toString() {
        return name;
    }

    public static IPVersion value(String name) {
        if (name == null) {
            return IPVersion.IPv4;
        }

        return valueOf("IP" + name);
    }
}

