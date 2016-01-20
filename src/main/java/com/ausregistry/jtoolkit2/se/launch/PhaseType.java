package com.ausregistry.jtoolkit2.se.launch;


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
