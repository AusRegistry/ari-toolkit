package com.ausregistry.jtoolkit2.xml;

public class Attribute {

    private final String type;
    private final String value;

    public Attribute(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
