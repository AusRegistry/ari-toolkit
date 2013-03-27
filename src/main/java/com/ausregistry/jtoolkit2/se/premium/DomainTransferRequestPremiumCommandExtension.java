package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

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
