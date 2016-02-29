package com.ausregistry.jtoolkit2.se;

/**
 * Enumeration of poll operations supported by EPP.
 */
public enum PollOperation {
    REQ("req"),
    ACK("ack");

    private String op;

    PollOperation(String opName) {
        op = opName;
    }

    public String toString() {
        return op;
    }
}

