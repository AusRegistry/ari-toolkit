package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Transfer command, representing the Transfer Premium Domain aspect of the
 * Premium Domain Name extension.</p>
 *
 * <p>Use this to acknowledge the premium fees associated with this domain name as part of an EPP Domain Transfer
 * command compliant with RFC5730 and RFC5731. The "renewal price" value is optional, but if it is
 * supplied, should match the renewal fee that is set for the domain name for the one year.
 * The response expected from a server should be handled by a Domain Transfer Response object.</p>
 *
 * @see DomainTransferRequestCommand
 * @see DomainTransferResponse
 * @see <a href="http://ausregistry.github.io/doc/premium-1.1/premium-1.1.html">Premium Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainTransferRequestPremiumCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5904807416938415563L;

    private BigDecimal renewalPrice;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element transferElement = xmlWriter.appendChild(extensionElement, "transfer",
                ExtendedObjectType.PREMIUM.getURI());
        Element ackElement = xmlWriter.appendChild(transferElement, "ack");

        if (renewalPrice != null) {
            xmlWriter.appendChild(ackElement, "renewalPrice").setTextContent(renewalPrice.toPlainString());
        }
    }

    public void setRenewalPrice(BigDecimal renewalPrice) {
        this.renewalPrice = renewalPrice;
    }
}
