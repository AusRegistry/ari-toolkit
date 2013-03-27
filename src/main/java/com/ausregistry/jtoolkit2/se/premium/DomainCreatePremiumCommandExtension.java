package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

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
