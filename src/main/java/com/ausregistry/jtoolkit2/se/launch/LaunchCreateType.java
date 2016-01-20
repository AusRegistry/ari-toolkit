package com.ausregistry.jtoolkit2.se.launch;

import com.ausregistry.jtoolkit2.se.DomainCreateCommand;

/**
 * <p>Enum maps to 'objectType' attribute in launch-1.0 extension, represents whether an application is
 * created or a direct registration.</p>
 *
 *
 * @see DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.launch.DomainCreateLaunchCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/launch-1.0/launch-1.0.html">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
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
