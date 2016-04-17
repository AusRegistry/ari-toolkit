package com.ausregistry.jtoolkit2.se.fee;

import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create Premium Domain aspect of the
 * Domain Name Fee Extension during a launch phase to create an application.</p>
 *
 * <p>Use this to acknowledge the price associated with this domain name as part of an EPP Domain Create
 * command compliant with RFC5730 and RFC5731. The "currency", "applicationFee", "allocationFee" and "registrationFee"
 * values supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.DomainCreateResponse
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainApplicationFeeCommandExtension implements CommandExtension {

    private static final long serialVersionUID = 3982521830455586062L;

    private static final String FIELD_IDENTIFIER = "<<field>>";
    private BigDecimal applicationFee;
    private BigDecimal allocationFee;
    private BigDecimal registrationFee;
    private String currency;

    public DomainApplicationFeeCommandExtension(BigDecimal applicationFee, BigDecimal allocationFee,
                                                BigDecimal registrationFee, String currency) {
        if (applicationFee != null) {
            this.applicationFee = applicationFee;
        } else {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.ar.domain.check.fee", FIELD_IDENTIFIER,
                    "applicationFee"));
        }
        if (allocationFee != null) {
            this.allocationFee = allocationFee;
        } else {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.ar.domain.check.fee", FIELD_IDENTIFIER,
                    "allocationFee"));
        }
        if (registrationFee != null) {
            this.registrationFee = registrationFee;
        } else {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.ar.domain.check.fee", FIELD_IDENTIFIER,
                    "registrationFee"));
        }
        this.currency = currency;

    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        applicationFee = applicationFee.setScale(2);
        allocationFee = allocationFee.setScale(2);
        registrationFee = registrationFee.setScale(2);

        final Element createElement = xmlWriter.appendChild(extensionElement, "create",
                ExtendedObjectType.FEE.getURI());
        xmlWriter.appendChild(createElement, "currency").setTextContent(currency);

        Element applicationFeeTag = xmlWriter.appendChild(createElement, "fee");
        applicationFeeTag.setTextContent(applicationFee.toPlainString());
        applicationFeeTag.setAttribute("description", "Application Fee");
        Element allocationFeeTag = xmlWriter.appendChild(createElement, "fee");
        allocationFeeTag.setTextContent(allocationFee.toPlainString());
        allocationFeeTag.setAttribute("description", "Allocation Fee");
        Element registrationFeeTag = xmlWriter.appendChild(createElement, "fee");
        registrationFeeTag.setTextContent(registrationFee.toPlainString());
        registrationFeeTag.setAttribute("description", "Registration Fee");
    }

}
