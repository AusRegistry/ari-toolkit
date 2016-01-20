package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.DomainCreateCommand;

/**
 * <p>Enum maps to 'phaseTypeValue' in launch-1.0.xsd extension, is required as value for 'phase' attribute</p>
 *
 *
 * @see DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.launch.DomainCreateLaunchCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/launch-1.0/launch-1.0.html">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
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
