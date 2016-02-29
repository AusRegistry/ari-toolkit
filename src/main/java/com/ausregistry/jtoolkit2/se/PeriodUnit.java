package com.ausregistry.jtoolkit2.se;

/**
 * Enumeration of units supported by EPP for period elements.
 */
public enum PeriodUnit {
    MONTHS("m"), YEARS("y");

    private String desc;

    PeriodUnit(String description) {
        desc = description;
    }

    public String toString() {
        return desc;
    }

    public static PeriodUnit value(String name) {
        if (name == null) {
            return PeriodUnit.YEARS;
        }

        for (PeriodUnit unit : PeriodUnit.values()) {
            if (unit.toString().equals(name)) {
                return unit;
            }
        }

        return PeriodUnit.YEARS;
    }
}

