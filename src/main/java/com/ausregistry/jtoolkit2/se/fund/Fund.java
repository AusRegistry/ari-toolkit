package com.ausregistry.jtoolkit2.se.fund;

import java.math.BigDecimal;

/**
 * This class models a fund object. Instances are used to access attributes
 * of fund information obtained by querying a list of fund objects via a
 * fund info EPP command, the response to which is implemented in the class
 * FundInfoResponse.
 */
public class Fund {
    private String id;
    private boolean limit;
    private BigDecimal available;

    public Fund(String id, boolean limit, BigDecimal available) {
        this.id = id;
        this.limit = limit;
        this.available = available;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public String getId() {
        return id;
    }

    public boolean getLimit() {
        return limit;
    }
}
