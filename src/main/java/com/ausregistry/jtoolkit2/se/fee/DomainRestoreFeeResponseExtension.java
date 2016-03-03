package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Renew response, representing the Fee extension.</p>
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
public final class DomainRestoreFeeResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -6007874008986690757L;
    private static final String FEE_PREFIX = ExtendedObjectType.FEE.getName();
    private static final String FEE_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + FEE_PREFIX
            + ":RESPONSE_TYPE/" + FEE_PREFIX;
    private static final String CURRENCY_EXPR = FEE_XPATH_PREFIX + ":currency/text()";
    private static final String FEE_EXPR_NODES = FEE_XPATH_PREFIX + ":fee";
    private static final String FEE_EXPR = FEE_XPATH_PREFIX + ":fee/text()";
    private static final String FEE_TYPE_EXPR = FEE_XPATH_PREFIX + ":fee/@description";
    private String responseType;
    private boolean initialised = false;
    private String currency;
    private BigDecimal restoreFee;
    private BigDecimal renewFee;

    public DomainRestoreFeeResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        currency = xmlDoc.getNodeValue(replaceResponseType(CURRENCY_EXPR, responseType));

        NodeList feeNodes = xmlDoc.getElements(replaceResponseType(FEE_EXPR_NODES, responseType));
        if (feeNodes != null) {
            for (int feeNodeIndex = 0; feeNodeIndex < feeNodes.getLength(); feeNodeIndex++) {
                Node feeNode = feeNodes.item(feeNodeIndex);
                String feeNodeValue = feeNode.getTextContent();
                String feetype = feeNode.getAttributes().getNamedItem("description").getTextContent();

                if (feetype.equalsIgnoreCase("Restore Fee")) {
                    restoreFee = new BigDecimal(feeNodeValue);
                }
                if (feetype.equalsIgnoreCase("Renewal Fee")) {
                    renewFee = new BigDecimal(feeNodeValue);
                }
            }
        }

        initialised = (currency != null && (restoreFee != null || renewFee != null));
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRestoreFee() {
        return restoreFee;
    }

    public BigDecimal getRenewFee() {
        return renewFee;
    }
}
