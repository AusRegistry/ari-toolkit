package com.ausregistry.jtoolkit2.se.price;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Check command, representing the Pricing Check aspect of the Domain Name Pricing
 * extension.</p>
 *
 * <p>Use this to request information about a domain names pricing as part of an EPP Domain Check command. The response
 * expected from a server should be handled by a Domain Check Pricing Response Extension.</p>
 *
 * @see DomainCheckCommand
 * @see DomainCheckPriceResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/price-1.0/price-1.0.html">Domain Name Check Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPricingCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 2327272643303127953L;
    private Period period;

    /**
     * @param command the domain-check command into which extension will be applied
     */
    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        Element checkElement = xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.PRICE.getURI());
        if (period != null) {
            period.appendPeriod(xmlWriter, checkElement);
        }
    }

    /**
     * @param period period to check the prices for
     */
    public void setPeriod(Period period) {
        this.period = period;
    }
}
