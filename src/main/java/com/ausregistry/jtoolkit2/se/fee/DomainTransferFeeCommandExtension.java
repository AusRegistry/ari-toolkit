package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Transfer command, representing the Transfer Premium Domain aspect of the
 * Domain Name Fee Extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Transfer
 * command compliant with RFC5730 and RFC5731. The "currency" and "fee" values
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Transfer Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainTransferCommand
 * @see com.ausregistry.jtoolkit2.se.DomainTransferResponse
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03#section-4.2.4">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainTransferFeeCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 5982521830455586062L;

    private BigDecimal fee;
    private String currency;

    public DomainTransferFeeCommandExtension(BigDecimal fee, String currency) {
        this.fee = fee;
        this.currency = currency;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        fee = fee.setScale(2);

        final Element createElement = xmlWriter.appendChild(extensionElement, "transfer",
                ExtendedObjectType.FEE.getURI());
        xmlWriter.appendChild(createElement, "currency").setTextContent(currency);
        xmlWriter.appendChild(createElement, "fee").setTextContent(fee.toPlainString());
    }

}
