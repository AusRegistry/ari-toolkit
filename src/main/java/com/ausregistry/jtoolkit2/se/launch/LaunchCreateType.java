package com.ausregistry.jtoolkit2.se.launch;

/**
 * Created by james.hateley on 19/01/2016.
 */
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
