package com.ausregistry.jtoolkit2.se.price;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Renew Premium Domain aspect of the
 * Domain Name Price extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Renew
 * command compliant with RFC5730 and RFC5731. The "price" value is optional, but if it is
 * supplied, should match the renewal fee that is set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Renew Response object.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 * @see com.ausregistry.jtoolkit2.se.DomainRenewResponse
 * @see <a href="http://ausregistry.github.io/doc/price-1.2/price-1.2.html">Domain Name Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRenewPriceV1_2CommandExtension implements CommandExtension {
    private static final long serialVersionUID = -9161045917661071996L;

    private BigDecimal price;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element renewElement = xmlWriter.appendChild(extensionElement, "renew",
                ExtendedObjectType.PRICEV12.getURI());
        Element ackElement = xmlWriter.appendChild(renewElement, "ack");

        if (price != null) {
            xmlWriter.appendChild(ackElement, "price").setTextContent(price.toPlainString());
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
