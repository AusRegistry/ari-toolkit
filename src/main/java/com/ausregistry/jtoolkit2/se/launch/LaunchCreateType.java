package com.ausregistry.jtoolkit2.se.launch;


public enum LaunchCreateType {
    REGISTRATION("registration"), APPLICATION("application");

    private String createType;
    LaunchCreateType(String createType) {
        this.createType = createType;
    }

    public String getCreateType() {
        return createType;
    }
}
