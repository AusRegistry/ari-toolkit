package com.ausregistry.jtoolkit2.se.price;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;

/**
 * <p>Extension for the EPP Domain Transfer command, representing the Transfer Premium Domain aspect of the
 * Domain Name Price extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Transfer
 * command compliant with RFC5730 and RFC5731. The "renewal price" value is optional, but if it is
 * supplied, should match the renewal fee that is set for the domain name for the one year.
 * The response expected from a server should be handled by a Domain Transfer Response object.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferRequestCommand
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 * @see <a href="http://ausregistry.github.io/doc/price-1.1/price-1.1.html">Domain Name Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainTransferRequestPriceV1_1CommandExtension extends DomainTransferRequestPriceCommandExtension {
    private static final long serialVersionUID = -1977092400292645530L;

    @Override
    protected ExtendedObjectType getExtendedObjectType() {
        return ExtendedObjectType.PRICEV11;
    }

}
