package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Application Create command, representing the Create Premium Domain aspect of the
 * Domain Name Fee Extension.</p>
 *
 * <p>Use this to identify the domain name application phase or application id that this command is being submitted
 * in as part of an EPP Domain Create command compliant with RFC5730 and RFC5731. The "currency" and "fee" values
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Create Application Response.</p>
 *
 * @see DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.app.DomainCreateApplicationResponseExtension
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateApplicationFeeCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 3982521830455586062L;

    private BigDecimal fee;
    private String currency;

    public DomainCreateApplicationFeeCommandExtension(BigDecimal fee, String currency) {
        this.fee = fee;
        this.currency = currency;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        fee = fee.setScale(2);

        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.FEE.getURI());
        xmlWriter.appendChild(createElement, "currency").setTextContent(currency);
        xmlWriter.appendChild(createElement, "fee").setTextContent(fee.toPlainString());
    }

}
