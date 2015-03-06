package com.ausregistry.jtoolkit2.se.price;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Check command, representing the Pricing Check aspect of the Domain Name Pricing
 * extension.</p>
 *
 * <p>Use this to request information about a domain names pricing as part of an EPP Domain Check command. The response
 * expected from a server should be handled by a Domain Check Pricing Response Extension.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckCommand
 * @see com.ausregistry.jtoolkit2.se.price.DomainCheckPriceV1_1ResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/price-1.2/price-1.2.html">Domain Name Check Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPriceV1_2CommandExtension implements CommandExtension {

    private static final long serialVersionUID = 6536806259913602008L;
    private Period period;

    /**
     * @param command the domain-check command into which extension will be applied
     */
    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        Element checkElement = xmlWriter.appendChild(extensionElement, "check", ExtendedObjectType.PRICEV12.getURI());
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
