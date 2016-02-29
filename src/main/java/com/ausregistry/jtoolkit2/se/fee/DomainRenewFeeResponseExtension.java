package com.ausregistry.jtoolkit2.se.fee;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

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
public final class DomainRenewFeeResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -6007874008986690757L;
    private static final String FEE_PREFIX = ExtendedObjectType.FEE.getName();
    private static final String FEE_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + FEE_PREFIX
            + ":RESPONSE_TYPE/" + FEE_PREFIX;
    private static final String CURRENCY_EXPR = FEE_XPATH_PREFIX + ":currency/text()";
    private static final String FEE_EXPR = FEE_XPATH_PREFIX + ":fee/text()";
    private String responseType;
    private boolean initialised = false;
    private String currency;
    private BigDecimal fee;

    public DomainRenewFeeResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        currency = xmlDoc.getNodeValue(replaceResponseType(CURRENCY_EXPR, responseType));
        String feeNodeValue = xmlDoc.getNodeValue(replaceResponseType(FEE_EXPR, responseType));
        if (feeNodeValue != null) {
            fee = new BigDecimal(feeNodeValue);
        }
        initialised = (currency != null && fee != null);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
