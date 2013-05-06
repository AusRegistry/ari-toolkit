package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create Premium Domain aspect of the
 * Premium Domain Name Extension.</p>
 *
 * <p>Use this to acknowledge the premium fees associated with this domain name as part of an EPP Domain Create
 * command compliant with RFC5730 and RFC5731. The "price" and "renewal price" values are optional, but if they are
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see DomainCreateCommand
 * @see DomainCreateResponse
 * @see <a href="http://ausregistry.github.io/doc/premium-1.1/premium-1.1.html">Premium Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreatePremiumCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 2782521830455586062L;

    private BigDecimal price;
    private BigDecimal renewalPrice;

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.PREMIUM.getURI());
        Element ackElement = xmlWriter.appendChild(createElement, "ack");
        if (price != null) {
            xmlWriter.appendChild(ackElement, "price").setTextContent(price.toPlainString());
        }
        if (renewalPrice != null) {
            xmlWriter.appendChild(ackElement, "renewalPrice").setTextContent(renewalPrice.toPlainString());
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRenewalPrice(BigDecimal renewalPrice) {
        this.renewalPrice = renewalPrice;
    }
}
