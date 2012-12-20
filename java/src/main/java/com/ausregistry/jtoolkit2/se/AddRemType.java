package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;

/**
 * Represents add or remove in EPP Domain Update command. This is used internally by the DomainAdd and DomainRem classes
 * to control the behaviour of their superclass DomainAddRem.
 *
 */
public enum AddRemType implements Serializable {
    ADD("add"), REM("rem");

    private String typeVal;

    AddRemType(String typeVal) {
        this.typeVal = typeVal;
    }

    @Override
    public String toString() {
        return typeVal;
    }
}
