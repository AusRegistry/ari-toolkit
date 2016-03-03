package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Restore/Renew Domain aspect of the
 * Domain Name Fee Extension.</p>
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Restore/Renew
 * command compliant with RFC5730 and RFC5731. The "currency" and "fee" values
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Renew Response.</p>
 *
 * @see DomainRenewCommand
 * @see DomainRenewResponse
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRestoreFeeCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 4982521830855586062L;

    private BigDecimal renewFee;
    private BigDecimal restoreFee;
    private String currency;

    public DomainRestoreFeeCommandExtension(BigDecimal renewFee, BigDecimal restoreFee, String currency) {
        this.renewFee = renewFee;
        this.restoreFee = restoreFee;
        this.currency = currency;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement, "renew",
                ExtendedObjectType.FEE.getURI());
        xmlWriter.appendChild(createElement, "currency").setTextContent(currency);

        if (restoreFee != null) {
            final Element restoreElement = xmlWriter.appendChild(createElement, "fee");
            restoreFee = restoreFee.setScale(2);
            restoreElement.setAttribute("description", "Restore Fee");
            restoreElement.setTextContent(restoreFee.toPlainString());
        }

        if (renewFee != null) {
            final Element renewElement = xmlWriter.appendChild(createElement, "fee");
            renewFee = renewFee.setScale(2);
            renewElement.setAttribute("description", "Renewal Fee");
            renewElement.setTextContent(renewFee.toPlainString());
        }

    }

}
