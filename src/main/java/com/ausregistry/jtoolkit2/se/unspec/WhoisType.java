package com.ausregistry.jtoolkit2.se.unspec;

public enum WhoisType {
    LEGAL("Legal"),
    NATURAL("Natural");

    private String value;

    WhoisType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
