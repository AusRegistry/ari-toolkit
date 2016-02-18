package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;
import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Renew Premium Domain aspect of the
 * Domain Name Fee Extension.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Renew
 * command compliant with RFC5730 and RFC5731. The "currency" and "fee" values
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Renew Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 * @see com.ausregistry.jtoolkit2.se.DomainRenewResponse
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRenewFeeCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 4982521830855586062L;

    private BigDecimal fee;
    private String currency;

    public DomainRenewFeeCommandExtension(BigDecimal fee, String currency) {
        this.fee = fee;
        this.currency = currency;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();

        final Element createElement = xmlWriter.appendChild(extensionElement, "renew",
                ExtendedObjectType.FEE.getURI());

        fee = fee.setScale(2);
        xmlWriter.appendChild(createElement, "currency").setTextContent(currency);
        xmlWriter.appendChild(createElement, "fee").setTextContent(fee.toPlainString());
    }

}
