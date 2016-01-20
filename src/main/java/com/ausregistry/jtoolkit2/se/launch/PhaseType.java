package com.ausregistry.jtoolkit2.se.launch;

/**
 * Created by james.hateley on 19/01/2016.
 */
public enum PhaseType {

     SUNRISE("sunrise"),LANDRUSH("landrush"),CLAIMS("claims"),OPEN("open"),CUSTOM("custom");

    private String phaseType;

    PhaseType(String phaseType) {
        this.phaseType = phaseType;
    }

    public String getPhaseType() {
        return phaseType;
    }
}
