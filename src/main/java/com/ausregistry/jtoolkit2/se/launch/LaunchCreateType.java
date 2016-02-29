package com.ausregistry.jtoolkit2.se.launch;


/**
 * <p>Enum maps to 'objectType' attribute in launch-1.0 extension, represents whether an application is
 * created or a direct registration.</p>
 *
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see DomainCreateLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
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
