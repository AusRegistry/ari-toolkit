package com.ausregistry.jtoolkit2.se.fee;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Create response, representing the Fee
 * extension during a launch phase to create an application..</p>
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
public final class DomainApplicationFeeResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -6007874008986690757L;

    private static final String FEE_PREFIX = ExtendedObjectType.FEE.getName();

    private static final String FEE_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + FEE_PREFIX
            + ":RESPONSE_TYPE/" + FEE_PREFIX;
    private static final String CURRENCY_EXPR = FEE_XPATH_PREFIX + ":currency/text()";
    private static final String FEE_EXPR = FEE_XPATH_PREFIX + ":fee";

    private String responseType;

    private boolean initialised = false;

    private String currency;
    private BigDecimal applicationFee;
    private BigDecimal allocationFee;
    private BigDecimal registrationFee;

    public DomainApplicationFeeResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        currency = xmlDoc.getNodeValue(replaceResponseType(CURRENCY_EXPR, responseType));
        NodeList feeNodeList = xmlDoc.getElements(replaceResponseType(FEE_EXPR, responseType));
        if (feeNodeList != null) {
            int nodeLength = feeNodeList.getLength();
            for (int i = 0; i < nodeLength; i++) {
                Node node = feeNodeList.item(i);
                String description = node.getAttributes().getNamedItem("description").getNodeValue();
                if (description.equals("Application Fee")) {
                    applicationFee = new BigDecimal(node.getTextContent());

                } else if (description.equals("Allocation Fee")) {
                    allocationFee = new BigDecimal(node.getTextContent());

                } else if (description.equals("Registration Fee")) {
                    registrationFee = new BigDecimal(node.getTextContent());

                }
            }
        }

        initialised = (currency != null && applicationFee != null && allocationFee != null && registrationFee != null);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRegistrationFee() {
        return registrationFee;
    }

    public BigDecimal getAllocationFee() {
        return allocationFee;
    }

    public BigDecimal getApplicationFee() {
        return applicationFee;
    }
}
