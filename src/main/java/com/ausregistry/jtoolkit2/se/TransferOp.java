package com.ausregistry.jtoolkit2.se;

/**
 * An enumeration of the transfer operation types defined for transfer commands
 * in RFC5730.
 */
public enum TransferOp {
    QUERY("query"),
    REQUEST("request"),
    CANCEL("cancel"),
    APPROVE("approve"),
    REJECT("reject");

    private String op;

    TransferOp(String operation) {
        op = operation;
    }

    @Override
    public String toString() {
        return op;
    }
}

