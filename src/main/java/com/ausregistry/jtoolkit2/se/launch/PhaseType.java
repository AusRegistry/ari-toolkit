package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.DomainCreateCommand;

/**
 * <p>Enum maps to 'phaseTypeValue' in launch-1.0.xsd extension, is required as value for 'phase' attribute</p>
 *
 *
 * @see DomainCreateCommand
 * @see DomainCreateLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
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
