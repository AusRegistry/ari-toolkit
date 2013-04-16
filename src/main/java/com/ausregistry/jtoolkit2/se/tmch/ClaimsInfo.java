package com.ausregistry.jtoolkit2.se.tmch;

public class ClaimsInfo {
    private final Boolean exists;
    private final String claimsKey;

    public ClaimsInfo(Boolean exists, String claimsKey) {
        this.exists = exists;
        this.claimsKey = claimsKey;
    }

    public Boolean exists() {
        return exists;
    }

    public String getClaimsKey() {
        return claimsKey;
    }
}
