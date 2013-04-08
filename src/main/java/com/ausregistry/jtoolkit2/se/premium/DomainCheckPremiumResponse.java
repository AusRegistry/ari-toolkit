package com.ausregistry.jtoolkit2.se.premium;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ausregistry.jtoolkit2.se.DataResponse;
import com.ausregistry.jtoolkit2.se.PremiumInfo;
import com.ausregistry.jtoolkit2.se.StandardCommandType;
import com.ausregistry.jtoolkit2.se.StandardObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Check response with the Premium Fee Check aspect of the
 * Premium Domain Name extension.</p>
 *
 * <p>Use this to access "create" and "renew" fees for premium domains as provided in an EPP Domain Check response
 * compliant with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Check command with the Premium Domain Name extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainCheckPremiumCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/premium-1.0/premium-1.0.html">Premium Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPremiumResponse extends DataResponse {

    private static final long serialVersionUID = 1616054275103086838L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + RES_DATA_EXPR
            + "/premium:chkData/*)";
    private static final String CHKDATA_IND_EXPR = RES_DATA_EXPR
            + "/premium:chkData/premium:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/premium:name/text()";
    private static final String CHKDATA_PREMIUM_VALUE_EXPR = "/premium:name/@premium";
    private static final String CHKDATA_CREATE_PRICE_EXPR = "/premium:price/text()";
    private static final String CHKDATA_RENEW_PRICE_EXPR = "/premium:renewalPrice/text()";

    private Map<String, PremiumInfo> premiumNameMap;
    private Map<Long, PremiumInfo> premiumIndexMap;


    public DomainCheckPremiumResponse() {
        super(StandardCommandType.CHECK, StandardObjectType.DOMAIN);
        premiumNameMap = new HashMap<String, PremiumInfo>();
        premiumIndexMap = new HashMap<Long, PremiumInfo>();
    }


    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public final void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);
        if (!resultArray[0].succeeded()) {
            return;
        }
        try {
            int cdCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
            for (int i = 0; i < cdCount; i++) {
                processElement(xmlDoc, i);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        } catch (NumberFormatException nfe) {
            maintLogger.warning(nfe.getMessage());
        } catch (NullPointerException npe) {
            maintLogger.warning(npe.getMessage());
        }
    }

    private void processElement(XMLDocument xmlDoc, int i) throws XPathExpressionException {
        String qry = replaceIndex(CHKDATA_IND_EXPR, i + 1);
        final String domainName = getKey(xmlDoc, qry);
        String premiumStr = xmlDoc.getNodeValue(qry + CHKDATA_PREMIUM_VALUE_EXPR);
        boolean isPremiumDomain = (premiumStr.equals("1") ? true : false);
        PremiumInfo premiumInfo;
        if (isPremiumDomain) {
        BigDecimal createPriceValue = BigDecimal.valueOf(Double.parseDouble(xmlDoc.getNodeValue(qry
                + CHKDATA_CREATE_PRICE_EXPR)));
        BigDecimal renewPriceValue = BigDecimal.valueOf(Double.parseDouble(xmlDoc.getNodeValue(qry
                + CHKDATA_RENEW_PRICE_EXPR)));
         premiumInfo = new PremiumInfo(isPremiumDomain, createPriceValue, renewPriceValue);
        } else {
            premiumInfo = new PremiumInfo(isPremiumDomain, null, null);
        }
        premiumIndexMap.put(i + 1L, premiumInfo);
        premiumNameMap.put(domainName, premiumInfo);
    }

    private String getKey(final XMLDocument xmlDoc, final String qry) throws XPathExpressionException {
        return xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
    }

    /**
     * @param domainName domain name to be checked
     * @return true if the domain is premium, false otherwise
     */
    public final Boolean isPremium(final String domainName) {
        PremiumInfo premiumInfo = premiumNameMap.get(domainName);
        return premiumInfo == null ? null : premiumInfo.isPremium();
    }

    /**
     * @param domainName domain name to be checked
     * @return create price for domain if exists otherwise null
     */
    public final BigDecimal getCreatePrice(final String domainName) {
        PremiumInfo premiumInfo = premiumNameMap.get(domainName);
        return premiumInfo == null ? null : premiumInfo.getCreatePrice();
    }

    /**
     * @param domainName domain name to be checked
     * @return renew price for domain if exists otherwise null
     */
    public final BigDecimal getRenewPrice(final String domainName) {
        PremiumInfo premiumInfo = premiumNameMap.get(domainName);
        return premiumInfo == null ? null : premiumInfo.getRenewPrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return true if the domain is premium, false otherwise
     */
    public final Boolean isPremium(final long index) {
        PremiumInfo premiumInfo = premiumIndexMap.get(index);
        return premiumInfo == null ? null : premiumInfo.isPremium();
    }

    /**
     * @param index the index of domain to be checked
     * @return create price for domain if exists otherwise null
     */
    public final BigDecimal getCreatePrice(final Long index) {
        PremiumInfo premiumInfo = premiumIndexMap.get(index);
        return premiumInfo == null ? null : premiumInfo.getCreatePrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return renew price for domain if exists otherwise null
     */
    public final BigDecimal getRenewPrice(final Long index) {
        PremiumInfo premiumInfo = premiumIndexMap.get(index);
        return premiumInfo == null ? null : premiumInfo.getRenewPrice();
    }
}
