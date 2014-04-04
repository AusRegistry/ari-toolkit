package com.ausregistry.jtoolkit2.se.price;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create Premium Domain aspect of the
 * Domain Name Price Extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Create
 * command compliant with RFC5730 and RFC5731. The "price" and "renewal price" values are optional, but if they are
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.DomainCreateResponse
 * @see <a href="http://ausregistry.github.io/doc/price-1.1/price-1.1.html">Domain Name Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */

public class DomainCreatePriceV1_1CommandExtension extends DomainCreatePriceCommandExtension {
    private static final long serialVersionUID = 5099827464190747273L;

    @Override
    protected ExtendedObjectType getExtendedObjectType() {
        return ExtendedObjectType.PRICEV11;
    }
}
