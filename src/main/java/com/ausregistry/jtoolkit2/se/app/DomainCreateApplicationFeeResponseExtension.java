package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;

/**
 * <p>Extension for the EPP Domain Application Create response, representing the Fee
 * extension.</p>
 *
 * <p>Use this to identify the domain name application phase or application id that this command is being submitted
 * in as part of an EPP Domain Create command compliant with RFC5730 and RFC5731. The "currency" and "fee" values
 * supplied, should match the fees that are set for the domain name for the requested period.
 * The response expected from a server should be handled by a Domain Create Response.</p>
 *
 * @see DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.app.DomainCreateApplicationResponseExtension
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public final class DomainCreateApplicationFeeResponseExtension extends ResponseExtension {

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

    public DomainCreateApplicationFeeResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        currency = xmlDoc.getNodeValue(replaceResponseType(
                CURRENCY_EXPR, responseType));

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
