package com.ausregistry.jtoolkit2.se.price;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Restore Request command, representing the Restore Premium Domain aspect of the
 * Domain Name Price extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Restore Request
 * command compliant with RFC5730 and RFC5731. The "price" value is optional, but if it is
 * supplied, should match the restore fee that is set for the domain name.
 * The response expected from a server should be handled by a Domain Transfer Response object.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.rgp.DomainRestoreReportCommand
 * @see com.ausregistry.jtoolkit2.se.rgp.DomainRestoreResponse
 * @see <a href="http://ausregistry.github.io/doc/price-1.1/price-1.1.html">Domain Name Price Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRestoreRequestPriceV1_1CommandExtension implements CommandExtension {
    private BigDecimal price;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element transferElement = xmlWriter.appendChild(extensionElement, "update",
                ExtendedObjectType.PRICEV11.getURI());
        Element ackElement = xmlWriter.appendChild(transferElement, "ack");

        if (price != null) {
            xmlWriter.appendChild(ackElement, "price").setTextContent(price.toPlainString());
        }

    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
