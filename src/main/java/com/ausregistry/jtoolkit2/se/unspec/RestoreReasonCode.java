package com.ausregistry.jtoolkit2.se.unspec;

public enum RestoreReasonCode {
    Other(0),
    RegistrantError(1),
    RegistrarError(2),
    Legal(3);

    private int reasonCode;

    RestoreReasonCode(int reasonCode) {
        this.reasonCode = reasonCode;
    }

    public int getValue() {
        return reasonCode;
    }
}
