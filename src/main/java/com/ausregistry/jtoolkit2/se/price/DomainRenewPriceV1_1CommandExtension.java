package com.ausregistry.jtoolkit2.se.price;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Renew Premium Domain aspect of the
 * Domain Name Price extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Renew
 * command compliant with RFC5730 and RFC5731. The "renewal price" value is optional, but if it is
 * supplied, should match the renewal fee that is set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Renew Response object.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 * @see com.ausregistry.jtoolkit2.se.DomainRenewResponse
 * @see <a href="http://ausregistry.github.io/doc/price-1.1/price-1.1.html">Domain Name Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRenewPriceV1_1CommandExtension extends DomainRenewPriceCommandExtension {
    private static final long serialVersionUID = -9161045917661071996L;

    @Override
    protected ExtendedObjectType getExtendedObjectType() {
        return ExtendedObjectType.PRICEV11;
    }

}
